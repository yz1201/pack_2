package cn.dbdj1201.order.api;

import cn.dbdj1201.common.utils.ordervo.UcenterMemberOrder;
import cn.dbdj1201.order.api.fallback.UcenterDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-08 9:25
 */
@Component
@FeignClient(value = "service-ucenter", fallback = UcenterDegradeFeignClient.class)
public interface UcenterClient {

    @GetMapping("/ucenter/member/getUserInfo/{id}")
    UcenterMemberOrder getUserInfo(@PathVariable("id") String id);
}
