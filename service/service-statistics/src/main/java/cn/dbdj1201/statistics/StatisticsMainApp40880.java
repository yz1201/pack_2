package cn.dbdj1201.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-08 15:39
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.dbdj1201"})
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients
@EnableScheduling
public class StatisticsMainApp40880 {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsMainApp40880.class, args);
    }
}
