package cn.damon.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;


/**
 * @ClassName TripleDESUtils
 * @Description 3des工具类
 * @Author Damon
 * @Date 2019/6/6 11:15
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
public class TripleDESUtils {

    //定义加密算法，有DES、DESede(即3DES)、Blowfish
    private static final String ALGORITHM = "DESede";
    //ALGORITHM
    private static final String PASSWORD_CRYPT_KEY = "2019-06-06 10:55";

    /**
     * 加密方法 三步走
     * @param src 源数据的字节数组
     * @return 加密后的字节数组
     */
    private static byte[] encryptMode(byte[] src,String desKey) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(build3DesKey(desKey), ALGORITHM);
            //实例化负责加密/解密的Cipher工具类
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            //初始化为加密模式
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public static String encryptMode(String planText,String desKey){
        if(StringUtils.isBlank(planText)){
            return "";
        }
        byte[] bytes = encryptMode(planText.getBytes(),desKey);
        if(bytes != null && bytes.length > 0){
            return Base64.encodeBase64String(bytes);
        }
        return "";
    }

    public static String decryptMode(String encodeText,String desKey){
        if(StringUtils.isBlank(encodeText)){
            return "";
        }
        byte[] bytes = Base64.decodeBase64(encodeText);
        if(bytes == null || bytes.length == 0){
            return "";
        }
        byte[] decodeByte = decryptMode(bytes,desKey);
        if(decodeByte != null && decodeByte.length > 0){
            return new String(decodeByte);
        }
        return "";
    }


    /**
     * 解密函数 三步走
     * @param src 密文的字节数组
     * @return 解密后的字节数组
     */
    private static byte[] decryptMode(byte[] src,String desKey) {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(desKey), ALGORITHM);
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            //初始化为解密模式
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }


    /**
     *
     * @param keyStr 秘钥源，对于key 必须是192位，24字节
     * @return 根据自定义秘钥生成的真正秘钥的数组
     * @throws UnsupportedEncodingException 不支持的加密
     */
    private static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        //声明一个24位的字节数组，默认里面都是0
        byte[] key = new byte[24];
        //将字符串转成字节数组
        byte[] temp = keyStr.getBytes("UTF-8");
        // System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
        if(key.length > temp.length){
            //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        }else{
            //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }


    public static void main(String[] args) throws Exception {
        String planText = "1123345";
        System.out.println("明文:"+planText);
        String encodeText = encryptMode(planText,"2019-06-06");
        System.out.println("密文:"+encodeText);
        String plaText = decryptMode(encodeText,"2019-06-06");
        System.out.println("明文:"+plaText);
    }



}
