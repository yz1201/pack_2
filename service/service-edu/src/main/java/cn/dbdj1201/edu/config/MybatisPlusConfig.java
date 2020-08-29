package cn.dbdj1201.edu.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-29 18:24
 */
@Configuration
@MapperScan("cn.dbdj1201.edu.mapper")
public class MybatisPlusConfig {

    /**
     * mybatis-plus的分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
