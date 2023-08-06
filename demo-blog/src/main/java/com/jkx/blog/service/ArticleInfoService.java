package com.jkx.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jkx.blog.model.dto.articleinfo.NewArticleRequest;
import com.jkx.blog.model.vo.ArticleVO;
import com.jkx.common.common.DeleteRequest;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoAddRequest;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoQueryRequest;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoUpdateRequest;
import com.jkx.blog.model.entity.ArticleInfo;

import java.util.List;

/**
 * 文章信息服务类
 *
 * @author jkx
 * @since 2023-08-07
 */
public interface ArticleInfoService extends IService<ArticleInfo> {

    /**
     * 校验
     *
     * @param entity 业务对象
     * @param add 是否为创建校验
     */
    void validPost(ArticleInfo entity, boolean add);

    /**
     * 新增
     *
     * @param addRequest
     * @return 新增记录ID
     */
    Long addRecord(ArticleInfoAddRequest addRequest);

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
    boolean updateRecord(ArticleInfoUpdateRequest updateRequest);

    /**
     * 根据 id 获取
     *
     * @param id
     * @return 单条记录
     */
    ArticleInfo getRecordById(long id);

    /**
     * 获取列表
     *
     * @param queryRequest
     * @return 记录列表
     */
    List<ArticleInfo> listRecord(ArticleInfoQueryRequest queryRequest);

    /**
     * 分页获取列表
     *
     * @param queryRequest
     * @return 分页对象
     */
    Page<ArticleInfo> listRecordByPage(ArticleInfoQueryRequest queryRequest);

    /**
     * 获取特定文章
     * @param id
     */
    ArticleVO getArticleById(long id);

    /**
     * 新增文章
     *
     * @param newArticle
     * @return
     */
    Long newArticle(NewArticleRequest newArticle);
}
