package cn.dbdj1201.statistics.api;

import cn.dbdj1201.common.utils.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-08 15:56
 */
@Component
@Slf4j
public class UcenterDegradeFeignClient implements UcenterClient {

    @Override
    public R findRegisterCountAtSomeday(String date) {
        log.info("我犯了什么罪？{}", date);
        return R.error().message("用户模块出问题了(￣_,￣ )");
    }
}
