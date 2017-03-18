package com.soho.framework.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class StringUtils {
    public static final String splitStrPattern = ",|，|;|；|、|\\.|。|-|_|\\(|\\)|\\[|\\]|\\{|\\}|\\\\|/| |　|\"";
    private static Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");
    private static Pattern emailPattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    private static Pattern floatNumericPattern = Pattern.compile("^[0-9\\-.]+$");
    private static Pattern abcPattern = Pattern.compile("^[a-z|A-Z]+$");
    private static Pattern mobilePattern = Pattern.compile("^(13|18|15)[0-9]{9}$");
    private static Log logger = LogFactory.getLog(StringUtils.class);

    /***************************************************************************
     * 判断字符串是否只由另外一个字符串中的字符组成
     *
     * @param source       要判断的字符串
     * @param legalContext 规则字符串
     * @return 是否只由第二个字符中的字符组成
     ***************************************************************************/
    public static boolean isLegalContext(String source, String legalContext) {
        if (!TypeChecker.isEmpty(source) && !TypeChecker.isEmpty(legalContext)) {
            for (int i = 0; i < source.length(); i++) {
                char c = source.charAt(i);
                if (!legalContext.contains(String.valueOf(c))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /***************************************************************************
     * 判断是否数字表示
     *
     * @param src 源字符串
     * @return 是否数字的标志
     ***************************************************************************/
    public static boolean isNumeric(String src) {
        boolean return_value = false;
        if (src == null || src.length() <= 0) {
            return false;
        }
        Matcher m = numericPattern.matcher(src);
        return m.find();
    }

    /***************************************************************************
     * 判断是否纯字母组合
     *
     * @param src 源字符串
     * @return 是否纯字母组合的标志
     ***************************************************************************/
    public static boolean isABC(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = abcPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /***************************************************************************
     * 判断是否浮点数字表示
     *
     * @param src 源字符串
     * @return 是否数字的标志
     ***************************************************************************/
    public static boolean isFloatNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = floatNumericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /***************************************************************************
     * 把string List用给定的符号symbol连接成一个字符串
     *
     * @param array  要连接的对象
     * @param symbol 连接符号
     * @return 连接后的字符串
     ***************************************************************************/
    public static String joinString(List array, String symbol) {
        String result = "";
        if (array != null) {
            for (Object anArray : array) {
                String temp = anArray.toString();
                if (temp != null && temp.trim().length() > 0)
                    result += (temp + symbol);
            }
            if (result.length() > 1)
                result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /***************************************************************************
     * 只显示字符串的前n个字符，剩余的字符串以...代替
     *
     * @param subject  要截取的字符串
     * @param size     截取的长度
     * @param isEncode 是否需要编码
     * @return 截取后的字符串
     ***************************************************************************/
    public static String subStringAndExtr(String subject, int size, boolean isEncode) {
        //TODO:encode string
        if (isEncode) {
            subject = subject + "";
        }
        if (subject != null && subject.length() > size) {
            subject = subject.substring(0, size) + "...";
        }
        return subject;
    }

    public static void main(String[] args) {
    }

    /***************************************************************************
     * 截取字符串　超出的字符用symbol代替
     *
     * @param len    字符串长度　长度计量单位为一个GBK汉字　　两个英文字母计算为一个单位长度
     * @param str    要截取的字符串
     * @param symbol 超出后的字符串
     * @return 截取后的字符串
     ***************************************************************************/
    public static String getLimitLengthString(String str, int len, String symbol) {
        int iLen = len * 2;
        int counterOfDoubleByte = 0;
        String strRet;
        try {
            if (str != null) {
                byte[] b = str.getBytes("GBK");
                if (b.length <= iLen) {
                    return str;
                }
                for (int i = 0; i < iLen; i++) {
                    if (b[i] < 0) {
                        counterOfDoubleByte++;
                    }
                }
                if (counterOfDoubleByte % 2 == 0) {
                    strRet = new String(b, 0, iLen, "GBK") + symbol;
                    return strRet;
                } else {
                    strRet = new String(b, 0, iLen - 1, "GBK") + symbol;
                    return strRet;
                }
            } else {
                return "";
            }
        } catch (Exception ex) {
            return str.substring(0, len);
        }
    }

    /***************************************************************************
     * 截取字符串　超出的字符用...代替
     *
     * @param len 　字符串长度　长度计量单位为一个GBK汉字　　两个英文字母计算为一个单位长度
     * @param str 要截取的字符串
     * @return 截取后的字符串
     ***************************************************************************/
    public static String getLimitLengthString(String str, int len) {
        return getLimitLengthString(str, len, "...");
    }

    /***************************************************************************
     * 截取字符
     *
     * @param subject  要截取的字符串
     * @param size     截取长度
     * @param isEncode 是否需要转码
     * @return 截取后的字符串
     ***************************************************************************/
    public static String subStr(String subject, int size, boolean isEncode) {
        //TODO : encode
        if (subject.length() > size) {
            subject = subject.substring(0, size);
        }
        return subject;
    }

    /***************************************************************************
     * 把string array t用给定的符号symbol连接成一个字符串
     *
     * @param array  StringArray
     * @param symbol 连接符号
     * @return 连接后的字符串
     ***************************************************************************/
    public static String joinString(String[] array, String symbol) {
        String result = "";
        if (array != null) {
            for (String temp : array) {
                if (temp != null && temp.trim().length() > 0)
                    result += (temp + symbol);
            }
            if (result.length() > 1)
                result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /***************************************************************************
     * 取得字符串的实际长度（考虑了汉字的情况）
     *
     * @param SrcStr 源字符串
     * @return 字符串的实际长度
     ***************************************************************************/
    public static int getStringLen(String SrcStr) {
        int return_value = 0;
        if (SrcStr != null) {
            char[] theChars = SrcStr.toCharArray();
            for (char theChar : theChars) {
                return_value += (theChar <= 255) ? 1 : 2;
            }
        }
        return return_value;
    }

    /***************************************************************************
     * 检查联系人信息是否填写，电话，手机，email必须填至少一个，email填了的话检查格式
     *
     * @param phoneCity    电话
     * @param phoneNumber  电话号码
     * @param phoneExt     额外手机号码
     * @param mobileNumber 手机号码
     * @param email        邮箱地址
     * @return 是否填写了一个
     ***************************************************************************/
    public static boolean checkContactInfo(String phoneCity, String phoneNumber, String phoneExt, String mobileNumber,
                                           String email) {
        int length = 0;
        length += phoneCity == null ? 0 : phoneCity.trim().length();
        length += phoneNumber == null ? 0 : phoneNumber.trim().length();
        length += phoneExt == null ? 0 : phoneExt.trim().length();
        length += mobileNumber == null ? 0 : mobileNumber.trim().length();
        length += email == null ? 0 : email.trim().length();
        return length >= 1 && isEmail(email);
    }

    /***************************************************************************
     * 检查数据串中是否包含非法字符集
     *
     * @param str 要检查的字符串
     * @return [true]|[false] 包含|不包含
     ***************************************************************************/
    public static boolean checkIllegalCharacter(String str) {
        String sIllegal = "'\"";
        int len = sIllegal.length();
        if (null == str)
            return false;
        for (int i = 0; i < len; i++) {
            if (str.indexOf(sIllegal.charAt(i)) != -1)
                return true;
        }
        return false;
    }

    /***************************************************************************
     * getHideEmailPrefix - 隐藏邮件地址前缀。
     *
     * @param email - EMail邮箱地址 例如: linwenguo@koubei.com 等等...
     ***************************************************************************/
    public static String getHideEmailPrefix(String email) {
        if (null != email) {
            int index = email.lastIndexOf('@');
            if (index > 0) {
                email = repeat("*", index).concat(email.substring(index));
            }
        }
        return email;
    }

    /***************************************************************************
     * repeat - 通过源字符串重复生成N次组成新的字符串。 
     * @return 返回已生成的重复字符串
     ***************************************************************************/
    public static String repeat(String src, int num) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < num; i++)
            s.append(src);
        return s.toString();
    }

    /***************************************************************************
     * 检查是否是email, when null or '' return true
     *
     * @param email 要判断的字符串
     * @return 是否为email
     ***************************************************************************/
    public static boolean isEmail(String email) {
        return !(email == null || email.length() == 0) && emailPattern.matcher(email).find();
    }

    /***************************************************************************
     * 根据指定的字符把源字符串分割成一个List集合
     *
     * @param sourceString 要分割的字符串
     * @param pattern      分割符
     * @return 分割后的字符List集合
     ***************************************************************************/
    public static List<String> splitString2ListByPattern(String sourceString, String pattern) {
        if (sourceString == null)
            return null;
        List<String> list = new ArrayList<>();
        String[] result = sourceString.split(pattern);
        Collections.addAll(list, result);
        return list;

    }

    /***************************************************************************
     * 根据指定的字符把源字符串分割成一个数组
     *
     * @param src 将字符串根据默认分隔符，分割成一个list集合
     * @return 分割后的list集合
     ***************************************************************************/
    public static List<String> splitString2ListByPattern(String src) {
        String pattern = "，|,|、|。";
        return splitString2ListByPattern(src, pattern);
    }

    /***************************************************************************
     * 格式化一个float
     *
     * @param format 要格式化成的格式 such as #.00, #.#
     ***************************************************************************/
    public static String formatFloat(float f, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(f);
    }

    /***************************************************************************
     * 判断是否是空字符串 null和"" 都返回 true
     ***************************************************************************/
    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

    /***************************************************************************
     * 自定义的分隔字符串函数 例如: 1,2,3 =>[1,2,3] 3个元素 ,2,3=>[,2,3] 3个元素 ,2,3,=>[,2,3,]
     * 4个元素 ,,,=>[,,,] 4个元素
     * <p>
     * 5.22算法修改，为提高速度不用正则表达式 两个间隔符,,返回""元素
     *
     * @param split 分割字符 默认,
     * @param src   输入字符串
     * @return 分隔后的list
     * @author Robin
     ***************************************************************************/
    public static List<String> splitToList(String split, String src) {
        // 默认,  
        String sp = ",";
        if (split != null && split.length() == 1) {
            sp = split;
        }
        List<String> r = new ArrayList<>();
        int lastIndex = -1;
        int index = src.indexOf(sp);
        if (-1 == index) {
            r.add(src);
            return r;
        }
        while (index >= 0) {
            if (index > lastIndex) {
                r.add(src.substring(lastIndex + 1, index));
            } else {
                r.add("");
            }
            lastIndex = index;
            index = src.indexOf(sp, index + 1);
            if (index == -1) {
                r.add(src.substring(lastIndex + 1, src.length()));
            }
        }
        return r;
    }

    /***************************************************************************
     * 将map中的键值对转化为URL get参数格式
     *
     * @param map 要转化的键值对
     * @return 转化后的URL
     ***************************************************************************/
    public static String linkedHashMapToString(LinkedHashMap<String, String> map) {
        if (map != null && map.size() > 0) {
            String result = "";
            for (String name : map.keySet()) {
                String value = map.get(name);
                result += (result.equals("")) ? "" : "&";
                result += String.format("%s=%s", name, value);
            }
            return result;
        }
        return null;
    }

    /***************************************************************************
     * 解析字符串返回 名称=值的参数表 (a=1&b=2 => a=1,b=2)
     ***************************************************************************/
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> toLinkedHashMap(String str) {
        if (str != null && !str.equals("") && str.indexOf("=") > 0) {
            LinkedHashMap result = new LinkedHashMap();
            String name = null;
            String value = null;
            int i = 0;
            while (i < str.length()) {
                char c = str.charAt(i);
                switch (c) {
                    case 61: // =
                        value = "";
                        break;
                    case 38: // &
                        if (name != null && value != null && !name.equals("")) {
                            result.put(name, value);
                        }
                        name = null;
                        value = null;
                        break;
                    default:
                        if (value != null) {
                            value = value + c;
                        } else {
                            name = (name != null) ? (name + c) : "" + c;
                        }
                }
                i++;
            }
            if (name != null && value != null && !name.equals("")) {
                result.put(name, value);
            }
            return result;
        }
        return null;
    }

    /***************************************************************************
     * 根据输入的多个解释和下标返回一个值
     *
     * @param captions 例如:"无,爱干净,一般,比较乱"
     * @param index    1
     * @return 一般
     ***************************************************************************/
    public static String getCaption(String captions, int index) {
        if (index > 0 && captions != null && !captions.equals("")) {
            String[] ss = captions.split(",");
            if (ss.length > 0 && index < ss.length) {
                return ss[index];
            }
        }
        return null;
    }

    /***************************************************************************
     * 数字转字符串,如果num<=0 则输出""
     ***************************************************************************/
    public static String numberToString(Object num) {
        if (num == null) {
            return null;
        } else if (num instanceof Integer && (Integer) num > 0) {
            return Integer.toString((Integer) num);
        } else if (num instanceof Long && (Long) num > 0) {
            return Long.toString((Long) num);
        } else if (num instanceof Float && (Float) num > 0) {
            return Float.toString((Float) num);
        } else if (num instanceof Double && (Double) num > 0) {
            return Double.toString((Double) num);
        } else {
            return "";
        }
    }

    /***************************************************************************
     * 货币转字符串
     ***************************************************************************/
    public static String moneyToString(Double money, String style) {
        if (money != null && style != null) {
            if (style.equalsIgnoreCase("default")) {
                if (money == 0) {
                    // 不输出0  
                    return "";
                } else if ((money * 10 % 10) == 0) {
                    // 没有小数  
                    return Integer.toString(money.intValue());
                } else {
                    // 有小数  
                    return money.toString();
                }
            } else {
                DecimalFormat df = new DecimalFormat(style);
                return df.format(money);
            }
        }
        return null;
    }

    /***************************************************************************
     * 在sou中是否存在finds 如果指定的finds字符串有一个在sou中找到,返回true;
     *
     * @param source 源字符串
     * @param finds  要查找的所有字符串
     * @return 指定的字符串在指定的字符串中是否存在
     ***************************************************************************/
    public static boolean strPos(String source, String... finds) {
        if (source != null && finds != null && finds.length > 0) {
            for (String find : finds) {
                if (source.contains(find))
                    return true;
            }
        }
        return false;
    }

    public static boolean strPos(String source, List<String> finds) {
        if (source != null && finds != null && finds.size() > 0) {
            for (String s : finds) {
                if (source.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean strPos(String sou, String finds) {
        List<String> t = splitToList(",", finds);
        return strPos(sou, t);
    }

    /***************************************************************************
     * 判断两个字符串是否相等 如果都为null则判断为相等,一个为null另一个not null则判断不相等 否则如果s1=s2则相等
     *
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @return 相等则为true否则为false
     ***************************************************************************/
    public static boolean equals(String s1, String s2) {
        if (isEmpty(s1) && isEmpty(s2)) {
            return true;
        } else if (!StringUtils.isEmpty(s1) && !StringUtils.isEmpty(s2)) {
            return s1.equals(s2);
        }
        return false;
    }

    public static int toInt(String s) {
        if (s != null && !"".equals(s.trim())) {
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    /***************************************************************************
     * 将字符串转化为double类型
     *
     * @param s 要转化的字符串
     * @return double数值（如果s为空，则返回0）
     ***************************************************************************/
    public static double toDouble(String s) {
        if (!isEmpty(s)) {
            return Double.parseDouble(s);
        }
        return 0;
    }

    /***************************************************************************
     * 按规定长度截断字符串，没有...的省略符号
     *
     * @param subject 要截取的字符串
     * @param size    截取的长度
     * @return 截取后的字符串
     ***************************************************************************/
    public static String subStringNoEllipsis(String subject, int size) {
//        subject = TextUtils.htmlEncode(subject);
        if (subject.length() > size) {
            subject = subject.substring(0, size);
        }
        return subject;
    }

    public static long toLong(String s) {
        try {
            if (s != null && !"".equals(s.trim()))
                return Long.parseLong(s);
        } catch (Exception ignored) {
        }
        return 0L;
    }

    public static String simpleEncrypt(String str) {
        if (str != null && str.length() > 0) {
            // str = str.replaceAll("0","a");  
            str = str.replaceAll("1", "b");
            // str = str.replaceAll("2","c");  
            str = str.replaceAll("3", "d");
            // str = str.replaceAll("4","e");  
            str = str.replaceAll("5", "f");
            str = str.replaceAll("6", "g");
            str = str.replaceAll("7", "h");
            str = str.replaceAll("8", "i");
            str = str.replaceAll("9", "j");
        }
        return str;
    }

    /***************************************************************************
     * 过滤用户输入的URL地址（防治用户广告） 目前只针对以http或www开头的URL地址
     * 本方法调用的正则表达式，不建议用在对性能严格的地方例如:循环及list页面等
     *
     * @param str 需要处理的字符串
     * @return 返回处理后的字符串
     * @author fengliang
     ***************************************************************************/
    public static String removeURL(String str) {
        if (str != null)
            str = str.toLowerCase()
                    .replaceAll("(http|www|com|cn|org|\\.)+", "");
        return str;
    }

    /***************************************************************************
     * 随即生成指定位数的含数字验证码字符串
     *
     * @param bit 指定生成验证码位数
     * @return String
     * @author Peltason
     * @date 2007-5-9
     ***************************************************************************/
    public static String randomNum(int bit) {
        if (bit == 0)
            bit = 6; // 默认6位  
        String str;
        str = "0123456789";// 初始化种子
        return RandomStringUtils.random(bit, str);// 返回6位的字符串  
    }

    /***************************************************************************
     * 随即生成指定位数的含验证码字符串
     *
     * @param bit 指定生成验证码位数
     * @return String
     * @author Peltason
     * @date 2007-5-9
     ***************************************************************************/
    public static String random(int bit) {
        if (bit == 0)
            bit = 6; // 默认6位  
        // 因为o和0,l和1很难区分,所以,去掉大小写的o和l  
        String str;
        str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";// 初始化种子  
        return RandomStringUtils.random(bit, str);// 返回6位的字符串  
    }

    /***************************************************************************
     * 检查字符串是否属于手机号码
     *
     * @param str 要检查的字符串
     * @return 是否为mobile
     ***************************************************************************/
    public static boolean isMobile(String str) {
        return !isEmpty(str) && mobilePattern.matcher(str).find();
    }

    /***************************************************************************
     * 字符串转float 如果异常返回0.00
     *
     * @param s 输入的字符串
     * @return 转换后的float
     ***************************************************************************/
    public static Float toFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    /***************************************************************************
     * 页面中去除字符串中的空格、回车、换行符、制表符
     *
     * @param str 源字符串
     * @return 取出空格、回车、换行后的字符串
     ***************************************************************************/
    public static String removeBlankCharacter(String str) {
        if (isNotEmpty(str)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }

    /***************************************************************************
     * 转换编码
     *
     * @param source         源字符串
     * @param sourceCodeType 源编码格式
     * @param targetCodeType 目标编码格式
     * @return 目标编码
     ***************************************************************************/
    public static String changeCoding(String source, String sourceCodeType, String targetCodeType) {
        try {
            return new String(source.getBytes(sourceCodeType), targetCodeType);
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("对[%s]进行编码时发生异常", source));
            return source;
        }
    }

    /***************************************************************************
     * @param str 要去除标签的字符串
     * @return 替换后的字符串
     ***************************************************************************/
    public static String removeHTMLLabelExe(String str) {
        Map<String, String> regular = new LinkedHashMap<>();
        //添加规则
        regular.put(">\\s*<", "><");
        regular.put(" ", " ");// 替换空格
        regular.put("<br ?/?>", "\n");// 去<br><br />
        regular.put("<p ?/?>", "\n");// 去<br><br />
        regular.put("<([^<>]+)>", "");// 去掉<>内的字符
        regular.put("\\s\\s+", " ");// 将多个空白变成一个空格
        regular.put("^\\s*", "");// 去掉头的空白
        regular.put("\\s*$", "");// 去掉尾的空白
        regular.put(" +", " ");
        //执行替换操作
        str = replaceAllByRegularMap(str, regular);
        return str;
    }

    /***************************************************************************
     * 根据所有条件，替换文本
     *
     * @param source  源文件
     * @param regular 所有的规则
     * @return 替换后的文本
     ***************************************************************************/
    private static String replaceAllByRegularMap(String source, Map<String, String> regular) {
        for (String s : regular.keySet()) {
            source = stringReplace(source, s, regular.get(s));
        }
        return source;
    }

    /***************************************************************************
     * 去掉HTML标签之外的字符串
     *
     * @param str 源字符串
     * @return 目标字符串
     ***************************************************************************/
    public static String removeOutHTMLLabel(String str) {
        //添加非Html标签的规则
        Map<String, String> regular = new LinkedHashMap<>();
        regular.put(">([^<>]+)<", "><");
        regular.put("^([^<>]+)<", "<");
        regular.put(">([^<>]+)$", ">");
        //根据所有规则替换文本
        str = replaceAllByRegularMap(str, regular);
        return str;
    }

    /***************************************************************************
     * 字符串替换
     *
     * @param str 源字符串
     * @param sr  正则表达式样式
     * @param sd  替换文本
     * @return 结果串
     ***************************************************************************/
    public static String stringReplace(String str, String sr, String sd) {
        Pattern p = Pattern.compile(sr, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        str = m.replaceAll(sd);
        return str;
    }

    /***************************************************************************
     * 将html的省略写法替换成非省略写法
     *
     * @param sourceHtml html字符串
     * @param label      标签如table
     * @return 结果串
     ***************************************************************************/
    public static String formatToFullHtmlForm(String sourceHtml, String label) {
        String regEx = "<" + label + "\\s+([\\S&&[^<>]]*)/>";
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sourceHtml);
        String[] sa;
        String sf;
        String sf2;
        String sf3;
        while (m.find()) {
            sa = sourceHtml.split(label);
            sf = sourceHtml.substring(
                    sa[0].length(),
                    sourceHtml.indexOf(
                            "/>",
                            sa[0].length())
            );
            sf2 = sf + "></" + label + ">";
            sf3 = sourceHtml.substring(sa[0].length() + sf.length() + 2);
            sourceHtml = sa[0] + sf2 + sf3;
        }
        return sourceHtml;
    }

    /***************************************************************************
     * 得到字符串的子串位置序列
     *
     * @param str 字符串
     * @param sub 子串
     * @param b   true子串前端,false子串后端
     * @return 字符串的子串位置序列
     ***************************************************************************/
    public static int[] getSubStringPos(String str, String sub, boolean b) {
        String[] sp;
        int l = sub.length();
        sp = splitString(str, sub);
        if (sp == null) {
            return null;
        }
        int[] ip = new int[sp.length - 1];
        for (int i = 0; i < sp.length - 1; i++) {
            ip[i] = sp[i].length() + l;
            if (i != 0) {
                ip[i] += ip[i - 1];
            }
        }
        if (b) {
            for (int j = 0; j < ip.length; j++) {
                ip[j] = ip[j] - l;
            }
        }
        return ip;
    }

    /***************************************************************************
     * 根据正则表达式分割字符串
     *
     * @param str 源字符串
     * @param ms  正则表达式
     * @return 目标字符串组
     ***************************************************************************/
    public static String[] splitString(String str, String ms) {
        Pattern p = Pattern.compile(ms, Pattern.CASE_INSENSITIVE);
        return p.split(str);
    }

    /***************************************************************************
     * 根据正则表达式提取字符串,相同的字符串只返回一个
     *
     * @param str     源字符串
     * @param pattern 正则表达式
     * @return 符合条件的字符串数组
     ***************************************************************************/
    public static String[] getStringArrayByPattern(String str, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(str);
        Set<String> result = new HashSet<>();
        while (matcher.find()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                result.add(matcher.group(i));
            }
        }
        if (result.size() <= 0) {
            return null;
        }
        return (String[]) result.toArray();
    }

    /***************************************************************************
     * 得到第一个b,e之间的字符串,并返回e后的子串
     *
     * @param sourceString 源字符串
     * @param begin        标志开始
     * @param end          标志结束
     * @return before, e之间的字符串
     ***************************************************************************/
    public static String[] midString(String sourceString, String begin, String end) {
        int i = sourceString.indexOf(begin) + begin.length();
        int j = sourceString.indexOf(end, i);
        String[] sa = new String[2];
        if (i < begin.length() || j < i + 1 || i > j) {
            sa[1] = sourceString;
            sa[0] = null;
            return sa;
        } else {
            sa[0] = sourceString.substring(i, j);
            sa[1] = sourceString.substring(j);
            return sa;
        }
    }

    /***************************************************************************
     * 带有前一次替代序列的正则表达式替代
     ***************************************************************************/
    public static String stringReplace(String source, String pattern, String pb, int start) {
        Pattern pattern_hand = Pattern.compile(pattern);
        Matcher matcher_hand = pattern_hand.matcher(source);
        int gc = matcher_hand.groupCount();
        int pos = start;
        String sf1, sf2, sf3;
        int if1;
        String strr = "";
        while (matcher_hand.find(pos)) {
            sf1 = matcher_hand.group();
            if1 = source.indexOf(sf1, pos);
            if (if1 >= pos) {
                strr += source.substring(pos, if1);
                pos = if1 + sf1.length();
                sf2 = pb;
                for (int i = 1; i <= gc; i++) {
                    sf3 = "\\" + i;
                    sf2 = replaceAll(sf2, sf3, matcher_hand.group(i));
                }
                strr += sf2;
            } else {
                return source;
            }
        }
        strr = source.substring(0, start) + strr;
        return strr;
    }

    /***************************************************************************
     * 纯文本替换
     *
     * @param source  源字符串
     * @param pattern 子字符串
     * @param target  替换成的字符串
     * @return 替换后的字符串
     ***************************************************************************/
    public static String replaceAll(String source, String pattern, String target) {
        int i = 0, j;
        int l = pattern.length();
        boolean b = true;
        boolean o = true;
        String str = "";
        do {
            j = i;
            i = source.indexOf(pattern, j);
            if (i > j) {
                str += source.substring(j, i);
                str += target;
                i += l;
                o = false;
            } else {
                str += source.substring(j);
                b = false;
            }
        } while (b);
        if (o) {
            str = source;
        }
        return str;
    }

    /***************************************************************************
     * 判断是否与给定字符串样式匹配
     *
     * @param str     字符串
     * @param pattern 正则表达式样式
     * @return 是否匹配是true, 否false
     ***************************************************************************/
    public static boolean isMatch(String str, String pattern) {
        Pattern pattern_hand = Pattern.compile(pattern);
        Matcher matcher_hand = pattern_hand.matcher(str);
        return matcher_hand.matches();
    }

    /***************************************************************************
     * 截取字符串
     *
     * @param s   源字符串
     * @param jmp 跳过jmp
     * @param sb  取在sb
     * @param se  于se
     * @return 之间的字符串
     ***************************************************************************/
    public static String subStringExe(String s, String jmp, String sb, String se) {
        if (isEmpty(s)) {
            return "";
        }
        int i = s.indexOf(jmp);
        if (i >= 0 && i < s.length()) {
            s = s.substring(i + 1);
        }
        i = s.indexOf(sb);
        if (i >= 0 && i < s.length()) {
            s = s.substring(i + 1);
        }
        if (Objects.equals(se, "")) {
            return s;
        } else {
            i = s.indexOf(se);
            if (i >= 0 && i < s.length()) {
                s = s.substring(i + 1);
            }
            return s;
        }
    }

    /***************************************************************************
     * 用要通过URL传输的内容进行编码
     *
     * @param src 源字符串
     * @return 经过编码的内容
     ***************************************************************************/
    public static String URLEncode(String src) {
        String return_value = "";
        try {
            if (src != null) {
                return_value = URLEncoder.encode(src, "GBK");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return_value = src;
        }
        return return_value;
    }

    /***************************************************************************
     * 对字符串进行GBK编码
     *
     * @param str 需要解码的字符串
     * @return 经过解码的内容
     ***************************************************************************/
    public static String getGBK(String str) {
        return transfer(str);
    }

    public static String transfer(String str) {
        Pattern p = Pattern.compile("&#\\d+;");
        Matcher m = p.matcher(str);
        while (m.find()) {
            String old = m.group();
            str = str.replaceAll(old, getChar(old));
        }
        return str;
    }

    public static String getChar(String str) {
        String dest = str.substring(2, str.length() - 1);
        char ch = (char) Integer.parseInt(dest);
        return "" + ch;
    }

    /***************************************************************************
     * yahoo首页中切割字符串.
     *
     * @param subject 要切割的字符串
     * @param size    切割的长度
     * @return 切割后的字符串
     * @author yxg
     * @date 2007-09-17
     ***************************************************************************/
    public static String subYhooString(String subject, int size) {
        subject = subject.substring(1, size);
        return subject;
    }

    public static String subYhooStringDot(String subject, int size) {
        subject = subject.substring(1, size) + "...";
        return subject;
    }

    /***************************************************************************
     * 泛型方法(通用)，把list转换成以“,”相隔的字符串 调用时注意类型初始化（申明类型） 如：List<Integer> intList =
     * new ArrayList<Integer>(); 调用方法：StringUtil.listTtoString(intList);
     * 效率：list中4条信息，1000000次调用时间为850ms左右
     *
     * @param <T>  泛型
     * @param list list列表
     * @return 以“,”相隔的字符串
     * @author fengliang
     * @serialData 2008-01-09
     ***************************************************************************/
    public static <T> String listTtoString(List<T> list) {
        if (list == null || list.size() < 1)
            return "";
        Iterator<T> i = list.iterator();
        if (!i.hasNext())
            return "";
        StringBuilder sb = new StringBuilder();
        for (; ; ) {
            T e = i.next();
            sb.append(e);
            if (!i.hasNext())
                return sb.toString();
            sb.append(",");
        }
    }

    /***************************************************************************
     * 把整形数组转换成以“,”相隔的字符串
     *
     * @param a 数组a
     * @return 以“,”相隔的字符串
     * @author fengliang
     * @serialData 2008-01-08
     ***************************************************************************/
    public static String intArraytoString(int[] a) {
        if (a == null)
            return "";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "";
        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(a[i]);
            if (i == iMax)
                return b.toString();
            b.append(",");
        }
    }

    /***************************************************************************
     * 判断文字内容重复
     *
     * @param content 需要判断的文字内容
     * @return 是否包含重复的内容
     ***************************************************************************/
    public static boolean isContentRepeat(String content) {
        int similarNum = 0;
        int forNum;
        int subNum;
        int thousandNum;
        String startStr;
        String nextStr;
        boolean result = false;
        float endNum;
        if (content != null && content.length() > 0) {
            if (content.length() % 1000 > 0)
                thousandNum = (int) Math.floor(content.length() / 1000) + 1;
            else
                thousandNum = (int) Math.floor(content.length() / 1000);
            if (thousandNum < 3)
                subNum = 100 * thousandNum;
            else if (thousandNum < 6)
                subNum = 200 * thousandNum;
            else if (thousandNum < 9)
                subNum = 300 * thousandNum;
            else
                subNum = 3000;
            for (int j = 1; j < subNum; j++) {
                if (content.length() % j > 0)
                    forNum = (int) Math.floor(content.length() / j) + 1;
                else
                    forNum = (int) Math.floor(content.length() / j);
                if (result || j >= content.length())
                    break;
                else {
                    for (int m = 0; m < forNum; m++) {
                        if (m * j > content.length()
                                || (m + 1) * j > content.length()
                                || (m + 2) * j > content.length())
                            break;
                        startStr = content.substring(m * j, (m + 1) * j);
                        nextStr = content.substring((m + 1) * j, (m + 2) * j);
                        if (startStr.equals(nextStr)) {
                            similarNum = similarNum + 1;
                            endNum = (float) similarNum / forNum;
                            if (endNum > 0.4) {
                                result = true;
                                break;
                            }
                        } else
                            similarNum = 0;
                    }
                }
            }
        }
        return result;
    }

    /***************************************************************************
     * Ascii转为String
     *
     * @param asc 需要转化成字符的ascii code
     * @return 转化后的字符串
     ***************************************************************************/
    public static String asciiToString(int asc) {
        String TempStr;
        char tempchar = (char) asc;
        TempStr = String.valueOf(tempchar);
        return TempStr;
    }

    /***************************************************************************
     * 判断是否是空字符串 null和"" null返回result,否则返回字符串
     ***************************************************************************/
    public static String isEmpty(String s, String result) {
        if (s != null && !s.equals("")) {
            return s;
        }
        return result;
    }

    /***************************************************************************
     * 把字节码转换成16进制
     ***************************************************************************/
    public static String byteToHex(byte bytes[]) {
        StringBuffer retString = new StringBuffer();
        for (byte aByte : bytes) {
            retString.append(Integer.toHexString(0x0100 + (aByte & 0x00FF))
                    .substring(1).toUpperCase());
        }
        return retString.toString();
    }

    /***************************************************************************
     * 把16进制转换成字节码
     *
     * @param hex 16进制字符串
     * @return 字节码
     ***************************************************************************/
    public static byte[] hexToByte(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bts;
    }

    /***************************************************************************
     * 转换数字为固定长度的字符串,前方补充0
     *
     * @param data   传入的数值
     * @param length 希望返回的字符串长度
     * @return 转化后的字符串
     ***************************************************************************/
    public static String getStringByInt(String data, int length) {
        String s_data = "";
        int datalength = data.length();
        if (length > 0 && length >= datalength) {
            for (int i = 0; i < length - datalength; i++) {
                s_data += "0";
            }
            s_data += data;
        }
        return s_data;
    }

    /***************************************************************************
     * 判断是否位数字,并可为空
     *
     * @param src 源字符串
     * @return 是否为数字或者null
     ***************************************************************************/
    public static boolean isNumericAndCanNull(String src) {
        Pattern numericPattern = Pattern.compile("^[0-9]+$");
        if (src == null || src.equals(""))
            return true;
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /***************************************************************************
     * 是否表示浮点型数字或者为null
     *
     * @param src 需要判断的字符串
     * @return 空或者为浮点型返回true，否则返回false
     ***************************************************************************/
    public static boolean isFloatAndCanNull(String src) {
        Pattern numericPattern = Pattern
                .compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
        if (src == null || src.equals(""))
            return true;
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isDate(String date) {
        String regEx = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);
        return m.find();
    }

    public static boolean isFormatDate(String date, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);
        return m.find();
    }

    /***************************************************************************
     * 根据指定整型list 组装成为一个字符串
     *
     * @param list 源list
     * @return 组装后的字符串
     ***************************************************************************/
    public static String listToString(List<Integer> list) {
        String str = "";
        if (list != null && list.size() > 0) {
            for (int id : list) {
                str = str + id + ",";
            }
            if (!"".equals(str) && str.length() > 0)
                str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /***************************************************************************
     * 全角字符变半角字符
     *
     * @param str 要转化的字符串
     * @return 转化后的字符串
     ***************************************************************************/
    public static String fullToHalf(String str) {
        if (str == null || "".equals(str))
            return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 65281 && c < 65373)
                sb.append((char) (c - 65248));
            else
                sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /***************************************************************************
     * 全角括号转为半角
     *
     * @param str 要转换的字符串
     * @return 转换后的字符串
     * @author shazao
     * @date 2007-11-29
     ***************************************************************************/
    public static String replaceBracketStr(String str) {
        if (str != null && str.length() > 0) {
            str = str.replaceAll("（", "(");
            str = str.replaceAll("）", ")");
        }
        return str;
    }

    /***************************************************************************
     * 分割字符，从开始到第一个split字符串为止
     *
     * @param src   源字符串
     * @param split 截止字符串
     * @return 截取后的字符串
     ***************************************************************************/
    public static String subStr(String src, String split) {
        if (!isEmpty(src)) {
            int index = src.indexOf(split);
            if (index >= 0) {
                return src.substring(0, index);
            }
        }
        return src;
    }

    /***************************************************************************
     * 取url里的keyword（可选择参数）参数，用于整站搜索整合
     *
     * @param params  要提取的参数
     * @param qString 要提取参数的URL
     * @return 此参数的值
     * @author huoshao
     ***************************************************************************/
    public static String getKeyWord(String params, String qString) {
        String keyWord = "";
        if (qString != null) {
            String param = params + "=";
            int i = qString.indexOf(param);
            if (i != -1) {
                int j = qString.indexOf("&", i + param.length());
                if (j > 0) {
                    keyWord = qString.substring(i + param.length(), j);
                }
            }
        }
        return keyWord;
    }

    /***************************************************************************
     * 解析字符串返回map键值对(例：a=1&b=2 => a=1,b=2)
     *
     * @param query   源参数字符串
     * @param split1  键值对之间的分隔符（例：&）
     * @param split2  key与value之间的分隔符（例：=）
     * @param dupLink 重复参数名的参数值之间的连接符，连接后的字符串作为该参数的参数值，可为null
     *                null：不允许重复参数名出现，则靠后的参数值会覆盖掉靠前的参数值。
     * @return map
     * @author sky
     ***************************************************************************/
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseQuery(
            String query,
            char split1,
            char split2, String dupLink) {
        if (isNotEmpty(query) && query.indexOf(split2) > 0) {
            Map<String, String> result = new HashMap();
            String name = null;
            String value = null;
            String tempValue;
            int len = query.length();
            for (int i = 0; i < len; i++) {
                char c = query.charAt(i);
                if (c == split2) {
                    value = "";
                } else if (c == split1) {
                    if (!isEmpty(name) && value != null) {
                        if (dupLink != null) {
                            tempValue = result.get(name);
                            if (tempValue != null) {
                                value += dupLink + tempValue;
                            }
                        }
                        result.put(name, value);
                    }
                    name = null;
                    value = null;
                } else if (value != null) {
                    value += c;
                } else {
                    name = (name != null) ? (name + c) : "" + c;
                }
            }
            if (isNotEmpty(name) && isNotEmpty(value)) {
                if (isNotEmpty(dupLink)) {
                    tempValue = result.get(name);
                    if (tempValue != null) {
                        value += dupLink + tempValue;
                    }
                }
                result.put(name, value);
            }
            return result;
        }
        return null;
    }

    /***************************************************************************
     * 将list 用传入的分隔符组装为String
     *
     * @return String
     ***************************************************************************/
    @SuppressWarnings("unchecked")
    public static String listToStringSlipStr(List list, String slipStr) {
        StringBuffer returnStr = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (Object aList : list) {
                returnStr.append(aList).append(slipStr);
            }
        }
        if (returnStr.toString().length() > 0)
            return returnStr.toString().substring(0,
                    returnStr.toString().lastIndexOf(slipStr));
        else
            return "";
    }

    /***************************************************************************
     * 获取从start开始用*替换len个长度后的字符串
     *
     * @param str   要替换的字符串
     * @param start 开始位置
     * @param len   长度
     * @return 替换后的字符串
     ***************************************************************************/
    public static String getMaskStr(String str, int start, int len) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (str.length() < start) {
            return str;
        }
        // 获取*之前的字符串
        String ret = str.substring(0, start);
        // 获取最多能打的*个数
        int strLen = str.length();
        if (strLen < start + len) {
            len = strLen - start;
        }
        // 替换成*
        for (int i = 0; i < len; i++) {
            ret += "*";
        }
        // 加上*之后的字符串
        if (strLen > start + len) {
            ret += str.substring(start + len);
        }
        return ret;
    }

    /***************************************************************************
     * 截取字符串
     *
     * @param str  原始字符串
     * @param len  要截取的长度
     * @param tail 结束加上的后缀
     * @return 截取后的字符串
     ***************************************************************************/
    public static String getHtmlSubString(String str, int len, String tail) {
        if (str == null || str.length() <= len) {
            return str;
        }
        int length = str.length();
        char c;
        String tag;
        String name;
        int size;
        String result = "";
        boolean isTag = false;
        List<String> tags = new ArrayList<>();
        int i = 0;
        for (int end = 0, spanEnd; i < length && len > 0; i++) {
            c = str.charAt(i);
            if (c == '<') {
                end = str.indexOf('>', i);
            }
            if (end > 0) {
                // 截取标签  
                tag = str.substring(i, end + 1);
                int n = tag.length();
                if (tag.endsWith("/>")) {
                    isTag = true;
                } else if (tag.startsWith("</")) { // 结束符  
                    name = tag.substring(2, end - i);
                    size = tags.size() - 1;
                    // 堆栈取出html开始标签  
                    if (size >= 0 && name.equals(tags.get(size))) {
                        isTag = true;
                        tags.remove(size);
                    }
                } else { // 开始符  
                    spanEnd = tag.indexOf(' ', 0);
                    spanEnd = spanEnd > 0 ? spanEnd : n;
                    name = tag.substring(1, spanEnd);
                    if (name.trim().length() > 0) {
                        // 如果有结束符则为html标签  
                        spanEnd = str.indexOf("</" + name + ">", end);
                        if (spanEnd > 0) {
                            isTag = true;
                            tags.add(name);
                        }
                    }
                }
                // 非html标签字符  
                if (!isTag) {
                    if (n >= len) {
                        result += tag.substring(0, len);
                        break;
                    } else {
                        len -= n;
                    }
                }
                result += tag;
                isTag = false;
                i = end;
                end = 0;
            } else { // 非html标签字符  
                len--;
                result += c;
            }
        }
        // 添加未结束的html标签  
        for (String endTag : tags) {
            result += "</" + endTag + ">";
        }
        if (i < length) {
            result += tail;
        }
        return result;
    }
}
