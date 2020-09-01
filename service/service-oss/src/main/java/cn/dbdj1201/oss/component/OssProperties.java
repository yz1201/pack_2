package cn.dbdj1201.oss.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-01 15:42
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")
@Data
public class OssProperties {

    private String endpoint;

    private String keyId;

    private String keySecret;

    private String bucketName;

}
