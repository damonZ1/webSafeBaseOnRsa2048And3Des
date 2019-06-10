package cn.damon;

import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Result
 * @Description
 * @Author Zhou Daoming
 * @Date 2019/6/6 15:45
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
public class Result<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;


    private Result(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result ok(T data){
        return new Result<T>(HttpStatus.SC_OK,"success",data);
    }

    public static <T> Result ok(){
        return new Result<T>(HttpStatus.SC_OK,"success",null);
    }

    public static <T> Result fail(String msg){
        return new Result<T>(HttpStatus.SC_INTERNAL_SERVER_ERROR,msg,null);
    }


}
