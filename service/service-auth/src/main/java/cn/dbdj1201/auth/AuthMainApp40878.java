package cn.dbdj1201.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-09 13:42
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"cn.dbdj1201"})
public class AuthMainApp40878 {
    public static void main(String[] args) {
        SpringApplication.run(AuthMainApp40878.class, args);
    }
}
