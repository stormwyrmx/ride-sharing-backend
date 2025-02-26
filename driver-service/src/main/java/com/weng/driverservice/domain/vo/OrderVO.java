package com.weng.driverservice.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @TableName user
 */
@Builder
@Data
public class OrderVO {
    private Long id;
    private String passengerName;
    private String passengerPhone;
    private String pickupLocation;
    private String destination;
    private Date createTime;
}