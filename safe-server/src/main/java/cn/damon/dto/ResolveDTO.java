package cn.damon.dto;

/**
 * @ClassName ResolveDTO
 * @Description
 * @Author Zhou Daoming
 * @Date 2019/6/6 15:37
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
public class ResolveDTO {
    private String encryptText;

    private String rsaEncrypt;

    public String getEncryptText() {
        return encryptText;
    }

    public void setEncryptText(String encryptText) {
        this.encryptText = encryptText;
    }

    public String getRsaEncrypt() {
        return rsaEncrypt;
    }

    public void setRsaEncrypt(String rsaEncrypt) {
        this.rsaEncrypt = rsaEncrypt;
    }
}
