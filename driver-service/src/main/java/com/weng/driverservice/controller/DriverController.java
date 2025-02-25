package com.weng.driverservice.controller;


import com.weng.api.client.OrderClient;
import com.weng.api.dto.OrderConfirmRequest;
import com.weng.common.domain.Result;
import com.weng.common.domain.ResultCodeEnum;
import com.weng.common.utils.UserContext;
import com.weng.driverservice.domain.dto.LoginRequest;
import com.weng.driverservice.domain.dto.RegisterRequest;
import com.weng.driverservice.domain.entity.Driver;
import com.weng.driverservice.domain.vo.LoginVO;
import com.weng.driverservice.service.DriverService;
import com.weng.driverservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@Validated
public class DriverController {

    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final OrderClient orderClient;

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

    @PutMapping("/confirm")
    public Result<Boolean> confirmRide(@RequestBody OrderConfirmRequest orderConfirmRequest){
        Long driverId = UserContext.getUser();
        Driver driver = driverService.getById(driverId);
        orderConfirmRequest = new OrderConfirmRequest(orderConfirmRequest.id(), driverId, driver.getVehicleId(), orderConfirmRequest.status());
        orderClient.confirmOrder(orderConfirmRequest);
        return Result.success(true);
    }

    @PutMapping("/finish")
    public Result<Boolean> finishRide(Long orderId){
        orderClient.finishOrder(orderId);
        return Result.success(true);
    }


//    /**
//     * 查询分配到的工单
//     */
//    @GetMapping("/task")
//    public Result<List<TaskVO>>getTask(@AuthenticationPrincipal Driver driver){
//        List<TaskVO> taskVOList = taskService.getTaskByDriverId(driver.getId());
//        return Result.success(taskVOList);
//    }
//
//    /**
//     * 执行工单,改变工单状态和司机路线状态
//     */
//    @PutMapping("/task/{taskId}")
//    public Result<Boolean> executeTask(@PathVariable Long taskId) {
//        //更新工单状态为2（已执行）
//        Task task = taskService.updateTaskStatus(taskId);
//        //添加司机的routeId
//        driverService.updateRouteId(task.getRouteId());
//        return Result.success(true);
//    }
//
//    /**
//     * 取消工单
//     */
//    @PutMapping("/task/cancel/{taskId}")
//    public Result<Boolean> cancelTask(@PathVariable Long taskId){
//        taskService.cancelTask(taskId);
//        return Result.success(true);
//    }
//
//
//    /**
//     * 执行工单后，获取当前登录司机信息
//     * @param driver
//     * @return
//     */
//    @GetMapping("/current")
//    public Result<DriverVO> getCurrentDriver(@AuthenticationPrincipal Driver driver) {
//        DriverVO driverVO = DriverVO.builder()
//                .id(driver.getId())
//                .username(driver.getUsername())
//                .name(driver.getName())
//                .phone(driver.getPhone())
//                .routeId(driver.getRouteId())
//                .number(routeMapper.selectById(driver.getRouteId()).getNumber())
//                .build();
//        return Result.success(driverVO);
//    }
//
//
//    /**
//     * 获取当前登录司机对应的路线表中各个站点的上车人数
//     * @param driver
//     * @return
//     */
//    @GetMapping("/route/geton")
//    public Result<Route> getRouteOn(@AuthenticationPrincipal Driver driver) {
//        Long routeId = driver.getRouteId();
//        Route route = routeMapper.selectById(routeId);
//        return switch (route.getNumber()) {
//            case 1, 2 -> Result.success(
//                    Route.builder()
//                            .changan(route.getChangan())
//                            .dongmen(route.getDongmen())
//                            .build());
//            case 3 -> Result.success(
//                    Route.builder()
//                            .youyi(route.getYouyi())
//                            .gaoxin(route.getGaoxin())
//                            .ziwei(route.getZiwei())
//                            .build());
//            case 4 -> Result.success(
//                    Route.builder()
//                            .youyi(route.getYouyi())
//                            .guojiyi(route.getGuojiyi())
//                            .build());
//            default -> Result.error(ResultCodeEnum.PARAMS_ERROR, "路线号错误");
//        };
//    }
//
//    /**
//     * 获取当前登录司机对应的路线表中各个站点的下车人数
//     * @param driver
//     * @return
//     */
//    @GetMapping("/route/getoff")
//    public Result<Route> getRouteOff(@AuthenticationPrincipal Driver driver) {
//        Long routeId = driver.getRouteId();
//        Route route = routeMapper.selectById(routeId);
//        return switch (route.getNumber()) {
//            case 1 -> Result.success(
//                    Route.builder()
//                            .ziwei(route.getZiwei())
//                            .gaoxin(route.getGaoxin())
//                            .laodong(route.getLaodong())
//                            .youyi(route.getYouyi())
//                            .build());
//            case 2 -> Result.success(
//                    Route.builder()
//                            .guojiyi(route.getGuojiyi())
//                            .laodong(route.getLaodong())
//                            .youyi(route.getYouyi())
//                            .build());
//            case 3,4 -> Result.success(
//                    Route.builder()
//                            .dongmen(route.getDongmen())
//                            .yun(route.getYun())
//                            .jiaoxi(route.getJiaoxi())
//                            .hai(route.getHai())
//                            .qixiang(route.getQixiang())
//                            .build());
//            default -> Result.error(ResultCodeEnum.PARAMS_ERROR, "路线号错误");
//        };
//    }
//
//    /**
//     * 每分钟上传司机当前位置，如果靠近站点，则给前端websocket语音播报提醒。
//     * @param locationAddRequest
//     * @return
//     */
//    @PostMapping("/location")
//    public Result<Boolean> addLocation(@RequestBody @Validated LocationAddRequest locationAddRequest,
//                                       @AuthenticationPrincipal Driver driver) throws IOException {
//        //存储driver的位置和速度
//        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
//        hashOperations.put("driver_"+driver.getId(), locationAddRequest.time(),
//                locationAddRequest.latitude()+","+locationAddRequest.longitude());
//        stringRedisTemplate.expire("driver_"+driver.getId(),2, TimeUnit.HOURS);
//        //判断是否到站，到站则websocket传给前端语音播报
//        driverService.checkStop(driver.getId(),locationAddRequest);
//        return Result.success(true);
//    }
//
//    /**
//     * 修改密码
//     */
//    @PutMapping("/password")
//    public Result<Boolean> changePassword(@Validated @RequestBody PasswordChangeRequest passwordChangeRequest){
//        driverService.changePassword(passwordChangeRequest);
//        return Result.success(true);
//    }
//
//    /**
//     * 获取所有司机信息
//     * @return
//     */
//    @GetMapping("/all")
//    public Result<List<DriverVO>> getAll(){
//        List<Driver> driverList = driverService.list();
//        List<DriverVO> driverVOList = driverList.stream().map(driver -> DriverVO.builder()
//                .id(driver.getId())
//                .username(driver.getUsername())
//                .name(driver.getName())
//                .build()).toList();
//        return Result.success(driverVOList);
//    }

}
