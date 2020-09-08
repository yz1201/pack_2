package cn.dbdj1201.order.service.impl;

import cn.dbdj1201.common.utils.ordervo.CourseWebVoOrder;
import cn.dbdj1201.common.utils.ordervo.UcenterMemberOrder;
import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.common.utils.util.JwtInfo;
import cn.dbdj1201.order.api.CourseClient;
import cn.dbdj1201.order.api.UcenterClient;
import cn.dbdj1201.order.entity.Order;
import cn.dbdj1201.order.mapper.OrderMapper;
import cn.dbdj1201.order.service.IOrderService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-08
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String generateOrder(String courseId, JwtInfo jwtInfo) {
        /*
            生成订单号，填充课程信息，讲师name，用户信息，订单其他信息
         */
        Assert.notNull(jwtInfo, "没登陆怎么可能走到这呢？");

        UcenterMemberOrder userInfo = this.ucenterClient.getUserInfo(jwtInfo.getId());
        CourseWebVoOrder course = this.courseClient.getCourseById(courseId);
        Order order = new Order();
        //生成17位随机id
        order.setOrderNo(RandomUtil.randomNumbers(17));
        //设置课程信息
        order.setCourseCover(course.getCover());
        order.setCourseId(courseId);
        order.setCourseTitle(course.getTitle());
        order.setTeacherName(course.getTeacherName());

        //设置用户信息
        order.setMemberId(userInfo.getId());
        order.setNickname(userInfo.getNickname());
        order.setMobile(userInfo.getMobile());
        //设置订单其他信息
        order.setPayType(1);
        order.setStatus(0);
        log.info("生成的订单信息为-{}", order);
        this.save(order);
        return order.getOrderNo();
    }

    @Override
    public boolean isBought(String courseId, String userId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("course_id", courseId);
        orderQueryWrapper.eq("member_id", userId);
        orderQueryWrapper.eq("status", 1);
        int count = this.count(orderQueryWrapper);
        if (count < 1) {
            log.info("真没买过，消费一下吧");
            return false;
        }
        log.info("查询购买情况，购买记录数-{}", count);
        return true;
    }
}
