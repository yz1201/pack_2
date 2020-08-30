package cn.dbdj1201.common.service.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-30 8:47
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("cn.dbdj1201.edu.controller"))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("在线教育平台对外接口")
                .description("1.提供**后台使用的接口 2.提供对其他服务调用的服务")
                .contact(new Contact("yz1201", "https://github.com/yz1201/pack_2", "15957121194@163.com"))
                .version("1.0")
                .build();
    }
}
