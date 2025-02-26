package com.weng.api.client;

import com.weng.api.dto.Order;
import com.weng.api.dto.OrderAddRequest;
import com.weng.api.dto.OrderConfirmRequest;
import com.weng.common.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

//指定远程地址，不能加@RequestMapping
//但是可以通过path来为所有方法级别的映射添加统一的前缀，url则用于指定服务的绝对路径，会覆盖服务发现的地址
@FeignClient(name = "order-service",path = "/orders")//这个 Feign 客户端将会向名为 "order-service" 的服务发送请求
public interface OrderClient {
//
//    @DeleteMapping("/carts")
//        //指定携带数据
//    void deleteCartItemByIds(@RequestParam("ids") Collection<Long> ids);

    /**
     * 输入订单的状态后，查询订单
     */
    //指定请求方式
    @GetMapping("/list/{status}")
    //指定结果返回，这里可以写在dto里
    List<Order> listOrder(@PathVariable Integer status);

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
    //在 Feign 中，当使用 @RequestParam 或 @PathVariable 参数时，需要明确指定参数名称，否则参数值会丢失。
    void finishOrder(@RequestParam("orderId")Long orderId);

}
