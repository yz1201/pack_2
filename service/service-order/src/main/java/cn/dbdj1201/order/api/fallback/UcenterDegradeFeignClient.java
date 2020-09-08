package cn.dbdj1201.order.api.fallback;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.common.utils.ordervo.UcenterMemberOrder;
import cn.dbdj1201.order.api.UcenterClient;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-08 9:27
 */
@Component
public class UcenterDegradeFeignClient implements UcenterClient {
    @Override
    public UcenterMemberOrder getUserInfo(String id) {
        throw new GOLException(20001, "获取不到用户模块信息");
    }
}
