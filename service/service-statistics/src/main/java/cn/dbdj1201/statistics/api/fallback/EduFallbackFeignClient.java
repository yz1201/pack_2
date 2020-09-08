package cn.dbdj1201.statistics.api.fallback;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.statistics.api.EduClient;
import org.springframework.stereotype.Component;

/**
 * @author yz1201
 * @date 2020-09-08 22:42
 **/
@Component
public class EduFallbackFeignClient implements EduClient {
    @Override
    public R getAddCourseCount(String date) {
//        throw new GOLException(20001, "课程模块出问题了");
        return R.error().message("课程模块出问题了");
    }
}
