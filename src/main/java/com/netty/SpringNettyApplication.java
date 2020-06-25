package com.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.Cacheable;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
@Cacheable
public class SpringNettyApplication{

    public static void main(String[] args) {
        SpringApplication.run(SpringNettyApplication.class, args);
    }


}
