package com.jkx.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jkx.blog.model.dto.category.CategoryAddRequest;
import com.jkx.blog.model.dto.category.CategoryQueryRequest;
import com.jkx.blog.model.dto.category.CategoryUpdateRequest;
import com.jkx.blog.model.entity.Category;
import com.jkx.common.common.DeleteRequest;

import java.util.List;

/**
 * 分类服务类
 *
 * @author jkx
 * @since 2023-08-09
 */
public interface CategoryService extends IService<Category> {

    /**
     * 校验
     *
     * @param entity 业务对象
     * @param add 是否为创建校验
     */
    void validPost(Category entity, boolean add);

    /**
     * 新增
     *
     * @param addRequest
     * @return 新增记录ID
     */
    Long addRecord(CategoryAddRequest addRequest);

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
    boolean updateRecord(CategoryUpdateRequest updateRequest);

    /**
     * 根据 id 获取
     *
     * @param id
     * @return 单条记录
     */
    Category getRecordById(long id);

    /**
     * 获取列表
     *
     * @param queryRequest
     * @return 记录列表
     */
    List<Category> listRecord(CategoryQueryRequest queryRequest);

    /**
     * 分页获取列表
     *
     * @param queryRequest
     * @return 分页对象
     */
    Page<Category> listRecordByPage(CategoryQueryRequest queryRequest);
}
