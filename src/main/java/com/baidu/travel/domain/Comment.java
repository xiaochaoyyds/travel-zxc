package com.baidu.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

/**
 * 评论
 * @author pfk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Comment implements Serializable,Cloneable{
    private Integer id;
    private Integer uid;
    private Integer rid;
    private Date time;
    private String content;

    /**
     * 克隆方法
     * @param clazz
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public <T> T clone(Class<T> clazz) throws InvocationTargetException, IllegalAccessException {
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        copyProperties(result,this);
        return result;
    }
}
