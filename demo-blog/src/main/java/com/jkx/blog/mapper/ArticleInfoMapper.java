package com.jkx.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoQueryRequest;
import com.jkx.blog.model.entity.ArticleInfo;
import com.jkx.blog.model.vo.ArticleInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章信息Mapper接口
 *
 * @author jkx
 * @since 2023-08-07
 */
public interface ArticleInfoMapper extends BaseMapper<ArticleInfo> {

    /**
     * 分页查询数量
     *
     * @param queryRequest
     * @return
     */
    Integer queryPageCount(ArticleInfoQueryRequest queryRequest);

    /**
     * 分页查询结果
     * @param queryRequest
     * @param offset
     * @param size
     * @return
     */
    List<ArticleInfoVO> queryPage(@Param("queryRequest") ArticleInfoQueryRequest queryRequest, @Param("offset") long offset,
                                  @Param("size") long size);
}
