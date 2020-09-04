package cn.dbdj1201.vod.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-01 15:42
 */
@Component
@ConfigurationProperties(prefix = "aliyun.vod.file")
@Data
public class VodProperties {

    private String keyId;

    private String keySecret;

}
