package cn.dbdj1201.msm.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-06 8:38
 */
@Component
@ConfigurationProperties(prefix = "msm")
@Data
public class MsmProperties {

    //存入redis的头部
    private String keyHead;
    //存入redis的中间名
    private String keyMidName;

    private String aliKey;

    private String aliSecret;

}
