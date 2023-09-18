package com.lqp.vod.controller;

import com.lqp.model.vod.Subject;
import com.lqp.model.vod.Teacher;
import com.lqp.result.Result;
import com.lqp.vod.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName SubjectController
 * @Package : com.lqp.vod.controller
 * @Description :
 * @Create on : 2023/9/16 15:54
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping(value="/admin/vod/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    //查询下一层课程分类
    //根据parent_id
    @ApiOperation("查询下一层的课程分类")
    @GetMapping("getChildSubject/{id}")
    public Result<List<Subject>> getChildSubject(@PathVariable Long id){
        List<Subject> list = subjectService.findChildSubject(id);
        return Result.ok(list);
    }

    @ApiOperation(value="课程分类导出")
    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response) {
        subjectService.exportData(response);
    }

    @ApiOperation(value = "课程分类导入")
    @PostMapping("/importData")
    public Result<Void> importData(MultipartFile file) {
        subjectService.importDictData(file);
        return Result.ok();
    }
}
