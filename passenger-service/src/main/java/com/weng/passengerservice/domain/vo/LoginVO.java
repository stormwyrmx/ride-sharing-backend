package com.weng.passengerservice.domain.vo;

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