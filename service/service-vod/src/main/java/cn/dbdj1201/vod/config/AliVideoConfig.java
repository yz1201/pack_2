package cn.dbdj1201.vod.config;

import cn.dbdj1201.vod.component.VodProperties;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-05 9:08
 */
@Configuration
public class AliVideoConfig {

    @Autowired
    private VodProperties vodProperties;

    @Bean
    public DefaultAcsClient defaultAcsClient() {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId,
                vodProperties.getKeyId(),
                vodProperties.getKeySecret());
        return new DefaultAcsClient(profile);
    }
}
