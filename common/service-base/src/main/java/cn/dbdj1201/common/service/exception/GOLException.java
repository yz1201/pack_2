package cn.dbdj1201.common.service.exception;

import cn.dbdj1201.common.utils.result.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//自定义异常
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GOLException extends RuntimeException {
    private Integer code;//状态码
    private String msg;//异常信息

    public GOLException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public GOLException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GOLException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }
}