package com.lqp.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lqp.exception.GgktException;
import com.lqp.model.vod.Teacher;
import com.lqp.result.Result;
import com.lqp.vo.vod.TeacherQueryVo;
import com.lqp.vod.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author liqiuping
 * @since 2023-08-19
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping(value="/admin/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //查询所有讲师列表
//    @ApiOperation("所有讲师列表")
//    @GetMapping("findAll")
//    public List<Teacher> findAll(){
//        List<Teacher> list = teacherService.list();
//        return list;
//    }
    /*
    * 查询所有讲师列表
    * */
    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public Result<List<Teacher>> findAll(){
        List<Teacher> teacherList = teacherService.list();
        return Result.ok(teacherList);
    }

    /*
    * 删除讲师
     */
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result<Void> removeById(@ApiParam(name = "id", value = "ID", required = true)  @PathVariable String id){
        boolean result = teacherService.removeById(id);
        return result ? Result.ok() : Result.fail();
    }


    /*
    * 条件查询分页列表
    * */
    @ApiOperation(value = "获取分页列表")
    @PostMapping("{page}/{limit}")
    public Result<IPage<Teacher>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherVo", value = "查询对象", required = false)
            @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        Page<Teacher> teacherPage = new Page<>(page, limit);

        LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherLambdaQueryWrapper.like(StringUtils.isNotBlank(teacherQueryVo.getName()),Teacher::getName,teacherQueryVo.getName())
                .eq(teacherQueryVo.getLevel()!=null,Teacher::getLevel,teacherQueryVo.getLevel())
                .ge(StringUtils.isNotBlank(teacherQueryVo.getJoinDateBegin()),Teacher::getJoinDate,teacherQueryVo.getJoinDateBegin())
                .le(StringUtils.isNotBlank(teacherQueryVo.getJoinDateEnd()),Teacher::getJoinDate,teacherQueryVo.getJoinDateEnd());
        IPage<Teacher> result = teacherService.page(teacherPage, teacherLambdaQueryWrapper);
        return Result.ok(result);
    }

    /*
    * 添加讲师
    * */
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result<Void> save(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return Result.ok(null);
    }

    /*
    * 修改讲师
    * */
    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result<Void> updateById(@RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return Result.ok(null);
    }

    /*
    * 查询讲师
    * */
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result<Teacher> getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    /*
    * 批量删除
    * */
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result<Void> batchRemove(@RequestBody List<Long> idList) {
        teacherService.removeByIds(idList);
        return Result.ok(null);
    }
}

