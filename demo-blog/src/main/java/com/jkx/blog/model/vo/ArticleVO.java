package com.jkx.blog.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jkx
 * @date 2023/8/7
 */
@Data
public class ArticleVO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 字数统计
     */
    private Integer wordCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 分类信息
     */
    private CategoryVO category;
}
