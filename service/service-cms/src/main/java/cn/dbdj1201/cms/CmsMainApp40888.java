package cn.dbdj1201.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-05 16:46
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"cn.dbdj1201"})
@EnableHystrix
@EnableFeignClients
public class CmsMainApp40888 {

    public static void main(String[] args) {
        SpringApplication.run(CmsMainApp40888.class, args);
    }
}
