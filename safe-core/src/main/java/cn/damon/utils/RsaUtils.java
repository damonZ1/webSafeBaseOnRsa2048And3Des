package cn.damon.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @ClassName TripleDESUtils
 * @Description rsa工具类
 * @Author Damon
 * @Date 2019/6/6 11:12
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
public class RsaUtils {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "PUBLICKEY";
    public static final String PRIVATE_KEY = "PRIVATEKEY";
    public static String publicKeyText = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr5oncgyKgEr9V8lPaCfbp1GIDKMNCH28e+Lai+qYVSg/JgCVp0AQ2vUmFtTuUfklFnmzgKq5L6QZiny6qrWNblEHLDKtwbP3DYTfg8oTRqNpBBhktqSV1j/Pm1sF3e78lZCDJrN28hv/s3mYAXT4TXfz/uUrGNtVRDxN4KxTY6nBTRJnHG1UbI24sMr9mdYRMVVH3Y2/mziZAK3Uam15TMdD3/l+q31rWTxsM+n//laSHCHOubAT1fc49Cog3Lkhmn5XizG3VQXLXTxpWIbwOE1rX4lK9RI0zVLet30e1svC78zLCfamicpC+3HzdbGwDx5MlvuRKkhrh1k4Ltw7NwIDAQAB";
    public static String privateKeyText = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCvmidyDIqASv1XyU9oJ9unUYgMow0Ifbx74tqL6phVKD8mAJWnQBDa9SYW1O5R+SUWebOAqrkvpBmKfLqqtY1uUQcsMq3Bs/cNhN+DyhNGo2kEGGS2pJXWP8+bWwXd7vyVkIMms3byG/+zeZgBdPhNd/P+5SsY21VEPE3grFNjqcFNEmccbVRsjbiwyv2Z1hExVUfdjb+bOJkArdRqbXlMx0Pf+X6rfWtZPGwz6f/+VpIcIc65sBPV9zj0KiDcuSGafleLMbdVBctdPGlYhvA4TWtfiUr1EjTNUt63fR7Wy8LvzMsJ9qaJykL7cfN1sbAPHkyW+5EqSGuHWTgu3Ds3AgMBAAECggEBAJL1gPOKmVK674DLYiA50zMZIbHptxuXFunbNamI4InMD+U+8f+xtyl3prPO6dBCoQ5JNbeFqUu8mv9yWUpKfJ2JRGZx10bX1PdEb0a9FBq24UljHM5ZuWg/qQBMUgGlY0/MTGZUJUSDuCA8r8DwYml67QEySzoXPSnJCifwMC2j3dTYapjozQVhaHXfL/SyPRgQFWDC3Xt9BR5DGHna5fiecmdOlGbNV1isCjmwx6gRD4Wd1iUm4cw9DYFOdIw/mGNnTuTchDk9XnGOpSI5MSwO/cwxYjesuJr4+SPEvqD8tKEh2GYR9Gx08AWxi3vut9reU/Nizpv754h7q4KQI3kCgYEA1VFLtCjnYc6JqzGQ3Jm30P9OANft6GgK5TOIhsEtxbldpz3ORZ41IKhFDRdz3vRkEfdMFCIY1/Dn6uwW6od9nsryETflgH3LETu739B/ObrfLIMh4HLYuzgLLTDpnRV/Sb2OyQ1A8vJTgDUj6TN21NfUOhMXtO1N5Dhq0n6NZdUCgYEA0rz5MLdhWGRSurZdwciM4av/3jVqARNJoUiwhxTQojz7YPI9iiObvxqkXChrbL81GnyRmkwtZxLSKU93ELzEVi4Ivzt2oK7KtTT75GPeh4YsExyAlz31H1CFHOrbzhbPnAV+DZmw7wV51HAzRV23TsIH4zmhfPE1TejveIVmptsCgYAyrdfAe59NG1aVt3blEhgbrSwJA0PgXSeJgI7FBQws13cqjoJ9A7M3iQKus42xwHot0oTem6MvWmqe42wmV/4+RZTxBkieGESwWXKvH8e6HW18E8NuoZpgEFaHbAF0cnEFQeW0WlO95Fh2MynO840d0BD5oyOUtnQ07jpiu/I36QKBgQCWx+UXyS2aW/WhaBg/Lao3UaR6LgeRi2ZUQrkjfW4llrJZ1BjZ/M5T9tEAq17paQmq0NQw7S5TIHFEY3oSfK2R59AUW4xuPYIGE0njSN2QZBym8+7Bx+/ZnvgPD6+Djmiu/nMJchzZeDaKHFli2UHt1QBlwPddeQwlmXO17OTcnwKBgECBlb6aSvUCdt4CKSYJvV4ZkQCq2+Ss5qKtl/32PmqCPZh5zBzaXY3i7QDl806pmGpdZ1kum4ssfAQsTuJ+J0tgkKHlnROR6jL9twP63ruE3+61SeSqBMvJyjWseZJrF1Y9CMlF30cFKuMXwu61fJMB53Xors3a6J2z1mRdHMRq";

    /**
     * RSA密钥长度必须是64的倍数，默认是1024
     */
    public static final int KEY_SIZE = 2048;

    private static final String PLAIN_TEXT = "MANUTD is the greatest club in the world";

    public static void main(String[] args) {
//        String planStr = "123456";
//        System.out.println("原文:"+planStr);
//        System.out.println("公钥："+publicKeyText);
//        String s = rsaEncode(publicKeyText, planStr);
//        System.out.println("密文:"+s);
//
//        System.out.println("私钥："+privateKeyText);
//        String s1 = rsaDecode(privateKeyText, s);
//        System.out.println("明文："+s1);
        generateKeyBytes();
    }

    /**
     * 生成密钥对
     *
     * @return 秘钥对map
     */
    private static void generateKeyBytes() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            System.out.println("公钥:" + Base64.encodeBase64String(publicKey.getEncoded()));
            System.out.println("私钥:" + Base64.encodeBase64String(privateKey.getEncoded()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param keyBytes 公钥数组
     * @return 真正符合规范的公钥
     */
    private static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            return factory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param keyBytes 秘钥数组
     * @return 真正符合规范的私钥
     */
    private static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            return factory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密，三步走。
     *
     * @param key       key公钥
     * @param plainText 原文
     * @return 密文数组
     */
    private static byte[] rsaEncode(PublicKey key, byte[] plainText) {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String rsaEncode(String publicKeyStr, String plainText) {
        if (StringUtils.isBlank(publicKeyStr) || StringUtils.isBlank(plainText)) {
            return "";
        }
        byte[] publicKeyByte = Base64.decodeBase64(publicKeyStr);
        PublicKey publicKey = restorePublicKey(publicKeyByte);
        return Base64.encodeBase64String(rsaEncode(publicKey, plainText.getBytes()));
    }

    public static String rsaDecode(String privateKeyStr, String encodeText) {
        if (StringUtils.isBlank(privateKeyStr) || StringUtils.isBlank(encodeText)) {
            return "";
        }
        byte[] privateKeyByte = Base64.decodeBase64(privateKeyStr);
        PrivateKey privateKey = restorePrivateKey(privateKeyByte);
        return rsaDecode(privateKey, Base64.decodeBase64(encodeText));
    }

    /**
     * 解密，三步走。
     *
     * @param key         key私钥
     * @param encodedText 原文
     * @return 原文数组
     */
    private static String rsaDecode(PrivateKey key, byte[] encodedText) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encodedText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}