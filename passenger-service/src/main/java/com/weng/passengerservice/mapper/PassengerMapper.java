package com.weng.passengerservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weng.passengerservice.domain.entity.Passenger;
import org.apache.ibatis.annotations.Mapper;

/**
* @author weng
* @description 针对表【passenger(乘客)】的数据库操作Mapper
* @createDate 2025-02-25 12:00:35
* @Entity generator.domain.Passenger
*/
@Mapper
public interface PassengerMapper extends BaseMapper<Passenger> {

}




