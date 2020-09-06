package cn.dbdj1201.ucenter.controller;


import cn.dbdj1201.common.utils.result.R;
import cn.dbdj1201.common.utils.util.JwtInfo;
import cn.dbdj1201.common.utils.util.JwtUtils;
import cn.dbdj1201.ucenter.entity.UcenterMember;
import cn.dbdj1201.ucenter.entity.vo.LoginVo;
import cn.dbdj1201.ucenter.entity.vo.RegisterVo;
import cn.dbdj1201.ucenter.service.IUcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-06
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
@Slf4j
@Api("用户模块")
public class UcenterMemberController {

    @Autowired
    private IUcenterMemberService memberService;


    //登录
    @ApiOperation("登录")
    @PostMapping("/login")
    public R loginUser(@RequestBody LoginVo loginVo) {
        log.info("用户来啦┗( T﹏T )┛ -{}", loginVo);
        String token = this.memberService.login(loginVo);
        log.info("校验成功Ψ(￣∀￣)Ψ，返回token。-{}", token);
        return R.success().data("token", token);
    }

    //根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        log.info("根据token获取用户信息");
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJWT(request);
        log.info("获取到的用户信息为-{}", jwtInfo);
        return R.success().data("userInfo", jwtInfo);
    }

    //注册
    @ApiOperation("注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        log.info("新用户来咯( ﹁ ﹁ ) ~→ -{}", registerVo);
        this.memberService.register(registerVo);
        return R.success();
    }
}
