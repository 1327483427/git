package com.lqp.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqp.exception.GgktException;
import com.lqp.model.vod.Subject;
import com.lqp.model.vod.Teacher;
import com.lqp.vo.vod.SubjectEeVo;
import com.lqp.vod.listener.SubjectListener;
import com.lqp.vod.mapper.SubjectMapper;
import com.lqp.vod.mapper.TeacherMapper;
import com.lqp.vod.service.SubjectService;
import com.lqp.vod.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName SubjectServiceImpl
 * @Package : com.lqp.vod.service.impl
 * @Description :
 * @Create on : 2023/9/16 15:55
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;

    @Override
    public List<Subject> findChildSubject(Long id) {
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("parent_id",id);
        List<Subject> subjects = baseMapper.selectList(subjectQueryWrapper);
        for (Subject subject:subjects) {
            Long subjectId = subject.getId();
            boolean isChild = this.hasChildren(subjectId);
            subject.setHasChildren(isChild);
        }
        return subjects;

    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
            List<Subject> dictList = baseMapper.selectList(null);
            List<SubjectEeVo> dictVoList = new ArrayList<>(dictList.size());
            for(Subject dict : dictList) {
                SubjectEeVo dictVo = new SubjectEeVo();
                BeanUtils.copyProperties(dict,dictVo);
                dictVoList.add(dictVo);
            }
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class).sheet("课程分类").doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),SubjectEeVo.class,subjectListener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasChildren(Long subjectId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",subjectId);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}
