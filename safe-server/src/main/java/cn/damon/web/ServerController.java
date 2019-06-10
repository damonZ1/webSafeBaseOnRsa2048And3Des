package cn.damon.web;

import cn.damon.Result;
import cn.damon.dto.ResolveDTO;
import cn.damon.service.IServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName ServerController
 * @Description
 * @Author Zhou Daoming
 * @Date 2019/6/6 15:35
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/")
public class ServerController {

    @Autowired
    private IServerService serverService;

    @RequestMapping("rightResolve")
    public Result rightResolve(@RequestBody ResolveDTO dto){
        return serverService.rightResolve(dto);
    }
}
