package cn.dbdj1201.edu.api.fallback;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.edu.api.UcenterClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yz1201
 * @date 2020-09-07 21:43
 **/
@Component
public class UcenterDegradeFeignClient implements UcenterClient {
    @Override
    public R getMemberInfo(HttpServletRequest request) {
        return R.error().message("用户中心跨了，撤退！");
    }
}
