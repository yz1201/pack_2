package cn.dbdj1201.order.api;

import cn.dbdj1201.common.utils.ordervo.CourseWebVoOrder;
import cn.dbdj1201.order.api.fallback.CourseDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-08 8:56
 */
@FeignClient(value = "service-edu", fallback = CourseDegradeFeignClient.class)
@Component
public interface CourseClient {
    @GetMapping("/edu/front/course/getCourseInfoOrder/{courseId}")
    CourseWebVoOrder getCourseById(@PathVariable("courseId") String courseId);
}
