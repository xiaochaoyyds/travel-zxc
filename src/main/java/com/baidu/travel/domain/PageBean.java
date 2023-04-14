package com.baidu.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PageBean<T> {
    private int totalCount;
    private int totalPage;
    private int currentPage;
    private int pageSize;
    private List<T> list;


}
