package com.netty.modules.user.bean;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ToString
@Entity
@Table(name = "db_user")
@Data
public class User implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer Id;

        // 设备id
        @Column(length = 32)
        private String clientId;

        @Column(length = 32)
        private String logoName;

        @Column
        private String password;

        @Column
        private Date createDate;

        @Column
        private Date updateDate;

}
