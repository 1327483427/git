package com.lqp.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lqp.model.vod.Subject;
import com.lqp.vo.vod.SubjectEeVo;
import com.lqp.vod.mapper.SubjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName SubjectListener
 * @Package : com.lqp.vod.listener
 * @Description :
 * @Create on : 2023/9/16 22:06
 */
@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {

    @Autowired
    private SubjectMapper subjectMapper;
    //一行一行读取
    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {
        //调用方法添加数据库
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectEeVo,subject);
        if (!(subject.getId()!= null && subjectMapper.selectById(subject.getId()) != null)){
            subjectMapper.insert(subject);
        }
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
