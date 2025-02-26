package com.weng.api.client;

import com.weng.api.dto.Passenger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "passenger-service",path = "/passengers")
public interface PassengerClient {

    @GetMapping("/listByIds")
    List<Passenger> getPassengerByIds(@RequestParam("ids") List<Long> ids);

}
