package com.weng.driverservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weng.common.domain.ResultCodeEnum;
import com.weng.common.exception.BusinessException;
import com.weng.common.properties.JwtProperties;
import com.weng.common.utils.JwtUtil;
import com.weng.driverservice.domain.dto.LoginRequest;
import com.weng.driverservice.domain.dto.RegisterRequest;
import com.weng.driverservice.domain.entity.Driver;
import com.weng.driverservice.domain.entity.Vehicle;
import com.weng.driverservice.domain.vo.LoginVO;
import com.weng.driverservice.mapper.VehicleMapper;
import com.weng.driverservice.service.DriverService;
import com.weng.driverservice.mapper.DriverMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
* @author weng
* @description 针对表【driver】的数据库操作Service实现
* @createDate 2025-02-25 12:01:42
*/
@Service
//@RequiredArgsConstructor
public class DriverServiceImpl extends ServiceImpl<DriverMapper, Driver>
    implements DriverService{

    @Resource
    private DriverMapper driverMapper;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private VehicleMapper vehicleMapper;

//    private final DriverMapper driverMapper;
//    private final JwtUtil jwtUtil;
//    private final JwtProperties jwtProperties;
//    private final VehicleMapper vehicleMapper;

    @Override
    public LoginVO login(LoginRequest loginRequest) {
        // 1.数据校验
        String username = loginRequest.username();
        String password = loginRequest.password();
        LambdaQueryWrapper<Driver> driverLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2.根据用户名查询
        driverLambdaQueryWrapper.eq(Driver::getUsername, username);
        Driver driver = driverMapper.selectOne(driverLambdaQueryWrapper);
        Assert.notNull(driver, "用户名错误");
        // 3.校验密码
        if (!password.equals(driver.getPassword())) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR,"用户名或密码错误");
        }
        // 4.生成TOKEN
        Map<String,Object> claims=new HashMap<>();
        claims.put("id",driver.getId());
        String token = jwtUtil.createJWT(jwtProperties.getSecretKey(), claims);
        // 5.封装VO返回
        return LoginVO.builder()
                .id(driver.getId())
                .username(driver.getUsername())
                .name(driver.getName())
                .phone(driver.getPhone())
                .token(token)
                .build();
    }

    @Override
    public Long register(RegisterRequest registerRequest) {
        //1.账号不能重复
        LambdaQueryWrapper<Driver> passengerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        passengerLambdaQueryWrapper.eq(Driver::getUsername, registerRequest.username());
        Long count = driverMapper.selectCount(passengerLambdaQueryWrapper);
        if (count > 0) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR, "账号重复");
        }
        //2.检查车辆id存在
        Vehicle vehicle = vehicleMapper.selectById(registerRequest.vehicleId());
        if (vehicle == null) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR, "车辆不存在");
        }
        //3.存储到数据库
        Driver driver = Driver.builder()
                .username(registerRequest.username())
                .password(registerRequest.password())
                .name(registerRequest.name())
                .phone(registerRequest.phone())
                .vehicleId(registerRequest.vehicleId())
                .build();
        driverMapper.insert(driver);//如果插入失败，它会抛出异常.而不是返回一个负数
        //4.返回id
        return driver.getId();
    }


}




