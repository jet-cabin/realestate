package com.jet.realestate.security.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Permission implements Serializable {
    private Long id;

    private Long pid;


    private String name;


    private String value;


    private String icon;


    private Integer type;


    private String uri;


    private Integer status;


    private Date createTime;


    private Integer sort;
}