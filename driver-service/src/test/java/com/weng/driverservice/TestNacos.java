package com.weng.driverservice;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@SpringBootTest
//@RequiredArgsConstructor
public class TestNacos {

    @Autowired
//    @Resource
    private DiscoveryClient discoveryClient;

    @Test
    public void testNacos() {
        System.out.println("fuck,5555555555555555555555555");
        for (String service : discoveryClient.getServices()) {
            System.out.println("service = " + service);
            //获取ip+port
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println("ip："+instance.getHost()+"；"+"port = " + instance.getPort());
            }
        }

    }

}
