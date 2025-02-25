package com.weng.orderservice.controller;

import com.weng.api.dto.OrderAddRequest;
import com.weng.api.dto.OrderConfirmRequest;
import com.weng.common.domain.Result;
import com.weng.orderservice.service.OrderService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 乘客呼叫订单
     */
    @PostMapping("/add")
    public Result<Boolean> addOrder(@RequestBody OrderAddRequest orderAddRequest){
        orderService.addOrder(orderAddRequest);
        return Result.success();
    }

    /**
     * 司机接受订单
     * @param orderConfirmRequest
     * @return
     */
    @PutMapping("/confirm")
    public Result<Boolean> confirmOrder(@RequestBody OrderConfirmRequest orderConfirmRequest){
        orderService.confirmOrder(orderConfirmRequest);
        return Result.success();
    }

    /**
     * 司机结束订单
     * @param orderId
     * @return
     */
    @PutMapping("/finish")
    public Result<Boolean> finishOrder(Long orderId){
        orderService.finishOrder(orderId);
        return Result.success();
    }


}
