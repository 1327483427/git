package com.lqp.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName ServiceVodApplication
 * @Package : com.lqp.vod
 * @Description :
 * @Create on : 2023/8/19 20:10
 */
@SpringBootApplication
//扫描包
@ComponentScan(basePackages = "com.lqp")
public class ServiceVodApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplication.class, args);
    }
}
