package cn.dbdj1201.order;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-08 10:59
 */
@Component
@ConfigurationProperties(prefix = "wx.pay")
@Data
public class WxPayProperties {
    //关联的公众号appid
    private String appid;
    //商户号
    private String partner;
    //商户key
    private String partnerkey;
    //回调地址
    private String notifyurl;
}
