package com.netty.modules.user.bean;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class CurrentDate implements Serializable {

    // 当前时间
    private Date date;
}
