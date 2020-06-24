package com.netty.common.config;

import com.netty.common.utils.DateUtils;
import com.netty.modules.user.bean.User;
import com.netty.modules.user.mapper.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 是用Jap 初始化一些数据
 */
@Slf4j
@Component
public class ApplicationConfig implements  ApplicationRunner, CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    /**
     *  初始化 系统
     * @param args
     */
    @Override
    public void run(ApplicationArguments args){
        User user =  userRepository.findUserByLogoName("123");
        if(user == null){
            // ===初始化用户===
            user = new User();
            user.setLogoName("123");
            user.setPassword("123");
            Date date = new Date();
            user.setCreateDate(date);
            user.setUpdateDate(date);
            userRepository.save(user);
            log.info("系统用户初始化成功用户初始化成功:{}",user);
        }
    }

    @Override
    public void run(String... args) {
//        System.out.println("CommandLineRunner钩子被使用了");
    }

}
