package com.jkx.blog.model.dto.articleinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 文章信息新增DTO
 *
 * @author jkx
 * @since 2023-08-07
 */
@Data
public class UpdateCategoryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类
     */
    private String category;

    /**
     * 分类
     */
    private String newName;
}
