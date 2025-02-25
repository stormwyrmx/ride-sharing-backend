package com.weng.driverservice;

import com.weng.api.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//todo 核心注解，这玩意到底要不要加，会不会有缓存？神奇！
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.weng.common","com.weng.driverservice"})
@EnableFeignClients(basePackages = "com.weng.api.client",defaultConfiguration = DefaultFeignConfig.class)
public class DriverServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverServiceApplication.class, args);
    }

}
