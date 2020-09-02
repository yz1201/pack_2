package cn.dbdj1201.edu.controller;

import cn.dbdj1201.common.utils.result.R;
import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-01 9:12
 */
@RestController
@RequestMapping("/edu/user")
@Api("讲师后台管理登录模块")
@Slf4j
@CrossOrigin
public class EduLoginController {

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(){
        return R.success().data("token", RandomUtil.randomString(17));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public R getInfo(){
        return R.success().data("name","dbdj1201").data("avatar","https://dbdj1201-gol.oss-cn-beijing.aliyuncs.com/2020/09/02/6111b862a8574b30a8b315e0d9c1ddf2mario.jpg");
    }
}
