package com.weng.orderservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weng.orderservice.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/**
* @author weng
* @description 针对表【order(订单表)】的数据库操作Mapper
* @createDate 2025-02-25 22:06:38
* @Entity generator.domain.Order
*/

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}




