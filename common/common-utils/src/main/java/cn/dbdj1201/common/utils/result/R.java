package cn.dbdj1201.common.utils.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-30 8:59
 */
//统一返回结果的类
@Data
public class R {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "响应状态码")
    private Integer code;

    @ApiModelProperty(value = "响应消息")
    private String message;

    @ApiModelProperty(value = "响应实体")
    private Map<String, Object> data = new HashMap<>();

    //把构造方法私有
    private R() {}

    //成功静态方法
    public static R success() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    //失败静态方法
    public static R error() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return r;
    }

    //填充响应结果
    public static R setResult(ResultCodeEnum resultCodeEnum){
        R r = new R();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}

