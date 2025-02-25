package com.weng.gateway;


import com.weng.common.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(properties = {
//        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
//})
public class TestProperties {

//    private final AuthProperties authProperties;

    private final JwtProperties jwtProperties;

//    @Resource
//    private JwtProperties jwtProperties;
//
//    @Resource
//    private AuthProperties authProperties;

//    @Test
//    void testAuthProperties() {
//        System.out.println(authProperties.getExcludePaths());
//    }

    @Test
    void testJwtProperties() {
        System.out.println(jwtProperties.getSecretKey());
    }
}
