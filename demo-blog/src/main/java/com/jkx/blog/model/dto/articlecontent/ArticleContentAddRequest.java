package com.jkx.blog.model.dto.articlecontent;

import lombok.Data;

import java.io.Serializable;

/**
 * 文章内容新增DTO
 *
 * @author jkx
 * @since 2023-08-07
 */
@Data
public class ArticleContentAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所属文章ID
     */
    private Long articleInfoId;

    /**
     * 内容
     */
    private String content;
}
