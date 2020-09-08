package cn.dbdj1201.statistics.api;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.statistics.api.fallback.EduFallbackFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author yz1201
 * @date 2020-09-08 22:41
 **/
@Component
@FeignClient(value = "service-edu", fallback = EduFallbackFeignClient.class)
public interface EduClient {

    @GetMapping("edu/course/getAddCourseCount/{date}")
    R getAddCourseCount(@PathVariable("date") String date);
}
