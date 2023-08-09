package com.jkx.blog.model.dto.category;

import lombok.Data;

import java.io.Serializable;

/**
 * 分类新增DTO
 *
 * @author jkx
 * @since 2023-08-09
 */
@Data
public class CategoryAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String description;
}
