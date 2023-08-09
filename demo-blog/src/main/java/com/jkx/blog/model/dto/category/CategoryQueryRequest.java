package com.jkx.blog.model.dto.category;

import com.jkx.common.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类查询DTO
 *
 * @author jkx
 * @since 2023-08-09
 */
@Data
public class CategoryQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
