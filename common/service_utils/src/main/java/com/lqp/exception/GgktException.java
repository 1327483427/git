package com.lqp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liqiuping
 * @version v1.0.0
 * @ClassName GgktException
 * @Package : com.lqp.exception
 * @Description :
 * @Create on : 2023/8/20 13:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GgktException extends RuntimeException{
    private Integer code;
    private String msg;
}
