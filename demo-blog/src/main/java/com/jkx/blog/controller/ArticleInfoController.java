package com.jkx.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkx.blog.model.dto.articleinfo.NewArticleRequest;
import com.jkx.blog.model.vo.ArticleVO;
import com.jkx.common.common.BaseResponse;
import com.jkx.common.common.DeleteRequest;
import com.jkx.common.common.ResultUtils;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoAddRequest;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoUpdateRequest;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoQueryRequest;
import com.jkx.blog.model.entity.ArticleInfo;
import com.jkx.blog.service.ArticleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章信息前端控制器
 *
 * @author jkx
 * @since 2023-08-07
 */
@RestController
@RequestMapping("/articleInfo")
public class ArticleInfoController {

    @Autowired
    private ArticleInfoService articleInfoService;

    /**
     * 创建
     *
     * @param addRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addRecord(@RequestBody ArticleInfoAddRequest addRequest) {
        Long recordId = articleInfoService.addRecord(addRequest);
        return ResultUtils.success(recordId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteRecord(@RequestBody DeleteRequest deleteRequest) {
        boolean isSuccess = articleInfoService.deleteRecord(deleteRequest);
        return ResultUtils.success(isSuccess);
    }

    /**
     * 更新
     *
     * @param updateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateRecord(@RequestBody ArticleInfoUpdateRequest updateRequest) {
        boolean isSuccess = articleInfoService.updateRecord(updateRequest);
        return ResultUtils.success(isSuccess);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<ArticleInfo> getRecordById(long id) {
        ArticleInfo entity = articleInfoService.getRecordById(id);
        return ResultUtils.success(entity);
    }

    /**
     * 获取列表
     *
     * @param queryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<ArticleInfo>> listRecord(ArticleInfoQueryRequest queryRequest) {
        List<ArticleInfo> list = articleInfoService.listRecord(queryRequest);
        return ResultUtils.success(list);
    }

    /**
     * 分页获取列表
     *
     * @param queryRequest
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ArticleInfo>> listRecordByPage(ArticleInfoQueryRequest queryRequest) {
        Page<ArticleInfo> page = articleInfoService.listRecordByPage(queryRequest);
        return ResultUtils.success(page);
    }

    /**
     * 查询最近文章
     *
     * @return 最近文章的信息列表
     */
    @GetMapping("/list/recent")
    public BaseResponse<Page<ArticleInfo>> listRecent() {
        Page<ArticleInfo> page = articleInfoService.listRecordByPage(new ArticleInfoQueryRequest());
        return ResultUtils.success(page);
    }

    /**
     * 查询特定文章
     *
     * @param id 文章ID
     * @return
     */
    @GetMapping("/getarticle")
    public BaseResponse<ArticleVO> getArticleById(long id) {
        ArticleVO entity = articleInfoService.getArticleById(id);
        return ResultUtils.success(entity);
    }

    /**
     * 创建文章
     */
    @PostMapping("/newarticle")
    public BaseResponse<Long> newArticle(@RequestBody NewArticleRequest newArticleRequest) {
        Long recordId = articleInfoService.newArticle(newArticleRequest);
        return ResultUtils.success(recordId);
    }
}
