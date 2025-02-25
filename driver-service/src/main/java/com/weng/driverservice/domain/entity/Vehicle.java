package com.weng.driverservice.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName vehicle
 */
@TableName(value ="vehicle")
@Data
public class Vehicle implements Serializable {
    private Long id;

    private String licensePlate;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    private static final long serialVersionUID = 1L;
}