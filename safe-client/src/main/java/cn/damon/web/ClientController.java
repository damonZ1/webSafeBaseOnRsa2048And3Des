package cn.damon.web;

import cn.damon.Result;
import cn.damon.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName ClientController
 * @Description
 * @Author Zhou Daoming
 * @Date 2019/6/6 14:23
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @RequestMapping("rightResolve")
    public Result rightResolve(){
        return clientService.rightResolve();
    }
}
