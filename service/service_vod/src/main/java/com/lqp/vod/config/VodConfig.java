package com.lqp.vod.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName VodConfig
 * @Package : com.lqp.vod.config
 * @Description :
 * @Create on : 2023/8/19 20:11
 */
@Configuration
@MapperScan("com.lqp.vod.mapper")
public class VodConfig {


    /*
    * 添加分页插件
    * */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
