package com.lqp.vod.controller;

import com.lqp.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName UserLoginController
 * @Package : com.lqp.vod.controller
 * @Description :
 * @Create on : 2023/8/20 15:08
 */
@RestController
@RequestMapping("/admin/vod/user")
public class UserLoginController {

    /*
    * login
    * */
    @PostMapping("login")
    public Result<Object> login(){
        HashMap<String, Object> loginResultMap = new HashMap<>();
        loginResultMap.put("token","admin-token");
        return Result.ok(loginResultMap);
    }

    @GetMapping("info")
    public Result<Object> getInfo(){
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        return Result.ok(map);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public Result<Void> logout(){
        return Result.ok();
    }

}
