package com.jkx.blog.model.dto.articleinfo;

import com.jkx.common.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章信息查询DTO
 *
 * @author jkx
 * @since 2023-08-07
 */
@Data
public class ArticleInfoQueryRequest extends PageRequest implements Serializable {

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
     * 分类ID
     */
    private Long categoryId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
