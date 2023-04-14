package com.baidu.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 收藏实体类
 * @author pfk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Favorite implements Serializable {
    /**
     * 旅游线路对象
     */
    private Route route;
    /**
     * 收藏时间
     */
    private String date;
    /**
     * 所属用户
     */
    private User user;

}
