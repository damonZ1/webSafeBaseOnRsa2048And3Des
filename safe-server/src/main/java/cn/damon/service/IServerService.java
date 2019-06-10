package cn.damon.service;

import cn.damon.Result;
import cn.damon.dto.ResolveDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @ClassName IServerService
 * @Description
 * @Author Zhou Daoming
 * @Date 2019/6/6 15:35
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
public interface IServerService {
    /**
     * 正确解析密文
     * @param dto dto
     * @return 消息
     */
    Result rightResolve(@RequestBody ResolveDTO dto);
}
