package cn.dbdj1201.common.service.exception;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.common.utils.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        log.error("你看看-{}",e.getMessage());
        return R.error().message("出了大问题了┭┮﹏┭┮");
    }

    //特定异常 先找特定异常 再找全局异常 合理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        log.error("你看看-{}",e.getMessage());
        return R.error().message("该背九九乘法表了吧O(∩_∩)O？");
    }

    //自定义异常
    @ExceptionHandler(GOLException.class)
    @ResponseBody //为了返回数据
    public R error(GOLException e) {
        /*
        这几句是想做啥？瞎了？ zz
        写入日志文件中
         */
        log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
