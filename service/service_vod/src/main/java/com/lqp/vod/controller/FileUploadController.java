package com.lqp.vod.controller;

import com.lqp.result.Result;
import com.lqp.vod.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName FileUploadController
 * @Package : com.lqp.vod.controller
 * @Description :
 * @Create on : 2023/8/22 23:33
 */
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/admin/vod/file")
public class FileUploadController {

    @Autowired
    private FileService fileService;
    /**
     * 文件上传
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public Result<String> upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        String uploadUrl = fileService.upload(file);
        return Result.ok(uploadUrl).message("文件上传成功");
    }
}