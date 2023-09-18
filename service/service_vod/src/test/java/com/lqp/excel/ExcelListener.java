package com.lqp.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName ExcelListener
 * @Package : com.lqp.excel
 * @Description :
 * @Create on : 2023/9/16 21:16
 */
public class ExcelListener extends AnalysisEventListener<Stu> {

    List<Stu> list = new ArrayList<Stu>();

    @Override
    public void invoke(Stu stu, AnalysisContext analysisContext) {
        System.out.println(stu);
        list.add(stu);
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
