package com.netty.modules.user.service.impl;

import com.netty.common.utils.StringUtils;
import com.netty.modules.user.bean.User;
import com.netty.modules.user.mapper.UserRepository;

import com.netty.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 其中的bookRepository本身自带了基本增删改查方法以及分页
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository  userRepository;

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public void save(String clientId) {
        User user = userRepository.findUserByClientId(clientId);
        if(StringUtils.isNullOrEmpty(user)){
          user.setLogoName(clientId);
          user.setPassword("123");
          Date date = new Date();
          user.setUpdateDate(date);
          user.setUpdateDate(date);
          userRepository.save(user);
        }
    }
}
