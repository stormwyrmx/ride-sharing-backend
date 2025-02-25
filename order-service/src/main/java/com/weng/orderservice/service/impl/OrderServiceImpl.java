package com.weng.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weng.api.dto.OrderAddRequest;
import com.weng.api.dto.OrderConfirmRequest;
import com.weng.orderservice.domain.Order;
import com.weng.orderservice.mapper.OrderMapper;
import com.weng.orderservice.service.OrderService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
* @author weng
* @description 针对表【order(订单表)】的数据库操作Service实现
* @createDate 2025-02-25 22:06:37
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService {

    @Mapper
    private OrderMapper orderMapper;

    @Override
    public void addOrder(OrderAddRequest orderAddRequest) {
        Order order = Order
                .builder()
                .passengerId(orderAddRequest.passengerId())
                .pickupLocation(orderAddRequest.pickupLocation())
                .destination(orderAddRequest.destination())
                .build();
        orderMapper.insert(order);
    }

    @Override
    public void confirmOrder(OrderConfirmRequest orderConfirmRequest) {
        Order order = Order.builder()
                .id(orderConfirmRequest.id())
                .driverId(orderConfirmRequest.driverId())
                .vehicleId(orderConfirmRequest.vehicleId())
                .status(orderConfirmRequest.status())
                .build();
        orderMapper.updateById(order);
    }

    @Override
    public void finishOrder(Long orderId) {
        orderMapper.updateById(Order.builder().id(orderId).status(2).build());
    }



}




