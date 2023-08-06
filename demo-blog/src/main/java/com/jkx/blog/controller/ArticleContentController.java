package com.jkx.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkx.common.common.BaseResponse;
import com.jkx.common.common.DeleteRequest;
import com.jkx.common.common.ResultUtils;
import com.jkx.blog.model.dto.articlecontent.ArticleContentAddRequest;
import com.jkx.blog.model.dto.articlecontent.ArticleContentUpdateRequest;
import com.jkx.blog.model.dto.articlecontent.ArticleContentQueryRequest;
import com.jkx.blog.model.entity.ArticleContent;
import com.jkx.blog.service.ArticleContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章内容前端控制器
 *
 * @author jkx
 * @since 2023-08-07
 */
@RestController
@RequestMapping("/articleContent")
public class ArticleContentController {

    @Autowired
    private ArticleContentService articleContentService;

    /**
     * 创建
     *
     * @param addRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addRecord(@RequestBody ArticleContentAddRequest addRequest) {
        Long recordId = articleContentService.addRecord(addRequest);
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
        boolean isSuccess = articleContentService.deleteRecord(deleteRequest);
        return ResultUtils.success(isSuccess);
    }

    /**
     * 更新
     *
     * @param updateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateRecord(@RequestBody ArticleContentUpdateRequest updateRequest) {
        boolean isSuccess = articleContentService.updateRecord(updateRequest);
        return ResultUtils.success(isSuccess);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<ArticleContent> getRecordById(long id) {
        ArticleContent entity = articleContentService.getRecordById(id);
        return ResultUtils.success(entity);
    }

    /**
     * 获取列表
     *
     * @param queryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<ArticleContent>> listRecord(ArticleContentQueryRequest queryRequest) {
        List<ArticleContent> list = articleContentService.listRecord(queryRequest);
        return ResultUtils.success(list);
    }

    /**
     * 分页获取列表
     *
     * @param queryRequest
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ArticleContent>> listRecordByPage(ArticleContentQueryRequest queryRequest) {
        Page<ArticleContent> page = articleContentService.listRecordByPage(queryRequest);
        return ResultUtils.success(page);
    }
}
