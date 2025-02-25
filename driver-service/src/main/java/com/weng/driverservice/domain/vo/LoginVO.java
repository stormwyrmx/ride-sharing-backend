package com.weng.driverservice.domain.vo;

import lombok.Builder;

/**
 * @TableName user
 */
@Builder
public record LoginVO(
        Long id,
        String username,
        String name,
        String phone,
        String token
) {
}