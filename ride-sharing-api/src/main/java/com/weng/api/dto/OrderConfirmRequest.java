package com.weng.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record OrderConfirmRequest(

        //修改订单需要订单id
        @NotNull(message = "订单id不能为空")
        Long id,

//        @NotNull(message = "司机id不能为空")
        //乘客id和车辆id是之后加上去的
        Long driverId,

//        @NotNull(message = "车辆id不能为空")
        Long vehicleId,

        @NotNull(message = "订单状态")
        Integer status
) {
}
