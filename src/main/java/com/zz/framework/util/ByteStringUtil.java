package com.zz.framework.util;

/**
 * 字节与字符串的转换工具
 */
public class ByteStringUtil {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 把字节数组格式化为16进制小写字符串显示。
     *
     * @param bytes 字节数组
     * @return 十六进制字符串小写
     */
    public static String convertToLowerHexString(byte[] bytes) {
        int len = bytes.length;
        StringBuilder builder = new StringBuilder(len * 2);
        // 把字节数组转换成十六进制的字符串形式
        for (byte hex : bytes) {
            builder.append(HEX_DIGITS[(hex >> 4) & 0x0f]);
            builder.append(HEX_DIGITS[hex & 0x0f]);
        }
        return builder.toString();
    }

    /**
     * 把字节数组格式化为16进制大写字符串显示。
     *
     * @param bytes 字节数组
     * @return 十六进制字符串大写
     */
    public static String convertToUpperHexString(byte[] bytes) {
        return ByteStringUtil.convertToLowerHexString(bytes).toUpperCase();
    }

}
