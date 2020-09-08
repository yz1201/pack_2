package cn.dbdj1201.order.api.fallback;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.common.utils.ordervo.CourseWebVoOrder;
import cn.dbdj1201.order.api.CourseClient;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-08 8:58
 */
@Component
public class CourseDegradeFeignClient implements CourseClient {
    @Override
    public CourseWebVoOrder getCourseById(String courseId) {
        throw new GOLException(20001, "获取不到课程信息┭┮﹏┭┮");
    }
}
