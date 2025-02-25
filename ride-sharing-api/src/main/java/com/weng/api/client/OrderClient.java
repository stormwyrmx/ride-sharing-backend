package com.weng.api.client;

import com.weng.api.dto.OrderAddRequest;
import com.weng.api.dto.OrderConfirmRequest;
import com.weng.common.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

//指定远程地址
@FeignClient(name = "order-service")//这个 Feign 客户端将会向名为 "cart-service" 的服务发送请求
public interface OrderClient
{
    //指定请求方式
    @DeleteMapping("/carts")
    //指定结果返回，这里可以写在dto里     //指定携带数据
    void deleteCartItemByIds(@RequestParam("ids") Collection<Long> ids);

    /**
     * 乘客呼叫订单
     */
    @PostMapping("/add")
    void addOrder(@RequestBody OrderAddRequest orderAddRequest);

    /**
     * 司机接受订单
     * @param orderConfirmRequest
     * @return
     */
    @PutMapping("/confirm")
    void confirmOrder(@RequestBody OrderConfirmRequest orderConfirmRequest);

    /**
     * 司机结束订单
     * @param orderId
     * @return
     */
    @PutMapping("/finish")
    void finishOrder(Long orderId);

}
