package com.jkx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkx.blog.mapper.ArticleInfoMapper;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoAddRequest;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoQueryRequest;
import com.jkx.blog.model.dto.articleinfo.ArticleInfoUpdateRequest;
import com.jkx.blog.model.dto.articleinfo.NewArticleRequest;
import com.jkx.blog.model.dto.articleinfo.UpdateArticleRequest;
import com.jkx.blog.model.entity.ArticleContent;
import com.jkx.blog.model.entity.ArticleInfo;
import com.jkx.blog.model.entity.Category;
import com.jkx.blog.model.entity.User;
import com.jkx.blog.model.vo.ArticleInfoVO;
import com.jkx.blog.model.vo.ArticleVO;
import com.jkx.blog.model.vo.CategoryVO;
import com.jkx.blog.service.ArticleContentService;
import com.jkx.blog.service.ArticleInfoService;
import com.jkx.blog.service.CategoryService;
import com.jkx.blog.service.UserService;
import com.jkx.common.common.DeleteRequest;
import com.jkx.common.common.ErrorCode;
import com.jkx.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文章信息服务实现类
 *
 * @author jkx
 * @since 2023-08-07
 */
@Slf4j
@Service
public class ArticleInfoServiceImpl extends ServiceImpl<ArticleInfoMapper, ArticleInfo> implements ArticleInfoService {

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Resource
    private ArticleContentService articleContentService;

    @Resource
    private CategoryService categoryService;

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
    public Page<ArticleInfoVO> listRecordByPage(ArticleInfoQueryRequest queryRequest) {
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
        // 分类ID校验
        if (queryRequest.getCategoryId() != null && queryRequest.getCategoryId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分类ID不得小于等于0");
        }
        // 进行分页查询
        Integer count = articleInfoMapper.queryPageCount(queryRequest);
        if (count == null || count == 0) {
            return new Page<>(current, size, 0);
        }
        long offset = (current - 1) * size;
        List<ArticleInfoVO> records = articleInfoMapper.queryPage(queryRequest, offset, size);
        Page<ArticleInfoVO> page = new Page<>(current, size, count);
        page.setRecords(records);
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
        // 查出相应的分类信息
        Category category = categoryService.getRecordById(articleInfo.getCategoryId());
        if (category != null) {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(category, categoryVO);
            articleVO.setCategory(categoryVO);
        } else {
            log.warn("category not exist. [articleId={}, categoryId={}]", id, articleInfo.getCategoryId());
        }
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
        // 分类ID非空及长度校验
        if (newArticle.getCategoryId() == null || newArticle.getCategoryId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未设置分类或者分类ID异常");
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
        // 仅管理员可新增
        HttpServletRequest request = getHttpServletRequest();
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 对象属性拷贝
        ArticleInfo articleInfo = new ArticleInfo();
        BeanUtils.copyProperties(newArticle, articleInfo);
        // 文章字数统计
        articleInfo.setWordCount(newArticle.getContent().length());
        // 检查分类是否存在，不存在就抛异常提示
        Category category = categoryService.getRecordById(newArticle.getCategoryId());
        if (category == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该分类不存在，请先新增");
        }
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

    @Transactional
    @Override
    public boolean deleteArticle(DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空或ID值非法");
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        ArticleInfo oldRecord = articleInfoMapper.selectById(id);
        if (oldRecord == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅管理员可删除
        HttpServletRequest request = getHttpServletRequest();
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        int articleInfoDeleteRows = articleInfoMapper.deleteById(id);
        boolean isContentDeleteSuccess = articleContentService.deleteByArticleId(id);
        if (articleInfoDeleteRows != 1 || !isContentDeleteSuccess) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "删除失败");
        }
        return articleInfoDeleteRows == 1 && isContentDeleteSuccess;
    }

    @Transactional
    @Override
    public boolean updateArticle(UpdateArticleRequest updateArticle) {
        if (updateArticle == null || updateArticle.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空或ID值非法");
        }
        // 参数校验
        // 文章标题、内容非空校验
        if (StringUtils.isBlank(updateArticle.getTitle()) || StringUtils.isBlank(updateArticle.getContent())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章标题或者内容为空");
        }
        // 分类ID非空及长度校验
        if (updateArticle.getCategoryId() == null || updateArticle.getCategoryId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未设置分类或者分类ID异常");
        }
        // 标题长度检查（限制长度60）
        if (updateArticle.getTitle().length() > 60) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章标题长度不可大于60");
        }
        // 摘要字数限制200，若为空则提取内容前100个字
        if (StringUtils.isBlank(updateArticle.getSummary())) {
            String content = updateArticle.getContent();
            updateArticle.setSummary(content.length() > 200 ? content.substring(0, 200) : content);
        } else {
            if (updateArticle.getSummary().length() > 200) {
                updateArticle.setSummary(updateArticle.getSummary().substring(0, 200));
            }
        }
        // 查询数据库记录，记录不存在则报错
        long id = updateArticle.getId();
        ArticleInfo oldRecord = articleInfoMapper.selectById(id);
        if (oldRecord == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 权限校验，非管理员不可操作
        HttpServletRequest request = getHttpServletRequest();
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 检查分类是否存在，不存在就抛异常提示
        Category category = categoryService.getRecordById(updateArticle.getCategoryId());
        if (category == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该分类不存在，请先新增");
        }
        // 计算新文章字数，组装文章表及文章内容表更新实体
        ArticleInfo articleInfo = new ArticleInfo();
        BeanUtils.copyProperties(updateArticle, articleInfo);
        articleInfo.setWordCount(updateArticle.getContent().length());
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleInfoId(updateArticle.getId());
        articleContent.setContent(updateArticle.getContent());
        //5.  数据入库，放在同一个事务中进行
        int articleInfoUpdateRows = articleInfoMapper.updateById(articleInfo);
        boolean isContentUpdateSuccess = articleContentService.updateContentByArticleId(updateArticle.getContent(),
                updateArticle.getId());
        if (articleInfoUpdateRows != 1 || !isContentUpdateSuccess) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "更新失败");
        }
        return articleInfoUpdateRows == 1 && isContentUpdateSuccess;
    }

    /**
     * 获得当前请求对象
     */
    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}
