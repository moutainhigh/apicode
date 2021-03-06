package com.ycandyz.master.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BasePageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 查询数据列表
     */
    private List<T> result;

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long pageSize = 10;

    /**
     * 当前页
     */
    private long page = 1;

    public BasePageResult(Page page) {
        this.result = page.getRecords();
        this.total = page.getTotal();
        this.pageSize = page.getSize();
        this.page = page.getCurrent();
    }
}
