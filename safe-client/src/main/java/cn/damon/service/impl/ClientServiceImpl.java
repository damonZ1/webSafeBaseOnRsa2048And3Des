package cn.damon.service.impl;

import cn.damon.Result;
import cn.damon.config.HttpUtils;
import cn.damon.service.IClientService;
import cn.damon.utils.DateUtils;
import cn.damon.utils.JsonUtils;
import cn.damon.utils.RsaUtils;
import cn.damon.utils.TripleDESUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ClientServiceImpl
 * @Description
 * @Author Zhou Daoming
 * @Date 2019/6/6 14:44
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
@Service
public class ClientServiceImpl implements IClientService{

    @Value("${rsa2048.publicKey}")
    private String rsaPublicKey;

    @Override
    public Result rightResolve() {
        Map<String,Object> data = new HashMap<>();
        data.put("name","damon");
        data.put("age",23);

        //原文本
        String plainText = JsonUtils.objectToJson(data);

        //获取随机的对称秘钥
        String randomKey = DateUtils.date2String(new Date(),"yyyy-MM-dd HH:mm:ss");

        Map<String,Object> requestData = new HashMap<>();
        //用随机秘钥对文本进行加密,获取 原文的密文
        String encryptText = TripleDESUtils.encryptMode(plainText, randomKey);

        System.out.println("randomKey:"+randomKey);
        System.out.println("rsa Key"+rsaPublicKey);
        //用rsa对随机秘钥进行加密 ，获取随机秘钥密文
        String rsaEncrypt = RsaUtils.rsaEncode(rsaPublicKey, randomKey);

        requestData.put("encryptText",encryptText);
        requestData.put("rsaEncrypt",rsaEncrypt);

        //传输给服务端
        String httpResponse = HttpUtils.doHttpPost(JsonUtils.objectToJson(requestData),"http://localhost:8090/rightResolve");
        if(StringUtils.isNotBlank(httpResponse)){
            return Result.ok(httpResponse);
        }
        return Result.fail("请求错误");
    }
}
