package cn.dbdj1201.order.service;

import cn.dbdj1201.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-08
 */
public interface IPayLogService extends IService<PayLog> {

    /**
     * 根据订单号生成微信支付二维码
     * @param orderNo
     * @return
     */
    Map<String, Object> createNative(String orderNo);

    /**
     * 查询订单支付状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     *
     * @param map
     */
    void updateOrderStatus(Map<String, String> map);
}
