package com.xiaoliu.system.common.response;

//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 61分
 * @date: 2022/7/29 14:01
 * @description:
 */
@Data
public class Result {

//    @ApiModelProperty(value = "是否成功")
    private Boolean success;
//    @ApiModelProperty(value = "返回状态码")
    private Integer code;
//    @ApiModelProperty(value = "返回消息")
    private String message;
//    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

//    私有化构造器，
    private Result(){}

    public static Result ok(){
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMsg());
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR.getCode());
        result.setMessage(ResultCode.ERROR.getMsg());
        return result;
    }


    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }



}
