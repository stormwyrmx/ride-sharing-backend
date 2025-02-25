package com.weng.driverservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weng.driverservice.domain.entity.Vehicle;
import com.weng.driverservice.service.VehicleService;
import com.weng.driverservice.mapper.VehicleMapper;
import org.springframework.stereotype.Service;

/**
* @author weng
* @description 针对表【vehicle】的数据库操作Service实现
* @createDate 2025-02-25 13:34:02
*/
@Service
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle>
    implements VehicleService{

}




