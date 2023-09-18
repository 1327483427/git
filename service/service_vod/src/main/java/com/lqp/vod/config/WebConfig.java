package com.lqp.vod.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.*;
import java.io.IOException;


/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName WebConfig
 * @Package : com.lqp.vod.config
 * @Description :
 * @Create on : 2023/8/20 15:51
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3600);
    }
}
