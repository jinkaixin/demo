package com.jkx.blog.model.dto.articleinfo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章信息新增DTO
 *
 * @author jkx
 * @since 2023-08-07
 */
@Data
public class NewArticleRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 分类
     */
    private String category;

    /**
     * 文章内容
     */
    private String content;
}
