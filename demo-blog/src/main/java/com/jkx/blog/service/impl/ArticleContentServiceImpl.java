package com.jkx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.jkx.common.common.DeleteRequest;
import com.jkx.common.common.ErrorCode;
import com.jkx.common.constant.CommonConstant;
import com.jkx.common.exception.BusinessException;
import com.jkx.blog.mapper.ArticleContentMapper;
import com.jkx.blog.model.dto.articlecontent.ArticleContentAddRequest;
import com.jkx.blog.model.dto.articlecontent.ArticleContentQueryRequest;
import com.jkx.blog.model.dto.articlecontent.ArticleContentUpdateRequest;
import com.jkx.blog.model.entity.ArticleContent;
import com.jkx.blog.model.entity.User;
import com.jkx.blog.service.ArticleContentService;
import com.jkx.blog.service.UserService;
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
 * 文章内容服务实现类
 *
 * @author jkx
 * @since 2023-08-07
 */
@Service
public class ArticleContentServiceImpl extends ServiceImpl<ArticleContentMapper, ArticleContent> implements ArticleContentService {

    @Resource
    private ArticleContentMapper articleContentMapper;

    @Resource
    private UserService userService;

    @Override
    public void validPost(ArticleContent entity, boolean add) {
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
    public Long addRecord(ArticleContentAddRequest addRequest) {
        if (addRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        ArticleContent insertEntity = new ArticleContent();
        BeanUtils.copyProperties(addRequest, insertEntity);
        // 校验
        validPost(insertEntity, true);
        HttpServletRequest request = getHttpServletRequest();
        User loginUser = userService.getLoginUser(request);
        int rows = articleContentMapper.insert(insertEntity);
        if (rows != 1) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return insertEntity.getId();
    }

    @Override
    public boolean deleteRecord(DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空或ID值非法");
        }
        HttpServletRequest request = getHttpServletRequest();
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        ArticleContent oldRecord = articleContentMapper.selectById(id);
        if (oldRecord == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        int rows = articleContentMapper.deleteById(id);
        return rows == 1;
    }

    @Override
    public boolean updateRecord(ArticleContentUpdateRequest updateRequest) {
        if (updateRequest == null || updateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空或ID值非法");
        }
        ArticleContent updateEntity = new ArticleContent();
        BeanUtils.copyProperties(updateRequest, updateEntity);
        // 参数校验
        validPost(updateEntity, false);
        HttpServletRequest request = getHttpServletRequest();
        User user = userService.getLoginUser(request);
        long id = updateRequest.getId();
        // 判断是否存在
        ArticleContent oldRecord = articleContentMapper.selectById(id);
        if (oldRecord == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        int rows = articleContentMapper.updateById(updateEntity);
        return rows == 1;
    }

    @Override
    public ArticleContent getRecordById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "ID值非法");
        }
        ArticleContent entity = articleContentMapper.selectById(id);
        return entity;
    }

    @Override
    public List<ArticleContent> listRecord(ArticleContentQueryRequest queryRequest) {
        ArticleContent queryCondition = new ArticleContent();
        if (queryRequest != null) {
            BeanUtils.copyProperties(queryRequest, queryCondition);
        }
        QueryWrapper<ArticleContent> queryWrapper = new QueryWrapper<>(queryCondition);
        List<ArticleContent> list = articleContentMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Page<ArticleContent> listRecordByPage(ArticleContentQueryRequest queryRequest) {
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
        ArticleContent queryCondition = new ArticleContent();
        BeanUtils.copyProperties(queryRequest, queryCondition);
        QueryWrapper<ArticleContent> queryWrapper = new QueryWrapper<>(queryCondition);
        // 支持模糊搜索的字段，如命名为content
//        String content = queryCondition.getContent();
//        queryCondition.setContent(null);
//        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                Objects.equals(CommonConstant.SORT_ORDER_ASC, sortOrder), sortField);
        Page<ArticleContent> page = articleContentMapper.selectPage(new Page<>(current, size), queryWrapper);
        return page;
    }

    @Override
    public ArticleContent getRecordByArticleId(long articleId) {
        if (articleId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "articleId值非法");
        }

        ArticleContent queryCondition = new ArticleContent();
        queryCondition.setArticleInfoId(articleId);
        QueryWrapper<ArticleContent> queryWrapper = new QueryWrapper<>(queryCondition);
        List<ArticleContent> list = articleContentMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获得当前请求对象
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}
