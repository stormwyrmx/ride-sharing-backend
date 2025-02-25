package com.weng.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weng.api.dto.OrderAddRequest;
import com.weng.api.dto.OrderConfirmRequest;
import com.weng.orderservice.domain.Order;

/**
* @author weng
* @description 针对表【order(订单表)】的数据库操作Service
* @createDate 2025-02-25 22:06:38
*/
public interface OrderService extends IService<Order> {

    void finishOrder(Long orderId);

    void confirmOrder(OrderConfirmRequest orderConfirmRequest);

    void addOrder(OrderAddRequest orderAddRequest);
}
