package com.weng.passengerservice.controller;

import com.weng.common.utils.BaiduMapSearch;
import org.springframework.web.util.UriUtils;
import com.weng.api.client.OrderClient;
import com.weng.common.domain.Result;
import com.weng.common.utils.UserContext;
import com.weng.passengerservice.domain.dto.LoginRequest;
import com.weng.api.dto.OrderAddRequest;
import com.weng.passengerservice.domain.dto.RegisterRequest;
import com.weng.passengerservice.domain.entity.Passenger;
import com.weng.passengerservice.domain.vo.LoginVO;
import com.weng.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;
    private final OrderClient orderClient;
    private final BaiduMapSearch baiduMapSearch;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginRequest loginRequest) {
        LoginVO loginVO = passengerService.login(loginRequest);
        return Result.success(loginVO);
    }

    @PostMapping("/register")
    public Result<Long> register(@RequestBody RegisterRequest registerRequest)
    {
        Long id = passengerService.register(registerRequest);
        return Result.success(id);
    }

    /**
     * 本质上是添加一个order，之后司机可以查询到对应的order
     * @return
     */
    @PostMapping("/add")
    public Result<Boolean> addRide(@RequestBody OrderAddRequest orderAddRequest){
        Long id = UserContext.getUser();
        orderAddRequest=OrderAddRequest.builder()
                .passengerId(id)
                .pickupLocation(orderAddRequest.pickupLocation())
                .destination(orderAddRequest.destination())
                .build();
        orderClient.addOrder(orderAddRequest);
        return Result.success();
    }

    /**
     * 根据ids批量查询乘客信息
     */
    @GetMapping("/listByIds")             //集合保存，要添加@RequestParam注解
    public List<Passenger> getPassengerByIds(@RequestParam("ids") List<Long>ids){
        return passengerService.listByIds(ids);
    }

    /**
     * 司机接受订单后，应该和对应乘客建立了实时连接
     * 获取当前乘客的信息和位置后可以传递司机
     */
    @GetMapping("/location")
    public Result<String> getCurrentLocation() throws Exception {
        String locationInfo = baiduMapSearch.requestGetAK();
        return Result.success(locationInfo);
    }

}
