package com.weng.passengerservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weng.passengerservice.domain.dto.LoginRequest;
import com.weng.api.dto.OrderAddRequest;
import com.weng.passengerservice.domain.dto.RegisterRequest;
import com.weng.passengerservice.domain.entity.Passenger;
import com.weng.passengerservice.domain.vo.LoginVO;

/**
* @author weng
* @description 针对表【passenger(乘客)】的数据库操作Service
* @createDate 2025-02-25 12:00:35
*/
public interface PassengerService extends IService<Passenger> {

    LoginVO login(LoginRequest loginRequest);

    Long register(RegisterRequest registerRequest);

}
