package com.lqp.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqp.model.vod.Subject;
import com.lqp.model.vod.Teacher;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName SubjectService
 * @Package : com.lqp.vod.service
 * @Description :
 * @Create on : 2023/9/16 15:55
 */
public interface SubjectService extends IService<Subject> {
    //查询下一层课程分类
    List<Subject> findChildSubject(Long id);

    void exportData(HttpServletResponse response);

    void importDictData(MultipartFile file);
}
