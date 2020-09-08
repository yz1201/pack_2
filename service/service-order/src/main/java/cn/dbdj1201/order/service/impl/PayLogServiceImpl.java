package cn.dbdj1201.order.service.impl;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.order.WxPayProperties;
import cn.dbdj1201.order.entity.Order;
import cn.dbdj1201.order.entity.PayLog;
import cn.dbdj1201.order.mapper.PayLogMapper;
import cn.dbdj1201.order.service.IOrderService;
import cn.dbdj1201.order.service.IPayLogService;
import cn.dbdj1201.order.util.HttpClient;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-08
 */
@Service
@Slf4j
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements IPayLogService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private WxPayProperties wxPayProperties;

    @Override
    public Map<String, Object> createNative(String orderNo) {
        log.info("wx properties-{}", wxPayProperties);
        try {
            Order order = this.orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));
            //2 使用map设置生成二维码需要参数
            Map<String, String> m = new HashMap<>();
            m.put("appid", wxPayProperties.getAppid());
            m.put("mch_id", wxPayProperties.getPartner());
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号
            //误会了，微信文档规定单位是分，你本来0.01，属于无效请求，得先处理一下
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", wxPayProperties.getNotifyurl());
            m.put("trade_type", "NATIVE");

            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, wxPayProperties.getPartnerkey()));
            client.setHttps(true);
            //执行post请求发送
            client.post();

            //4 得到发送请求返回结果
            //返回内容，是使用xml格式返回
            String xml = client.getContent();

            //把xml格式转换map集合，把map集合返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            log.info("请求微信生成支付订单-{}", resultMap);
            //最终返回数据 的封装
            Map<String, Object> map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));        //二维码地址
            log.info("根据订单号生成的结果集为-{}", map);
            return map;
        } catch (Exception e) {
            log.error("生成验证码失败-{}", e.getMessage());
            e.printStackTrace();
            throw new GOLException(20001, "生成二维码失败");
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map<String, String> m = new HashMap<>();
            m.put("appid", wxPayProperties.getAppid());
            m.put("mch_id", wxPayProperties.getPartner());
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 发送httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, wxPayProperties.getPartnerkey()));
            client.setHttps(true);
            client.post();


            //3 得到请求返回内容
            String xml = client.getContent();
            //6、转成Map再返回
            return WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取失败状态失败-{}", e.getMessage());
            return null;
        }
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        /*
        对应订单表的支付状态state修改为1,不为1修改，为1，就算了。
         */
        String orderNo = map.get("out_trade_no");
        Order order = this.orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        if (order.getStatus().equals(1)) {
            return;
        }
        order.setStatus(1);
        this.orderService.updateById(order);
        /*
        增加支付表的记录
         */
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);  //订单号
        payLog.setPayTime(LocalDateTime.now()); //订单完成时间
        payLog.setPayType(1);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额(分)

        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id")); //流水号
        payLog.setAttr(JSONObject.toJSONString(map));
        this.save(payLog);
        log.info("入库的支付日志为-{}", payLog);
    }
}
