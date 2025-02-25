package com.weng.passengerservice.controller;


import com.weng.api.client.OrderClient;
import com.weng.common.domain.Result;
import com.weng.common.utils.UserContext;
import com.weng.passengerservice.domain.dto.LoginRequest;
import com.weng.api.dto.OrderAddRequest;
import com.weng.passengerservice.domain.dto.RegisterRequest;
import com.weng.passengerservice.domain.vo.LoginVO;
import com.weng.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
@Validated
public class PassengerController {

    private final PassengerService passengerService;
    private final OrderClient orderClient;


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
    @PostMapping("/search")
    public Result<Boolean> searchRide(@RequestBody OrderAddRequest orderAddRequest){
        Long id = UserContext.getUser();
        orderAddRequest=OrderAddRequest.builder()
                .passengerId(id)
                .pickupLocation(orderAddRequest.pickupLocation())
                .destination(orderAddRequest.destination())
                .build();
        orderClient.addOrder(orderAddRequest);
        return Result.success();
    }

//    /**
//     * 查询对应时间、路线号下有哪些尚未满员的路线
//     */
//    @GetMapping("/task")
//    public Result<List<TaskVO>>getTask(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time,
//                                       Integer number){
//        List<TaskVO> taskVOList = taskService.getTaskByTimeAndNumber(time,number);
//        return Result.success(taskVOList);
//    }
//
//    /**
//     * 选择路线
//     */
//    @PutMapping("/task/{taskId}")
//    public Result<Boolean> chooseRoute(@PathVariable Long taskId){
//        //添加乘客的routeId
//        Task task = taskService.getById(taskId);
//        passengerService.updateRouteId(task.getRouteId());
//        return Result.success(true);
//    }
//
//    /**
//     * 取消路线选择
//     */
//    @PutMapping("/task/cancel")
//    public Result<Boolean> cancelRoute(String getonStationName,String getoffStationName){
//        passengerService.cancelRoute(getonStationName,getoffStationName);
//        return Result.success(true);
//    }
//
//    /**
//     * 获取当前登录乘客信息
//     * @param passenger
//     * @return
//     */
//    @GetMapping("/current")
//    public Result<PassengerVO> getCurrentPassenger(
//            //该注解会调用UserDetailsService的loadUserByUsername方法，将当前登录的用户信息注入到参数中
//            @AuthenticationPrincipal Passenger passenger) {
//        PassengerVO passengerVO = PassengerVO.builder()
//                .id(passenger.getId())
//                .username(passenger.getUsername())
//                .name(passenger.getName())
//                .phone(passenger.getPhone())
//                .getonStationName(passenger.getGetonStationName())
//                .getoffStationName(passenger.getGetoffStationName())
//                .driverId(passenger.getDriverId())
//                .routeId(passenger.getRouteId())
//                .number(routeMapper.selectById(passenger.getRouteId()).getNumber())
//                .money(passenger.getMoney())
//                .build();
//        return Result.success(passengerVO);
//    }
//
//    /**
//     * 乘客选择/修改上车地点
//     * @param getonStationName
//     * @param passenger
//     * @return
//     */
//    @PutMapping("/getonStationName")
//    public Result<Boolean>updateGetonStationName(@NotBlank String getonStationName, @AuthenticationPrincipal Passenger passenger) throws IOException {
//        if (passenger.getRouteId()==0){
//            return Result.error(ResultCodeEnum.FORBIDDEN_ERROR,"您尚未选择路线");
//        }
//        passengerService.updateGetonStationName(passenger,getonStationName);
//        return Result.success(true);
//    }
//
//    /**
//     * 每分钟上传乘客当前位置
//     * @param locationAddRequest
//     * @return
//     */
//    @PostMapping("/location")
//    public Result<Boolean> addLocation(@RequestBody @Validated LocationAddRequest locationAddRequest,
//                                       @AuthenticationPrincipal Passenger passenger) throws IOException, InterruptedException {
//        //存储passenger的位置和速度
//        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
//        hashOperations.put("passenger_"+passenger.getId(),locationAddRequest.time(),
//                locationAddRequest.latitude()+","+locationAddRequest.longitude());
//        //应该先存储数据，然后再设置过期时间
//        stringRedisTemplate.expire("passenger_"+passenger.getId(),2, TimeUnit.HOURS);
//
//        //如果乘客的状态为0且乘客已上传数据大于10，则拿乘客的数据和司机的数据进行比对
//        //如果匹配成功则向前端传递数据，如果不成功则不传
//        if (hashOperations.keys("passenger_"+passenger.getId()).size()>=10&&passenger.getDriverId()==0){
//            passengerService.compareLocation(passenger.getId());
//        }
//        //判断是否到达选定的下车站点，到达则websocket传给前端语音播报
//        if (passenger.getGetoffStationName()!=null){
//            passengerService.checkStop(passenger.getId(),locationAddRequest);
//        }
//        return Result.success(true);
//    }
//
//    /**
//     * 乘客选择/修改下车地点（人车拟合成功以后才可以修改）
//     * @param getoffStationName
//     * @param passenger
//     * @return
//     */
//    @PutMapping("/getoffStationName")
//    public Result<Boolean>updateGetoffStationName(@NotBlank String getoffStationName, @AuthenticationPrincipal Passenger passenger) throws IOException {
//        //如果乘客还未监测在车上，就不能选择下车地点
//        if (passenger.getDriverId()==0){
//            Result.error(ResultCodeEnum.FORBIDDEN_ERROR,"系统尚未监测您在车上，请耐心等待");
//        }else {
//            passengerService.updateGetoffStationName(passenger,getoffStationName);
//        }
//        return Result.success(true);
//    }
//
//    /**
//     * 修改余额
//     */
//    @PutMapping("/payment")
//    public Result<BigDecimal> payment(@NotNull @Min(0) BigDecimal money, @AuthenticationPrincipal Passenger passenger) {
//        //乘客支付
//        passengerService.payment(money,passenger);
//        return Result.success(passenger.getMoney().add(money));
//    }
//
//    /**
//     * 修改密码
//     */
//    @PutMapping("/password")
//    public Result<Boolean> changePassword(@Validated @RequestBody PasswordChangeRequest passwordChangeRequest){
//        passengerService.changePassword(passwordChangeRequest);
//        return Result.success(true);
//    }

}
