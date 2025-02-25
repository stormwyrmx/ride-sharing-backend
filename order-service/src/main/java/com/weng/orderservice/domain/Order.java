package com.weng.orderservice.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @TableName order
 */
@TableName(value ="order")
@Data
@Builder
public class Order implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long passengerId;

    private Long driverId;

    private Long vehicleId;

    private String pickupLocation;

    private String destination;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}