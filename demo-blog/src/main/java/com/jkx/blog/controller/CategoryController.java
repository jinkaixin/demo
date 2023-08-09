package com.jkx.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkx.blog.model.dto.category.CategoryAddRequest;
import com.jkx.blog.model.dto.category.CategoryQueryRequest;
import com.jkx.blog.model.dto.category.CategoryUpdateRequest;
import com.jkx.blog.model.entity.Category;
import com.jkx.blog.service.CategoryService;
import com.jkx.common.common.BaseResponse;
import com.jkx.common.common.DeleteRequest;
import com.jkx.common.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类前端控制器
 *
 * @author jkx
 * @since 2023-08-09
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 创建
     *
     * @param addRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addRecord(@RequestBody CategoryAddRequest addRequest) {
        Long recordId = categoryService.addRecord(addRequest);
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
        boolean isSuccess = categoryService.deleteRecord(deleteRequest);
        return ResultUtils.success(isSuccess);
    }

    /**
     * 更新
     *
     * @param updateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateRecord(@RequestBody CategoryUpdateRequest updateRequest) {
        boolean isSuccess = categoryService.updateRecord(updateRequest);
        return ResultUtils.success(isSuccess);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Category> getRecordById(long id) {
        Category entity = categoryService.getRecordById(id);
        return ResultUtils.success(entity);
    }

    /**
     * 获取列表
     *
     * @param queryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<Category>> listRecord(CategoryQueryRequest queryRequest) {
        List<Category> list = categoryService.listRecord(queryRequest);
        return ResultUtils.success(list);
    }

    /**
     * 分页获取列表
     *
     * @param queryRequest
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<Category>> listRecordByPage(CategoryQueryRequest queryRequest) {
        Page<Category> page = categoryService.listRecordByPage(queryRequest);
        return ResultUtils.success(page);
    }
}
