package cn.dbdj1201.ucenter.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-06 10:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {

    //手机号
    private String mobile;
    //用户密码
    private String password;

}
