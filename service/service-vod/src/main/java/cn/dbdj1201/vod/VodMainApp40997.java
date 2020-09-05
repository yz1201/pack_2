package cn.dbdj1201.vod;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-04 18:57
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@ComponentScan({"cn.dbdj1201"})
@EnableDiscoveryClient
public class VodMainApp40997 {
    public static void main(String[] args) {
        SpringApplication.run(VodMainApp40997.class, args);
    }
}
