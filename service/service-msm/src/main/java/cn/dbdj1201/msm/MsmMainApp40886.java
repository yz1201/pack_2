package cn.dbdj1201.msm;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yz1201
 * @date 2020-09-05 23:09
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@ComponentScan({"cn.dbdj1201"})
@EnableDiscoveryClient
public class MsmMainApp40886 {
    public static void main(String[] args) {
        SpringApplication.run(MsmMainApp40886.class, args);
    }
}
