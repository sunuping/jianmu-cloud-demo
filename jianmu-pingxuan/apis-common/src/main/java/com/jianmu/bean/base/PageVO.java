package com.jianmu.bean.base;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author kong
 */
@ApiModel(value = "分页结果")
@Data
@Accessors(chain = true)
public class PageVO<T> {
    private Long current;
    private Long size;
    private List<T> data;
    private Long total;
    private Long pages;
}
