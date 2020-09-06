package cn.dbdj1201.ucenter.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-06 15:25
 */
@Component
@Data
@ConfigurationProperties(prefix = "wx.open")
public class VxProperties {
    //微信开发平台app id
    private String appId;
    //app 密钥
    private String app_secret;
    //重定向url
    private String redirectUrl;


}
