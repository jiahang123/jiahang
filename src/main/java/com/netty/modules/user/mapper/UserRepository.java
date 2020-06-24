package com.netty.modules.user.mapper;


import com.netty.modules.user.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 根据用户登陆 名称查询用户信息
     * @param logoName
     * @return
     */
    User findUserByLogoName(String logoName);


    /**
     * 根据唯一标识查询用户信息
     * @param clientId
     * @return
     */
    User findUserByClientId(String clientId);
}
