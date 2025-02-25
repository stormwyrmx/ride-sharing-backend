package com.weng.driverservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotBlank(message = "用户名不能为空")
        @Pattern(regexp = "^\\d{10}$", message = "用户名格式不正确")
        String username,

        @NotBlank(message = "密码不能为空")
        @Pattern(regexp = "^[a-zA-Z0-9_]{4,30}$", message = "密码格式不正确")
        String password,

        @NotBlank(message = "姓名不能为空")
        String name,

        @NotBlank(message = "电话号不能为空")
        @Pattern(regexp = "^1[3-9]\\d{9}$", message = "电话号格式不正确")
        String phone,

        @NotNull(message = "车辆ID不能为空")
        Long vehicleId

) {
}
