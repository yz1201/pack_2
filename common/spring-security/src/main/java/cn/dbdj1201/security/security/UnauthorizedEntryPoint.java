package cn.dbdj1201.security.security;

import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.common.utils.util.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 未授权的统一处理方式
 * @Author: dbdj1201
 * @Date: 2020-09-09
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {

        ResponseUtil.out(response, R.error().message("未经授权(ノへ￣、)"));
    }
}
