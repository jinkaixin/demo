package com.jkx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.jkx.blog.model.dto.articleinfo.NewArticleRequest;
import com.jkx.blog.model.entity.ArticleContent;
import com.jkx.blog.model.vo.ArticleVO;
import com.jkx.blog.service.ArticleContentService;
import com.jkx.common.common.DeleteRequest;
import com.jkx.common.common.ErrorCode;
import com.jkx.common.constant.CommonConstant;
import com.jkx.common.exception.BusinessException;
import com.jkx.blog.mapper.ArticleInfoMapper;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoAddRequest;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoQueryRequest;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoUpdateRequest;
import com.jkx.blog.model.entity.ArticleInfo;
import com.jkx.blog.model.entity.User;
import com.jkx.blog.service.ArticleInfoService;
import com.jkx.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 文章信息服务实现类
 *
 * @author jkx
 * @since 2023-08-07
 */
@Service
public class ArticleInfoServiceImpl extends ServiceImpl<ArticleInfoMapper, ArticleInfo> implements ArticleInfoService {

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Resource
    private ArticleContentService articleContentService;

    @Resource
    private UserService userService;

    @Override
    public void validPost(ArticleInfo entity, boolean add) {
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
    public Long addRecord(ArticleInfoAddRequest addRequest) {
        if (addRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        ArticleInfo insertEntity = new ArticleInfo();
        BeanUtils.copyProperties(addRequest, insertEntity);
        // 校验
        validPost(insertEntity, true);
        HttpServletRequest request = getHttpServletRequest();
        User loginUser = userService.getLoginUser(request);
        int rows = articleInfoMapper.insert(insertEntity);
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
        ArticleInfo oldRecord = articleInfoMapper.selectById(id);
        if (oldRecord == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        int rows = articleInfoMapper.deleteById(id);
        return rows == 1;
    }

    @Override
    public boolean updateRecord(ArticleInfoUpdateRequest updateRequest) {
        if (updateRequest == null || updateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空或ID值非法");
        }
        ArticleInfo updateEntity = new ArticleInfo();
        BeanUtils.copyProperties(updateRequest, updateEntity);
        // 参数校验
        validPost(updateEntity, false);
        HttpServletRequest request = getHttpServletRequest();
        User user = userService.getLoginUser(request);
        long id = updateRequest.getId();
        // 判断是否存在
        ArticleInfo oldRecord = articleInfoMapper.selectById(id);
        if (oldRecord == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        int rows = articleInfoMapper.updateById(updateEntity);
        return rows == 1;
    }

    @Override
    public ArticleInfo getRecordById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "ID值非法");
        }
        ArticleInfo entity = articleInfoMapper.selectById(id);
        return entity;
    }

    @Override
    public List<ArticleInfo> listRecord(ArticleInfoQueryRequest queryRequest) {
        ArticleInfo queryCondition = new ArticleInfo();
        if (queryRequest != null) {
            BeanUtils.copyProperties(queryRequest, queryCondition);
        }
        QueryWrapper<ArticleInfo> queryWrapper = new QueryWrapper<>(queryCondition);
        List<ArticleInfo> list = articleInfoMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Page<ArticleInfo> listRecordByPage(ArticleInfoQueryRequest queryRequest) {
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
        ArticleInfo queryCondition = new ArticleInfo();
        BeanUtils.copyProperties(queryRequest, queryCondition);
        QueryWrapper<ArticleInfo> queryWrapper = new QueryWrapper<>(queryCondition);
        // 支持模糊搜索的字段，如命名为content
//        String content = queryCondition.getContent();
//        queryCondition.setContent(null);
//        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                Objects.equals(CommonConstant.SORT_ORDER_ASC, sortOrder), sortField);
        Page<ArticleInfo> page = articleInfoMapper.selectPage(new Page<>(current, size), queryWrapper);
        return page;
    }

    @Override
    public ArticleVO getArticleById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "ID值非法");
        }
        ArticleVO articleVO = new ArticleVO();
        ArticleInfo articleInfo = articleInfoMapper.selectById(id);
        ArticleContent articleContent = articleContentService.getRecordByArticleId(id);
        if (articleInfo == null || articleContent == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        BeanUtils.copyProperties(articleInfo, articleVO);
        articleVO.setContent(articleContent.getContent());
        return articleVO;
    }

    @Transactional
    @Override
    public Long newArticle(NewArticleRequest newArticle) {
        if (newArticle == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        // 文章标题、内容非空校验
        if (StringUtils.isBlank(newArticle.getTitle()) || StringUtils.isBlank(newArticle.getContent())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章标题或者内容为空");
        }
        // 标题长度检查（限制长度60）
        if (newArticle.getTitle().length() > 60) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章标题长度不可大于60");
        }
        // 摘要字数限制200，若为空则提取内容前100个字
        if (StringUtils.isBlank(newArticle.getSummary())) {
            String content = newArticle.getContent();
            newArticle.setSummary(content.length() > 200 ? content.substring(0, 200) : content);
        } else {
            if (newArticle.getSummary().length() > 200) {
                newArticle.setSummary(newArticle.getSummary().substring(0, 200));
            }
        }
        // 对象属性拷贝
        ArticleInfo articleInfo = new ArticleInfo();
        BeanUtils.copyProperties(newArticle, articleInfo);
        // 文章字数统计
        articleInfo.setWordCount(newArticle.getContent().length());
        // 组装文章表和文章内容表实体，入库操作放在同一个事务中进行
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(newArticle.getContent());
        // 数据入库，放在同一个事务中进行
        int rows = articleInfoMapper.insert(articleInfo);
        if (rows != 1) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "新增文章时存入文章信息失败");
        }
        articleContent.setArticleInfoId(articleInfo.getId());
        boolean isSuccess = articleContentService.save(articleContent);
        if (!isSuccess) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "新增文章时存入文章内容失败");
        }
        return articleInfo.getId();
    }

    /**
     * 获得当前请求对象
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}
