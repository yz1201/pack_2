package cn.dbdj1201.statistics.api;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.statistics.api.fallback.StaUcenterDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-08 15:44
 */
@FeignClient(value = "service-ucenter", fallback = StaUcenterDegradeFeignClient.class)
@Component
public interface StaUcenterClient {

    @GetMapping("/ucenter/member/registerCounts/{date}")
    R findRegisterCountAtSomeday(@PathVariable("date") String date);
}
