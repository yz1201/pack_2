package cn.dbdj1201.common.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dbdj1201
 * @since
 */
@Data
public class MemberDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //会员id
    private String id;
    //手机号
    private String mobile;
    //昵称
    private String nickname;
}