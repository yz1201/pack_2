package cn.dbdj1201.edu.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-05 10:46
 */
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.BASIC;
    }
}
