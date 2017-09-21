package com.yimei.hs.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hary on 2017/9/21.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    /**
     * 业务错误代码
     */
    private int code;

    /**
     * 业务错误消息: 可来自异常
     */
    private String message;
}
