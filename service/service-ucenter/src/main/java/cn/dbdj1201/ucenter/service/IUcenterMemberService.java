package cn.dbdj1201.ucenter.service;

import cn.dbdj1201.ucenter.entity.UcenterMember;
import cn.dbdj1201.ucenter.entity.vo.LoginVo;
import cn.dbdj1201.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-06
 */
public interface IUcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录
     *
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    /**
     * 判断用户表里是否存在该openId的用户
     *
     * @param openId
     * @return
     */
    UcenterMember getOpenIdMember(String openId);

    /**
     * 查询特定日期的注册人数
     *
     * @param date
     * @return
     */
    Integer findRegisterCountAtSomeday(String date);
}
