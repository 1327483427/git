package com.lqp.excel;

import com.alibaba.excel.EasyExcel;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName TestExcelRead
 * @Package : com.lqp.excel
 * @Description :
 * @Create on : 2023/9/16 21:15
 */
public class TestExcelRead {
    public static void main(String[] args) {
        String fileName = "/Volumes/Data/Users/liqiuping/Workspace/11.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, Stu.class, new ExcelListener()).sheet().doRead();
    }
}
