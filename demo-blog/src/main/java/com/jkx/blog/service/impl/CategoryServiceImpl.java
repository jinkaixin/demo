package com.jkx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.jkx.blog.mapper.ArticleInfoMapper;
import com.jkx.blog.mapper.CategoryMapper;
import com.jkx.blog.model.dto.category.CategoryAddRequest;
import com.jkx.blog.model.dto.category.CategoryQueryRequest;
import com.jkx.blog.model.dto.category.CategoryUpdateRequest;
import com.jkx.blog.model.entity.ArticleInfo;
import com.jkx.blog.model.entity.Category;
import com.jkx.blog.model.entity.User;
import com.jkx.blog.service.CategoryService;
import com.jkx.blog.service.UserService;
import com.jkx.common.common.DeleteRequest;
import com.jkx.common.common.ErrorCode;
import com.jkx.common.constant.CommonConstant;
import com.jkx.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 分类服务实现类
 *
 * @author jkx
 * @since 2023-08-09
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private UserService userService;

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Override
    public void validPost(Category entity, boolean add) {
        if (entity == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "要校验的对象为空");
        }
        if (add) {
            // TODO 创建时，会对一些参数做额外的校验
        }
        // TODO 其它校验
        //if (StringUtils.isNotBlank(content) && content.length() > 8192) {
        //    throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        //}
    }

    @Override
    public Long addRecord(CategoryAddRequest addRequest) {
        if (addRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        // 分类名非空校验，且字数不超过15
        if (StringUtils.isBlank(addRequest.getCategoryName()) || addRequest.getCategoryName().length() > 15) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类名为空或长度超过15");
        }
        // 分类描述字数不超过200
        if (addRequest.getDescription() != null && addRequest.getDescription().length() > 200) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "描述长度不可超过200");
        }
        // 权限校验，仅管理员登录后可操作
        HttpServletRequest request = getHttpServletRequest();
        User user = userService.getLoginUser(request);
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 查询数据库，检查分类名是否重复，重复则抛异常提示
        Category condition = new Category();
        condition.setCategoryName(addRequest.getCategoryName());
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>(condition);
        List<Category> list = categoryMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该分类名已存在");
        }
        // 组装数据，并入库
        Category insertEntity = new Category();
        BeanUtils.copyProperties(addRequest, insertEntity);
        int rows = categoryMapper.insert(insertEntity);
        if (rows != 1) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

        return insertEntity.getId();
    }

    @Override
    public boolean deleteRecord(DeleteRequest deleteRequest) {
        // 校验ID非空且大于0
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空或ID值非法");
        }
        // 权限校验，仅管理员登录后可操作
        HttpServletRequest request = getHttpServletRequest();
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 查询数据库记录，记录不存在则报错
        long id = deleteRequest.getId();
        Category oldRecord = categoryMapper.selectById(id);
        if (oldRecord == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 检查该分类下是否存在文章，存在则不允许删除
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setCategoryId(deleteRequest.getId());
        List<ArticleInfo> articleList = articleInfoMapper.selectList(new QueryWrapper<>(articleInfo));
        if (!CollectionUtils.isEmpty(articleList)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该分类下还存在文章，请迁移后再删除分类");
        }
        // 删除记录
        int rows = categoryMapper.deleteById(id);
        return rows == 1;
    }

    @Override
    public boolean updateRecord(CategoryUpdateRequest updateRequest) {
        // 校验ID非空且大于0
        if (updateRequest == null || updateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空或ID值非法");
        }
        // 业务字段校验
        if (StringUtils.isNotBlank(updateRequest.getCategoryName()) && updateRequest.getCategoryName().length() > 15) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类名长度不可超过15");
        }
        if (StringUtils.isNotBlank(updateRequest.getDescription()) && updateRequest.getDescription().length() > 200) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类描述长度不可超过200");
        }
        // 权限校验，仅管理员登录后可操作
        HttpServletRequest request = getHttpServletRequest();
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //查询数据库记录，记录不存在则报错
        long id = updateRequest.getId();
        Category oldRecord = categoryMapper.selectById(id);
        if (oldRecord == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 修改记录
        Category updateEntity = new Category();
        BeanUtils.copyProperties(updateRequest, updateEntity);
        int rows = categoryMapper.updateById(updateEntity);
        return rows == 1;
    }

    @Override
    public Category getRecordById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "ID值非法");
        }
        Category entity = categoryMapper.selectById(id);
        return entity;
    }

    @Override
    public List<Category> listRecord(CategoryQueryRequest queryRequest) {
        Category queryCondition = new Category();
        if (queryRequest != null) {
            BeanUtils.copyProperties(queryRequest, queryCondition);
        }
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>(queryCondition);
        List<Category> list = categoryMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Page<Category> listRecordByPage(CategoryQueryRequest queryRequest) {
        if (queryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        // 分页字段校验
        long current = queryRequest.getCurrent();
        long size = queryRequest.getPageSize();
        if (current <= 0 || size <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "页数或每页记录数必须为正");
        }
        // 限制未登录可查看页数
        if (current > 5) {
            HttpServletRequest request = getHttpServletRequest();
            userService.getLoginUser(request);
        }
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求记录数过多");
        }
        String sortField = queryRequest.getSortField();
        if (sortField != null) {
            sortField = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortField);
        }
        String sortOrder = queryRequest.getSortOrder();
        // 组装查询条件
        Category queryCondition = new Category();
        BeanUtils.copyProperties(queryRequest, queryCondition);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>(queryCondition);
        // 支持模糊搜索的字段，如命名为content
//        String content = queryCondition.getContent();
//        queryCondition.setContent(null);
//        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                Objects.equals(CommonConstant.SORT_ORDER_ASC, sortOrder), sortField);
        Page<Category> page = categoryMapper.selectPage(new Page<>(current, size), queryWrapper);
        return page;
    }

    /**
     * 获得当前请求对象
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}
