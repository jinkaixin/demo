package com.jkx.blog.model.dto.articleinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;

/**
 * 文章信息新增DTO
 *
 * @author jkx
 * @since 2023-08-07
 */
@Data
public class ArticleInfoAddRequest implements Serializable {

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
}
