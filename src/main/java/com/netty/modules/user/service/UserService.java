package com.netty.modules.user.service;


import com.netty.modules.user.bean.User;

import java.util.List;

public interface UserService {

    /**
     * 查询所有用户信息
     * @return
     */
    List<User> findAll();

    /**
     *  增加或修改 用户
     * @param clientId
     * @return
     */
    void save(String clientId);
}
