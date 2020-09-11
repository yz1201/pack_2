package cn.dbdj1201.ucenter.controller;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.common.utils.util.JwtInfo;
import cn.dbdj1201.common.utils.util.JwtUtils;
import cn.dbdj1201.ucenter.component.VxProperties;
import cn.dbdj1201.ucenter.entity.UcenterMember;
import cn.dbdj1201.ucenter.service.IUcenterMemberService;
import cn.dbdj1201.ucenter.utils.HttpClientUtils;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.net.www.http.HttpClient;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-06 15:35
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
@Slf4j
@Api("微信登录模块")
public class WxApiController {

    @Autowired
    private VxProperties vxProperties;

    @Autowired
    private IUcenterMemberService memberService;

    //生成微信二维码
    @ApiOperation("生成微信二维码")
    @GetMapping("/login")
    public String getWxCode() {
        //重定向到请求微信二维码的地址上去

        log.info("调用微信接口申请二维码");
        String url = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String gol = null;
        try {
            gol = String.format(url,
                    vxProperties.getAppId(),
                    URLEncoder.encode(vxProperties.getRedirectUrl(), "UTF-8"),
                    "gol");
        } catch (Exception e) {
            log.error("编码失败");
            e.printStackTrace();
        }
        //地址经过了url编码
        log.info("最终要重定向到的地址为-{}", gol);
        return "redirect:" + gol;
    }

    //获取扫描人信息，添加数据
    @GetMapping("callback")
    public String callback(String code, String state) {
        log.info("code-{},state-{}", code, state);
        String baseUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenAndOpenIdUrl = String.format(baseUrl,
                vxProperties.getAppId(),
                vxProperties.getApp_secret(),
                code);

        //测试hutool中的http util
        //        String accessTokenAndOpenIdTest = HttpUtil.get(accessTokenAndOpenIdUrl);
        //        log.info("http util test -{}", accessTokenAndOpenIdTest);

        String accessTokenAndOpenId;
        String accessToken;
        String openId;

        try {
            accessTokenAndOpenId = HttpClientUtils.get(accessTokenAndOpenIdUrl);
            log.info("http client get -{}", accessTokenAndOpenId);

            Gson gson = new Gson();
            HashMap accessTokenAndOpenIdMap = gson.fromJson(accessTokenAndOpenId, HashMap.class);
            accessToken = (String) accessTokenAndOpenIdMap.get("access_token");
            openId = (String) accessTokenAndOpenIdMap.get("openid");

            log.info("code换来的用户token-{}跟openid-{}", accessToken, openId);

            //获取到用户信息，openId nickname headimgurl 第一次需要将用户注册进会员表，第二次就不用再注册了
            UcenterMember ucenterMember = this.memberService.getOpenIdMember(openId);
            if (ucenterMember == null) {

                String userInfoBaseUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                String userInfoUrl = String.format(
                        userInfoBaseUrl,
                        accessToken,
                        openId
                );

                log.info("用授权数据调用微信接口获取用户信息");
                String userInfo = HttpClientUtils.get(userInfoUrl);
                log.info("userInfo-{}", userInfo);

                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");

                ucenterMember = new UcenterMember();
                ucenterMember.setAvatar(headimgurl);
                ucenterMember.setIsDisabled(false);
                ucenterMember.setNickname(nickname);
                ucenterMember.setOpenid(openId);
                log.info("根据微信用户信息注册-{}", ucenterMember);
                this.memberService.save(ucenterMember);
            }
            String jwt = JwtUtils.generateJWT(new JwtInfo(ucenterMember.getId(),
                    ucenterMember.getNickname(),
                    ucenterMember.getAvatar()), 24 * 60 * 60);
            //不存在cookie里，cookie跨域有问题
            return "redirect:http://localhost:3000?token=" + jwt;
        } catch (Exception e) {
            log.error("出问题了-{}", e.getMessage());
            e.printStackTrace();
            throw new GOLException(20001, "登录异常ヽ(✿ﾟ▽ﾟ)ノ");
        }
    }
}
