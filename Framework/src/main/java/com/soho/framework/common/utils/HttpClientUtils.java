package com.soho.framework.common.utils;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings({"unused", "StringBufferMayBeStringBuilder"})
public class HttpClientUtils {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 请求URL
     */
    private String requestUrl;
    private Map<String, String> requestParams = new HashMap<>();
    private Map<String, List<File>> files = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();

    /**
     * 下载文件
     *
     * @param url          http://www.xxx.com/img/333.jpg
     * @param destFileName xxx.jpg/xxx.png/xxx.txt
     * @throws IOException IO发生错误
     */
    public static void getFile(String url, String destFileName) throws IOException {
        // 生成一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        File file = new File(destFileName);
        try (InputStream in = entity.getContent()) {
            FileOutputStream fout = new FileOutputStream(file);
            int l;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp, 0, l);
                // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
            }
            fout.flush();
            fout.close();
        }
        httpclient.close();
    }

    /**
     * 发送请求
     *
     * @param httpclient client对象
     * @param httpost    request参数
     * @return 响应response
     */
    private HttpResponse sendRequest(HttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = null;
        try {
            for (Entry<String, String> next : this.headers.entrySet()) {
                httpost.addHeader(next.getKey(), next.getValue());
            }
            response = httpclient.execute(httpost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void addHeader(String headName, String value) {
        this.headers.put(headName, value);
    }

    /**
     * 添加需要发送的参数
     *
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     */
    public void push(String fieldName, String fieldValue) {
        requestParams.put(fieldName, fieldValue);
    }

    /**
     * 添加需要发送的文件
     *
     * @param fieldName 字段名称
     * @param file      要发送的文件
     */
    public void push(String fieldName, File file) {
        if (!files.containsKey(fieldName)) {
            files.put(fieldName, new ArrayList<>());
        }
        files.get(fieldName).add(file);
    }

    @SuppressWarnings("WeakerAccess")
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * 设置请求路径
     *
     * @param requestUrl 请求路径
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    private HttpPost prepareHttpPost() throws ParseException, IOException {
        HttpPost httpPost = new HttpPost(this.getRequestUrl());
        // 如果不需要传文件，使用postForm方法，否则需要使用
        if (files.isEmpty()) {
            List<NameValuePair> nvps = new ArrayList<>();
            Set<String> keySet = requestParams.keySet();
            logger.info("====上传的参数清单===");
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, requestParams.get(key)));
                logger.info(String.format("[%-20s] %s", key, requestParams.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        } else {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            logger.info("====上传的文件清单===");
            // 添加文件
            for (Entry<String, List<File>> stringEntry : files.entrySet()) {
                String fieldName = stringEntry.getKey();
                List<File> fileList = stringEntry.getValue();
                for (File file : fileList) {
                    multipartEntityBuilder.addPart(
                            fieldName,
                            new FileBody(file)
                    );
                    logger.info(String.format("[%-20s] %s", fieldName, file.getName()));
                }
            }
            logger.info("====上传的参数清单===");
            // 添加字段
            for (Entry<String, String> stringStringEntry : requestParams.entrySet()) {
                multipartEntityBuilder.addPart(
                        stringStringEntry.getKey(),
                        new StringBody(
                                stringStringEntry.getValue(),
                                ContentType.create(
                                        "text/plain",
                                        Consts.UTF_8
                                )
                        )
                );
                logger.info(stringStringEntry.getKey() + "\t" + stringStringEntry.getValue());
            }
            HttpEntity reqEntity = multipartEntityBuilder.build();
            httpPost.setEntity(reqEntity);
            logger.info("===构建上传信息完毕===");
        }
        return httpPost;
    }

    /**
     * post请求
     */
    public String sendPost() {
        String body = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            logger.info(String.format("%s[%20s]%s", StringUtils.repeat("=", 20), "正在准备Http Post请求", StringUtils.repeat("=", 20)));
            HttpPost post = this.prepareHttpPost();
            logger.info(String.format("[%-20s] : %s", "requestURL", this.getRequestUrl()));
            body = invoke(httpclient, post);
            logger.info(String.format("[%-20s] : %s", "response", body));
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    /**
     * get请求
     */
    public String sendGet() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String requestURL = this.getRequestUrl();
        logger.info(String.format("%s[%20s]%s", StringUtils.repeat("=", 20), "preparing Http Get request", StringUtils.repeat("=", 20)));
        logger.info(">>请求参数");
        if (!requestParams.isEmpty()) {
            StringBuffer requestURLStringBuffer = new StringBuffer(this.getRequestUrl());
            requestURLStringBuffer.append("?");
            Iterator<Entry<String, String>> iterator = requestParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> paramEntry = iterator.next();
                requestURLStringBuffer.append(paramEntry.getKey());
                requestURLStringBuffer.append("=");
                requestURLStringBuffer.append(paramEntry.getValue());
                logger.info("　　" + paramEntry.getKey() + "\t=" + paramEntry.getValue());
                if (iterator.hasNext()) {
                    requestURLStringBuffer.append("&");
                }
            }
            requestURL = requestURLStringBuffer.toString();
            logger.info(">>开始发送Get请求" + requestURL);
        }
        HttpGet get = new HttpGet(requestURL);
        String body = invoke(httpclient, get);
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("><关闭HttpGet请求时发生异常" + e.getMessage());
        }
        logger.info("<<响应结果为" + body);
        return body;
    }

    /**
     * 调用请求
     *
     * @param httpclient httlClient对象
     * @param httpost    request信息
     * @return 请求的结果
     */
    private String invoke(HttpClient httpclient, HttpUriRequest httpost) {
        HttpResponse response = sendRequest(httpclient, httpost);
        return paseResponse(response);
    }

    /**
     * 解析响应
     *
     * @param response 响应信息
     * @return 解析后的字符串
     */
    private String paseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return body;
    }
}