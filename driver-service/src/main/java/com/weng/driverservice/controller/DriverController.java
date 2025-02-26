package com.weng.driverservice.controller;


import com.weng.api.client.OrderClient;
import com.weng.api.client.PassengerClient;
import com.weng.api.dto.Order;
import com.weng.api.dto.OrderConfirmRequest;
import com.weng.api.dto.Passenger;
import com.weng.common.domain.Result;
import com.weng.common.utils.BaiduMapSearch;
import com.weng.common.utils.UserContext;
import com.weng.driverservice.domain.dto.LoginRequest;
import com.weng.driverservice.domain.dto.RegisterRequest;
import com.weng.driverservice.domain.entity.Driver;
import com.weng.driverservice.domain.vo.LoginVO;
import com.weng.driverservice.domain.vo.OrderVO;
import com.weng.driverservice.service.DriverService;
import com.weng.driverservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@Validated
public class DriverController {

    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final OrderClient orderClient;
    private final BaiduMapSearch baiduMapSearch;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginRequest loginRequest) {
        LoginVO loginVO = driverService.login(loginRequest);
        return Result.success(loginVO);
    }

    @PostMapping("/register")
    public Result<Long> register(@RequestBody RegisterRequest registerRequest)
    {
        Long id = driverService.register(registerRequest);
        return Result.success(id);
    }

    /**
     * 搜索乘客已经添加的订单（订单状态为0等待中的）
     * 既要查询乘客的信息，又要查询订单的信息
     * @return
     */
    @GetMapping("/search")
    public Result<List<OrderVO>> searchRide(){
        List<OrderVO> orderVOList=driverService.searchRide();
        return Result.success(orderVOList);
    }

    /**
     * 获取当前登录的司机信息后，确认订单
     * @param orderConfirmRequest
     * @return
     */
    @PutMapping("/confirm")
    public Result<Boolean> confirmRide(@RequestBody OrderConfirmRequest orderConfirmRequest){
        Long driverId = UserContext.getUser();
        Driver driver = driverService.getById(driverId);
        orderConfirmRequest = new OrderConfirmRequest(orderConfirmRequest.id(), driverId, driver.getVehicleId(), orderConfirmRequest.status());
        orderClient.confirmOrder(orderConfirmRequest);
        return Result.success(true);
    }

    /**
     * 直接根据传过来的订单id，完成订单，之后用户付款
     * @param orderId
     * @return
     */

    @PutMapping("/finish")
    public Result<Boolean> finishRide(Long orderId){
        orderClient.finishOrder(orderId);
        return Result.success(true);
    }

    /**
     * 乘客的订单被接收后，可以查看当前司机的位置
     */
    @GetMapping("/location")
    public Result<String> getCurrentLocation() throws Exception {
        String locationInfo = baiduMapSearch.requestGetAK();
        return Result.success(locationInfo);
    }

}
