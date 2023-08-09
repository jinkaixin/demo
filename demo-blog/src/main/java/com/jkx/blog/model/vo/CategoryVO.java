package com.jkx.blog.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 分类实体类
 *
 * @author jkx
 * @since 2023-08-09
 */
@Data
public class CategoryVO implements Serializable {

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
