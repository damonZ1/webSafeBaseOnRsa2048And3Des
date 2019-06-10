package cn.damon.service.impl;

import cn.damon.Result;
import cn.damon.dto.ResolveDTO;
import cn.damon.service.IServerService;
import cn.damon.utils.JsonUtils;
import cn.damon.utils.RsaUtils;
import cn.damon.utils.TripleDESUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ServerServiceImpl
 * @Description
 * @Author Zhou Daoming
 * @Date 2019/6/6 15:35
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
@Service
public class ServerServiceImpl implements IServerService {

    @Value("${rsa2048.privatekey}")
    private String rsaPrivateKey;

    @Override
    public Result rightResolve(ResolveDTO dto) {
        //获取请求信息
        String encryptText = dto.getEncryptText();
        String rsaEncrypt = dto.getRsaEncrypt();
        if(StringUtils.isBlank(encryptText) || StringUtils.isBlank(rsaEncrypt)){
            return Result.fail("参数错误");
        }
        //解密rsa获取对称秘钥密文
        String randomKey = RsaUtils.rsaDecode(rsaPrivateKey, rsaEncrypt);
        if(StringUtils.isBlank(randomKey)){
            return Result.fail("解密错误,无法获得对称密钥");
        }
        //用解密获得的对称秘钥 解密密文
        String message = TripleDESUtils.decryptMode(encryptText, randomKey);
        if(StringUtils.isBlank(message)){
            return Result.fail("无法获取原文,解密错误");
        }
        System.out.println("解密后的消息是:"+message);
        return Result.ok(message);
    }
}
