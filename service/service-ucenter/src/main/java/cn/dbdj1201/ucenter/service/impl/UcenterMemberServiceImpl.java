package cn.dbdj1201.ucenter.service.impl;

import cn.dbdj1201.common.service.exception.GOLException;
import cn.dbdj1201.common.utils.util.JwtInfo;
import cn.dbdj1201.common.utils.util.JwtUtils;
import cn.dbdj1201.common.utils.util.RedisUtil;
import cn.dbdj1201.ucenter.entity.UcenterMember;
import cn.dbdj1201.ucenter.entity.vo.LoginVo;
import cn.dbdj1201.ucenter.entity.vo.RegisterVo;
import cn.dbdj1201.ucenter.mapper.UcenterMemberMapper;
import cn.dbdj1201.ucenter.service.IUcenterMemberService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-06
 */
@Service
@Slf4j
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements IUcenterMemberService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String login(LoginVo loginVo) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //根据手机号跟密码做登录，所有要求这两个都不能为空
        if (StrUtil.isBlank(mobile) || StrUtil.isBlank(password)) {
            log.error("别传假信息┭┮﹏┭┮");
            throw new GOLException(20001, "登录失败");
        }

        //做登录
        UcenterMember one = this.getOne(new QueryWrapper<UcenterMember>()
                .eq("mobile", mobile));

        log.info("一个手机号码引发的血案o(￣▽￣)ｄ-{}", one);


        //根据电话查询是否有这条记录
        if (one == null) {
            log.error("查无此人┭┮﹏┭┮");
            throw new GOLException(20001, "登录失败");
        }
        //核对密码 ##之后会做加密处理，所以在此处分离开
        if (!SecureUtil.md5(password).equals(one.getPassword())) {
            log.error("密码校验失败o(一︿一+)o");
            throw new GOLException(20001, "密码校验失败o(一︿一+)o");
        }
        //判断用户是否被禁用
        if (one.getIsDisabled()) {
            log.error("back off(°ー°〃)");
            throw new GOLException(20001, "喷子(°ー°〃)");
        }

        return JwtUtils.generateJWT(new JwtInfo(one.getId(), one.getNickname(), one.getAvatar()), 60 * 60 * 24);
    }

    public static void main(String[] args) {
        System.out.println(JwtUtils.generateJWT(new JwtInfo("1302546754869010433", "Siijer", "https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLKn3pyoQZHjYZsqX5Np2TiaDO0OQuzicgxuGpmYnjJNHyW2NYKKOxEG7SMnQ5vdVJZs5ObzTErKp1A/132"), 60 * 60 * 24));
    }


    @Override
    public void register(RegisterVo registerVo) {
        /*
        注册，验证码的校验 -》添加记录到用户表，
         */

        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if (StrUtil.isBlank(code) ||
                StrUtil.isBlank(mobile) ||
                StrUtil.isBlank(nickname) ||
                StrUtil.isBlank(password)) {
            log.error("不填信息注册你🐎？");
            throw new GOLException(20001, "注册失败");
        }

        //验证码校验
        String redisCode = this.redisUtil.get("DBDJ1201::MSM::" + mobile) + "";
        log.info("code-{}, redisCode-{}", code, redisCode);

        if (!code.equals(redisCode)) {
            log.error("验证码错误");
            throw new GOLException(20001, "验证码错误");
        }

        //一个手机号注册一次，查持有这个号码的记录数就好了，你老惦记你那整条记录***？
        int count = this.count(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (count > 0) {
            log.error("该用户已经被注册了┌(。Д。)┐");
            throw new GOLException(20001, "该用户已经被注册了");
        }

        //封装数据，入库
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(SecureUtil.md5(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://dbdj1201-gol.oss-cn-beijing.aliyuncs.com/2020/09/04/3b101ebe5544418aa1e5c7fa3283b80emario.jpg");
        this.save(ucenterMember);
    }

    @Override
    public UcenterMember getOpenIdMember(String openId) {
        return this.getOne(new QueryWrapper<UcenterMember>().eq("openid", openId));
    }

    @Override
    public Integer findRegisterCountAtSomeday(String date) {
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return this.baseMapper.findRegisterCountAtSomeDay(date);
    }

}
