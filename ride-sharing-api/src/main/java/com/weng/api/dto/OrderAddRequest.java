package com.weng.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OrderAddRequest(

//        @NotNull(message = "乘客id不能为空")
        //乘客id是之后加上去的吧
        Long passengerId,

        @NotBlank(message = "起始地不能为空")
        String pickupLocation,

        @NotBlank(message = "终点不能为空")
        String destination

) {
}
