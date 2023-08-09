package com.jkx.blog.model.dto.articleinfo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章信息修改DTO
 *
 * @author jkx
 * @since 2023-08-07
 */
@Data
public class ArticleInfoUpdateRequest implements Serializable {

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
     * 分类ID
     */
    private Long categoryId;

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
     * 是否删除
     */
    private Integer isDeleted;


}
