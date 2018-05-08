package com.zz.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 散列工具类
 */
public class HashUtil {

    private static Logger logger = LoggerFactory.getLogger(HashUtil.class);

    /**
     * 对字符串进行MD5消息摘要
     *
     * @param stringForHash 待处理字符串
     * @return MD5消息摘要后的HEX字符串，若待处理字符串为<code>null</code>则返回空字符串<code>""</code>
     */
    public static String hashMD5(String stringForHash) {
        return hash(stringForHash, "MD5");
    }

    /**
     * 对字符串进行SHA1消息摘要
     *
     * @param stringForHash 待处理字符串
     * @return SHA1消息摘要后的HEX字符串，若待处理字符串为<code>null</code>则返回空字符串<code>""</code>
     */
    public static String hashSHA1(String stringForHash) {
        return hash(stringForHash, "SHA-1");
    }

    /**
     * 对字符串进行SHA256消息摘要
     *
     * @param stringForHash 待处理字符串
     * @return SHA256消息摘要后的HEX字符串，若待处理字符串为<code>null</code>则返回空字符串<code>""</code>
     */
    public static String hashSHA256(String stringForHash) {
        return hash(stringForHash, "SHA-256");
    }

    /**
     * 对字符串进行消息摘要。
     * <p>
     * <p>
     * 支持MD5、SHA-1、SHA-256三种摘要算法。若algorithm参数为null或非标准算法名字，则默认使用SHA-256。
     *
     * @param stringForHash 待处理字符串
     * @param algorithm     摘要算法的字符串表示
     * @return 消息摘要后的HEX字符串，若待处理字符串为<code>null</code>则返回空字符串<code>""</code>
     */
    public static String hash(String stringForHash, String algorithm) {
        if (stringForHash == null) {
            return "";
        }
        // 规范算法命名
        String useAlgorithm = (algorithm != null) ? algorithm.toUpperCase().replaceAll("-", "") : "SHA256";
        MessageDigest messageDigest;
        byte[] strByteArray;
        try {
            switch (useAlgorithm) {
                case "SHA1":
                case "SHA-1":
                case "sha1":
                case "sha-1":
                    messageDigest = MessageDigest.getInstance("SHA-1");
                    break;
                case "MD5":
                case "md5":
                    messageDigest = MessageDigest.getInstance("MD5");
                    break;
                case "SHA256":
                default:
                    messageDigest = MessageDigest.getInstance("SHA-256");
            }
            strByteArray = stringForHash.getBytes(StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException ignored) {
            // Java Runtime不支持该算法
            logger.error(String.format("Your Java Runtime Has No such hash algorithm as: %s", useAlgorithm), ignored);
            return "";
        }
        messageDigest.update(strByteArray);
        return ByteStringUtil.convertToLowerHexString(messageDigest.digest());
    }

}
