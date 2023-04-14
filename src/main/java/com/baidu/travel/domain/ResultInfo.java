package com.baidu.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 用于封装后端返回前端数据对象
 * @author pfk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResultInfo implements Serializable {
    /**
     * 后端返回结果正常为true，发生异常返回false
     */
    private boolean flag;
    /**
     * 后端返回结果数据对象
     */
    private Object data;
    /**
     * 发生异常的错误消息
     */
    private String errorMsg;

}
