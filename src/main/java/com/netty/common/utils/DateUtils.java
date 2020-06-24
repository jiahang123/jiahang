package com.netty.common.utils;

import com.netty.modules.user.bean.CurrentDate;
import com.netty.modules.user.mapper.CurrentDateRepository;

import java.util.Date;


public class DateUtils {

    /**
     * 获取数据库当前时间
     * 此方法只能在spring 容器启动的时候使用
     * @return
     */
    public static Date getDatabaseCurrent(){
        CurrentDateRepository currentDateRepository =   SpringContextHolder.getBean(CurrentDateRepository.class);
        CurrentDate currentDate = currentDateRepository.getCurrentDate();
        return currentDate.getDate();
    }
}
