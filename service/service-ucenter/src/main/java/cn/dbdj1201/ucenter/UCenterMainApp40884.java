package cn.dbdj1201.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-06 9:30
 */
@SpringBootApplication
@ComponentScan({"cn.dbdj1201"})
@EnableDiscoveryClient
public class UCenterMainApp40884 {

    public static void main(String[] args) {
        SpringApplication.run(UCenterMainApp40884.class, args);
    }
}
