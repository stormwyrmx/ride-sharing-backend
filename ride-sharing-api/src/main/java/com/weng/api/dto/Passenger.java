package com.weng.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName passenger
 */
@Data
@Builder
public class Passenger implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String name;

    private String phone;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

}