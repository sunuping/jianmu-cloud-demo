package com.jianmu.bean.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author kong
 */
@Data
@Accessors(chain = true)
public class BasePageParam {
    @ApiModelProperty("当前页-默认1")
    @Min(1)
    private Long current = 1L;

    @ApiModelProperty("每页数量-默认10")
    @Min(1)
    @Max(100)
    private Long size = 10L;

    @ApiModelProperty("搜索")
    @NotNull
    private String search;
}
