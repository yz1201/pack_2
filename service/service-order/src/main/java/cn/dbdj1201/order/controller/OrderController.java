package cn.dbdj1201.order.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.common.utils.util.JwtInfo;
import cn.dbdj1201.common.utils.util.JwtUtils;
import cn.dbdj1201.order.entity.Order;
import cn.dbdj1201.order.service.IOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-08
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
@Slf4j
@Api("订单模块")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    //生成订单
    @ApiOperation("生成订单")
    @PostMapping("/generateOrder/{courseId}")
    public R generateOrder(@PathVariable String courseId, HttpServletRequest request) {
        /*
            生成订单号，填充课程信息，讲师name，用户信息，订单其他信息
         */
        JwtInfo jwtInfo = JwtUtils.getMemberInfoByJWT(request);
        log.info("生成订单,courseId-{},jwtInfo-{}", courseId, jwtInfo);
        String orderNo = this.orderService.generateOrder(courseId, jwtInfo);
        return R.success().data("orderNo", orderNo);
    }

    //根据订单号查询订单信息
    @ApiOperation("根据订单no获取订单信息")
    @GetMapping("/getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable("orderNo") String orderNo) {
        log.info("根据订单号查询订单信息-{}", orderNo);
        Order order = this.orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        log.info("根据订单号查询订单完毕-{}", order);
        return R.success().data("item", order);
    }

    @ApiOperation("是否已购买")
    @GetMapping("isBought/{courseId}")
    public R isBought(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        JwtInfo userInfo = JwtUtils.getMemberInfoByJWT(request);
        Assert.notNull(userInfo, "没登陆？坏token");
        String userId = userInfo.getId();
        log.info("查询{}课程是否已经被{}购买", courseId, userId);
        boolean isBought = this.orderService.isBought(courseId, userId);
        return R.success().data("isBought", isBought);
    }

}
