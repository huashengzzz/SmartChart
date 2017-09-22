package com.smart.smartchart.utils.encrypt;

import android.util.Base64;

import com.smart.smartchart.http.utils.RandomUtil;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES
 */
public class AES {
    // /** 算法/模式/填充 **/
    private static final String CipherMode = "AES/CBC/PKCS5Padding";

    /**
     * 生成一个AES密钥字符串
     *
     * @return
     */
    public static String generateKeyString() {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom());
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            return Base64.encodeToString(secretKeySpec.getEncoded(), Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(byte[] content, byte[] key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            if (null == iv) {
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            }
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过String类型的密钥加密String
     *
     * @return 16进制密文字符串
     */
    public static String encrypt(String content, String key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CipherMode);
        SecretKeySpec skeySpec = new SecretKeySpec(Base64.decode(key, Base64.DEFAULT), "AES");
        if (null == iv) {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        }
        byte[] data = cipher.doFinal(content.getBytes("UTF-8"));
        String encryptedText = Base64.encodeToString(data, Base64.DEFAULT);
        return encryptedText;
    }


    /**
     * 通过byte[]类型的密钥解密byte[]
     *
     * @param content
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] content, byte[] key, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            if (null == iv) {
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            }
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过String类型的密钥 解密String类型的密文
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key, IvParameterSpec iv) {
        String decryptedText = null;
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            SecretKeySpec keySpec = new SecretKeySpec(Base64.decode(key, Base64.DEFAULT), "AES");
            if (null == iv) {
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            }
            byte[] result = cipher.doFinal(Base64.decode(content, Base64.DEFAULT));
            decryptedText = new String(result, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }

    public static IvParameterSpec getIV() {
//        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        byte[] iv = RandomUtil.generateRandomBytes(16);
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

}