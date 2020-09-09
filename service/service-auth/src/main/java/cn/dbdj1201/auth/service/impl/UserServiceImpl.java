package cn.dbdj1201.auth.service.impl;

import cn.dbdj1201.auth.entity.User;
import cn.dbdj1201.auth.mapper.UserMapper;
import cn.dbdj1201.auth.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User selectByUsername(String username) {
        return this.getOne(new QueryWrapper<User>().eq("username", username));
    }
}
