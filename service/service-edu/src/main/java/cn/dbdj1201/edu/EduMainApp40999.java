package cn.dbdj1201.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-29 16:18
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.dbdj1201"})
public class EduMainApp40999 {

    public static void main(String[] args) {
        SpringApplication.run(EduMainApp40999.class, args);
    }
}
