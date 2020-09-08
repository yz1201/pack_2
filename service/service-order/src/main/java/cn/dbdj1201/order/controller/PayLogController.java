package cn.dbdj1201.order.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.order.service.IPayLogService;
import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-08
 */
@RestController
@RequestMapping("/payLog")
@CrossOrigin
@Slf4j
@Api("支付模块")
public class PayLogController {

    @Autowired
    private IPayLogService payLogService;

    //根据订单号生成微信支付二维码
    @ApiOperation("根据订单号生成微信支付二维码")
    @GetMapping("/createQrCode/{orderNo}")
    public R createQrCode(@PathVariable("orderNo") String orderNo) {
        log.info("根据订单号生成微信支付二维码-{}", orderNo);
        Map<String, Object> map = this.payLogService.createNative(orderNo);
        return R.success().data(map);
    }

    //查看订单支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    @ApiOperation("查看订单支付状态")
    public R queryPayStatus(@PathVariable("orderNo") String orderNo) {
        log.info("查看{}订单支付状态", orderNo);
        Map<String, String> map = this.payLogService.queryPayStatus(orderNo);
        /*
        没获取到结果集，支付出错
         */
        if (CollectionUtil.isEmpty(map)) {
            return R.error().message("支付出问题了");
        }

        /*
        支付状态为成功，增加支付日志表的记录，订单表state修改为1
         */
        if ("SUCCESS".equals(map.get("trade_state"))) {
            this.payLogService.updateOrderStatus(map);
            return R.success().message("支付成功ヾ(￣▽￣)Bye~Bye~");
        }
        return R.success().code(25000).message("支付中╰(*°▽°*)╯");
    }


}
