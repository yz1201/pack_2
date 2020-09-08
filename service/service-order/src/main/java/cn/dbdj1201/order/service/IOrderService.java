package cn.dbdj1201.order.service;

import cn.dbdj1201.common.utils.util.JwtInfo;
import cn.dbdj1201.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-08
 */
public interface IOrderService extends IService<Order> {

    String generateOrder(String courseId, JwtInfo jwtInfo);

    /**
     * 判断该课程是否被该用户购买过了
     * @param courseId
     * @param userId
     * @return
     */
    boolean isBought(String courseId, String userId);
}
