package cn.dbdj1201.ucenter.mapper;

import cn.dbdj1201.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-06
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer findRegisterCountAtSomeDay(String date);
}
