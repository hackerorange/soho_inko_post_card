package com.soho.inko.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


/**
 * Created by ZhongChongtao on 2017/3/25.
 */
public class HttpClientUtils {

    private final static int BUFFER = 1024;

//    public static void main(String[] args) {
//        System.out.println(downloadFileByUrl("http://localhost:8089/file/fb413367e2e29e2f05baff4489baac79", new File("D:/tmp/tmp0001")));
//    }

    /**
     * 从指定的地址下载文件，并保存到指定的文件中
     *
     * @param fileUrl    文件URL地址
     * @param targetFile 目标文件
     * @return 成功返回true，失败返回false
     */
    @SuppressWarnings({"SameParameterValue", "WeakerAccess"})
    public static boolean downloadFileByUrl(String fileUrl, File targetFile) {
        HttpClient client = new HttpClient();
        GetMethod httpGet = new GetMethod(fileUrl);
        InputStream in = null;
        FileOutputStream out = null;
        try {
            //执行get方法
            client.executeMethod(httpGet);

            in = httpGet.getResponseBodyAsStream();
            if (!targetFile.exists()) {
                if (!targetFile.getParentFile().exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    targetFile.getParentFile().mkdirs();
                }
                //noinspection ResultOfMethodCallIgnored
                targetFile.createNewFile();
            }
            out = new FileOutputStream(targetFile);
            byte[] b = new byte[BUFFER];
            int len;
            byte[] content = new byte[0];
            int length;
            //写文件，这里将所有的内容都缓存到内存中，最后一次性写入
            while ((len = in.read(b)) > 0) {
                // out.write(b,0,len);
                length = content.length;
                content = Arrays.copyOf(content, length + len);// 扩容
                System.arraycopy(b, 0, content, length, len);// 将第二个数组与第一个数组合并
            }
            out.write(content, 0, content.length);
            in.close();
            out.close();

        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null)
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            e.printStackTrace();
            return false;
        } finally {
            httpGet.releaseConnection();
        }
        return true;
    }
}
