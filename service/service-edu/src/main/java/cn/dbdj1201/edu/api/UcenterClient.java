package cn.dbdj1201.edu.api;

import cn.dbdj1201.common.utils.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yz1201
 * @date 2020-09-07 21:33
 **/
@FeignClient(value = "service-ucenter", fallback = UcenterDegradeFeignClient.class)
@Component
public interface UcenterClient {

    @GetMapping("/ucenter/member/getMemberInfo")
    R getMemberInfo(HttpServletRequest request);
}
