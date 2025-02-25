package com.weng.driverservice.mapper;

import com.weng.driverservice.domain.entity.Vehicle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author weng
* @description 针对表【vehicle】的数据库操作Mapper
* @createDate 2025-02-25 13:34:02
* @Entity com.weng.driverservice.domain.entity.Vehicle
*/
@Mapper
public interface VehicleMapper extends BaseMapper<Vehicle> {

}




