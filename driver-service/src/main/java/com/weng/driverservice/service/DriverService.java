package com.weng.driverservice.service;

import com.weng.driverservice.domain.dto.LoginRequest;
import com.weng.driverservice.domain.dto.RegisterRequest;
import com.weng.driverservice.domain.entity.Driver;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weng.driverservice.domain.vo.LoginVO;

/**
* @author weng
* @description 针对表【driver】的数据库操作Service
* @createDate 2025-02-25 12:01:42
*/
public interface DriverService extends IService<Driver> {

    LoginVO login(LoginRequest loginRequest);

    Long register(RegisterRequest registerRequest);
}
