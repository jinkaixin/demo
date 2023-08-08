package com.jkx.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jkx.common.common.DeleteRequest;
import com.jkx.blog.model.dto.articlecontent.ArticleContentAddRequest;
import com.jkx.blog.model.dto.articlecontent.ArticleContentQueryRequest;
import com.jkx.blog.model.dto.articlecontent.ArticleContentUpdateRequest;
import com.jkx.blog.model.entity.ArticleContent;

import java.util.List;

/**
 * 文章内容服务类
 *
 * @author jkx
 * @since 2023-08-07
 */
public interface ArticleContentService extends IService<ArticleContent> {

    /**
     * 校验
     *
     * @param entity 业务对象
     * @param add    是否为创建校验
     */
    void validPost(ArticleContent entity, boolean add);

    /**
     * 新增
     *
     * @param addRequest
     * @return 新增记录ID
     */
    Long addRecord(ArticleContentAddRequest addRequest);

    /**
     * 删除
     *
     * @param deleteRequest
     * @return 删除操作是否成功，true表示成功
     */
    boolean deleteRecord(DeleteRequest deleteRequest);

    /**
     * 修改
     *
     * @param updateRequest
     * @return 更新操作是否成功，true表示成功
     */
    boolean updateRecord(ArticleContentUpdateRequest updateRequest);

    /**
     * 根据 id 获取
     *
     * @param id
     * @return 单条记录
     */
    ArticleContent getRecordById(long id);

    /**
     * 获取列表
     *
     * @param queryRequest
     * @return 记录列表
     */
    List<ArticleContent> listRecord(ArticleContentQueryRequest queryRequest);

    /**
     * 分页获取列表
     *
     * @param queryRequest
     * @return 分页对象
     */
    Page<ArticleContent> listRecordByPage(ArticleContentQueryRequest queryRequest);

    /**
     * 根据文章ID获取文章内容
     *
     * @param articleId
     * @return
     */
    ArticleContent getRecordByArticleId(long articleId);

    /**
     * 删除对应文章内容
     *
     * @param articleId
     * @return
     */
    boolean deleteByArticleId(long articleId);

    /**
     * 更新文章内容
     * @param content
     * @param articleId
     * @return
     */
    boolean updateContentByArticleId(String content, Long articleId);
}
