package com.netty.modules.user.mapper;


import com.netty.modules.user.bean.CurrentDate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CurrentDateRepository {

    CurrentDate getCurrentDate();

}
