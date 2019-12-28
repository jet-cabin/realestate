package com.jet.realestate.security.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;

    private String username;

    private String password;

    private String address;

    private String phone;
    
    private String email;

    private String nickname;
    
    private String note;

    private Date createTime;

    private Date loginTime;

    private Integer status;
}
