package cn.dbdj1201.bookmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-11 15:23
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.dbdj1201"})
@EnableDiscoveryClient
public class BookmarkMainApp48888 {

    public static void main(String[] args) {
        SpringApplication.run(BookmarkMainApp48888.class, args);
    }
}
