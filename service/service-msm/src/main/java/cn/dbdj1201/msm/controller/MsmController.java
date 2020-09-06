package cn.dbdj1201.msm.controller;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.common.utils.util.RedisUtil;
import cn.dbdj1201.msm.component.MsmProperties;
import cn.dbdj1201.msm.service.IMsmService;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yz1201
 * @date 2020-09-05 23:11
 **/
@RestController
@RequestMapping("/msm")
@Slf4j
@Api("发送短信模块")
@CrossOrigin
public class MsmController {

    @Autowired
    private IMsmService msmService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MsmProperties msmProperties;

    @ApiOperation("发送短信")
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        String key = msmProperties.getKeyHead() + "::" + msmProperties.getKeyMidName() + "::" + phone;

        // 从redis获取验证码，如果获取到直接返回
        String code = redisUtil.get(key) + "";
        if (StrUtil.isNotBlank(code)) {
            R.success();
        }

        /*
         redis中没缓存，就生成一个随机6位数字验证码，调用阿里云接口发送短信息。
         发送完成之后存入redis，key 按规格的key ，value就是code ，失效时间5min
         */
        code = RandomUtil.randomNumbers(6);
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = this.msmService.sendMsm(param, phone);
        if (isSend) {
            redisUtil.set(key, code, 5 * 60);
            return R.success().message("发送成功");
        }

        return R.error().message("发送短信失败");
    }

}
