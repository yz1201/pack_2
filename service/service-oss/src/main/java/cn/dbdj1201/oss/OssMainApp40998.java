package cn.dbdj1201.oss;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


/**
 * @Author: dbdj1201
 * @Date: 2020-09-01 15:28
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@ComponentScan({"cn.dbdj1201"})
public class OssMainApp40998 {

    public static void main(String[] args) {
        SpringApplication.run(OssMainApp40998.class, args);
    }
}
