package com.lqp.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName FileService
 * @Package : com.lqp.vod.service
 * @Description :
 * @Create on : 2023/8/22 23:30
 */
public interface FileService {

    String upload(MultipartFile file);
}
