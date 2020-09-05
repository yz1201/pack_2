package cn.dbdj1201.msm.controller;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.msm.service.IMsmService;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yz1201
 * @date 2020-09-05 23:11
 **/
@RestController
@RequestMapping("/msm")
public class MsmController {

    @Autowired
    private IMsmService msmService;

    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        String code = RandomUtil.randomNumbers(6);
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = this.msmService.sendMsm(param, phone);
        if (isSend) {
            return R.success().message("发送成功");
        }

        return R.error().message("发送短信失败");
    }

}
