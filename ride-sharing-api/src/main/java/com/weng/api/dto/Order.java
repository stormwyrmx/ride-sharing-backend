package com.weng.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName order
 */
@Data
@Builder
public class Order implements Serializable {

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
}