package com.baidu.travel.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author pfk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CommentVO {
    private String href;
    private String text;

}
