package com.lqp.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName Stu
 * @Package : com.lqp
 * @Description :
 * @Create on : 2023/9/16 21:04
 */
@Data
public class Stu {
    //设置表头名称
    @ExcelProperty("学生编号")
    private int sno;
    //设置表头名称
    @ExcelProperty("学生姓名")
    private String sname;
}
