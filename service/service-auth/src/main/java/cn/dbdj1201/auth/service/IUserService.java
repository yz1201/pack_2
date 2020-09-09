package cn.dbdj1201.auth.service;

import cn.dbdj1201.auth.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-09
 */
public interface IUserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}
