package com.weng.passengerservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weng.common.domain.ResultCodeEnum;
import com.weng.common.exception.BusinessException;
import com.weng.common.properties.JwtProperties;
import com.weng.common.utils.JwtUtil;
import com.weng.passengerservice.domain.dto.LoginRequest;
import com.weng.api.dto.OrderAddRequest;
import com.weng.passengerservice.domain.dto.RegisterRequest;
import com.weng.passengerservice.domain.entity.Passenger;
import com.weng.passengerservice.domain.vo.LoginVO;
import com.weng.passengerservice.mapper.PassengerMapper;
import com.weng.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
* @author weng
* @description 针对表【passenger(乘客)】的数据库操作Service实现
* @createDate 2025-02-25 12:00:35
*/
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl extends ServiceImpl<PassengerMapper, Passenger>
    implements PassengerService {

    private final PassengerMapper passengerMapper;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;


    @Override
    public LoginVO login(LoginRequest loginRequest) {
        // 1.数据校验
        String username = loginRequest.username();
        String password = loginRequest.password();
        LambdaQueryWrapper<Passenger> passengerLambdaQueryWrapper= new LambdaQueryWrapper<>();
        // 2.根据用户名查询
        passengerLambdaQueryWrapper.eq(Passenger::getUsername, username);
        Passenger passenger = passengerMapper.selectOne(passengerLambdaQueryWrapper);
        Assert.notNull(passenger, "用户名错误");
        // 3.校验密码
        if (!password.equals(passenger.getPassword())) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR,"用户名或密码错误");
        }
        // 4.生成TOKEN
        Map<String,Object> claims=new HashMap<>();
        claims.put("id",passenger.getId());
        String token = jwtUtil.createJWT(jwtProperties.getSecretKey(), claims);
        // 5.封装VO返回
        return LoginVO.builder()
                .id(passenger.getId())
                .username(passenger.getUsername())
                .name(passenger.getName())
                .phone(passenger.getPhone())
                .token(token)
                .build();
    }

    @Override
    public Long register(RegisterRequest registerRequest) {
        //1.账号不能重复
        LambdaQueryWrapper<Passenger> passengerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        passengerLambdaQueryWrapper.eq(Passenger::getUsername, registerRequest.username());
        Long count = passengerMapper.selectCount(passengerLambdaQueryWrapper);
        if (count > 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR, "账号重复");
        }
        //2.存储到数据库
        Passenger passenger = Passenger.builder()
                .username(registerRequest.username())
                .password(registerRequest.password())
                .name(registerRequest.name())
                .phone(registerRequest.phone())
                .build();
        passengerMapper.insert(passenger);//如果插入失败，它会抛出异常.而不是返回一个负数
        //3.返回id
        return passenger.getId();
    }

}




