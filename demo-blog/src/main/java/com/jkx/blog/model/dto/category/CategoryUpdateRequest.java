package com.jkx.blog.model.dto.category;

import lombok.Data;

import java.io.Serializable;

/**
 * 分类修改DTO
 *
 * @author jkx
 * @since 2023-08-09
 */
@Data
public class CategoryUpdateRequest implements Serializable {

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
}
