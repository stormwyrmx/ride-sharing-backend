package com.weng.driverservice.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @TableName driver
 */
@TableName(value ="driver")
@Data
@Builder
public class Driver implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String phone;

    private Long vehicleId;

    private Date createTime;

    private Date updateTime;


    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}