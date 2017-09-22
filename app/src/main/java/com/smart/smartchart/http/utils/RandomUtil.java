package com.smart.smartchart.http.utils;

import java.security.SecureRandom;

/**
 * Created by linhe on 2016/8/24.
 */
public class RandomUtil {

    /**
     * 每位允许的字符
     */
    private static final String POSSIBLE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 随机数生成器
     */
    private static SecureRandom secureRandom = new SecureRandom();


    /**
     * 生产一个指定长度的随机字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(POSSIBLE_CHARS.charAt(secureRandom.nextInt(POSSIBLE_CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * 生产一个指定长度的随机字符数组
     *
     * @param length 字符串长度
     * @return
     */
    public static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int nextInt = secureRandom.nextInt(10);
            bytes[i] = Byte.parseByte(String.valueOf(nextInt));
        }
        return bytes;
    }

}
