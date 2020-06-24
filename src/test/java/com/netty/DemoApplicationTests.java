package com.netty;

import com.netty.modules.user.bean.CurrentDate;
import com.netty.modules.user.mapper.CurrentDateRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class DemoApplicationTests {
    @Autowired
    CurrentDateRepository currentDateRepository;

    @Test
    public void contextLoads() {
        CurrentDate currentDate = currentDateRepository.getCurrentDate();
        log.info("时间{}",currentDate);
    }

}
