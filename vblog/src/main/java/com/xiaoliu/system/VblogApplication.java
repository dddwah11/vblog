package com.xiaoliu.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: 61分
 * @date: 2022/7/29 13:22
 * @description:
 */
@SpringBootApplication
//@EnableSwagger2
@MapperScan("com.xiaoliu.system.mapper")
@EnableScheduling
public class VblogApplication {
    public static void main(String[] args) {
        SpringApplication.run(VblogApplication.class,args);
    }
}
