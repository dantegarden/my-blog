package com.dg.myblog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.myblog.dao.BlogMapper;
import com.dg.myblog.global.exception.NotFoundException;
import com.dg.myblog.global.utils.MarkdownUtils;
import com.dg.myblog.model.entity.Blog;
import com.dg.myblog.model.entity.BlogTags;
import com.dg.myblog.service.BlogService;
import com.dg.myblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-02 16:12
 */
@Transactional
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    private QueryWrapper<Blog> getBlogQueryWrapper(){
        return Wrappers.<Blog>query()
                .eq("published", true)
                .orderByDesc("update_time");
    }

    @Override
    public IPage<Blog> listBlog(Integer pageNum) {
        QueryWrapper<Blog> queryWrapper = getBlogQueryWrapper();
        Page<Blog> page = new Page<>(pageNum, 8, true);
        IPage<Blog> blogIPage = this.baseMapper.selectPage(page, queryWrapper);
        blogIPage.getRecords().forEach(blog -> blog.loadRelations(false));
        return blogIPage;
    }

    @Override
    public IPage<Blog> listBlog(String query, Integer pageNum) {
        QueryWrapper<Blog> queryWrapper = getBlogQueryWrapper();
        if(StrUtil.isNotEmpty(query)){
            queryWrapper.like("title", query).or().like("content", query);
        }
        Page<Blog> page = new Page<>(pageNum, 8, true);
        IPage<Blog> blogIPage = this.baseMapper.selectPage(page, queryWrapper);
        blogIPage.getRecords().forEach(blog -> blog.loadRelations(false));
        return blogIPage;
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        QueryWrapper<Blog> queryWrapper = getBlogQueryWrapper();
        queryWrapper.eq("recommend", true);
        Page<Blog> page = new Page<>(0, size, false);
        IPage<Blog> blogIPage = this.baseMapper.selectPage(page, queryWrapper);
//        blogIPage.getRecords().forEach(blog -> blog.loadRelations());
        return blogIPage.getRecords();
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = this.baseMapper.selectById(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
        blog.loadRelations(true);
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        this.baseMapper.updateViews(id); //浏览数+1
        return b;
    }

    @Override
    public IPage<Blog> listBlogByTagsId(Long tagsId, Integer pageNum) {
        QueryWrapper<Blog> queryWrapper = Wrappers.<Blog>query();
        queryWrapper.eq("bt.tags_id", tagsId);
        queryWrapper.eq("b.published", true);
        queryWrapper.orderByDesc("b.update_time");
        Page<Blog> page = new Page<>(pageNum, 8, true);
        IPage<Blog> blogIPage = this.baseMapper.selectBolgByTagId(page, queryWrapper);
        blogIPage.getRecords().forEach(blog -> blog.loadRelations(false));
        return blogIPage;
    }

    @Override
    public IPage<Blog> listBlogByTypeId(Long typeId, Integer pageNum) {
        QueryWrapper<Blog> queryWrapper = getBlogQueryWrapper();
        queryWrapper.eq("type_id", typeId);
        Page<Blog> page = new Page<>(pageNum, 8, true);
        IPage<Blog> blogIPage = this.baseMapper.selectPage(page, queryWrapper);
        blogIPage.getRecords().forEach(blog -> blog.loadRelations(false));
        return blogIPage;
    }

    @Override
    public IPage<Blog> listBlog(Integer pageNum, BlogQuery blogQuery) {
        QueryWrapper<Blog> queryWrapper = Wrappers.<Blog>query(); //从example生成where条件
        if(BooleanUtil.isTrue(blogQuery.getRecommend())){
            queryWrapper.eq("recommend", true);
        }
        if(StrUtil.isNotBlank(blogQuery.getTitle())){
            queryWrapper.like("title", blogQuery.getTitle());
        }
        if(blogQuery.getTypeId()!=null){
            queryWrapper.eq("type_id", blogQuery.getTypeId());
        }
        queryWrapper.orderByDesc("update_time");
        Page<Blog> page = new Page<>(pageNum, 8, true);
        IPage<Blog> blogIPage = this.baseMapper.selectPage(page, queryWrapper);
        blogIPage.getRecords().forEach(blog -> blog.loadRelations(false));
        return blogIPage;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> months = this.baseMapper.selectGroupMonth();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        if(CollectionUtil.isNotEmpty(months)){
            for (String month : months) {
                QueryWrapper<Blog> queryWrapper = getBlogQueryWrapper();
                queryWrapper.eq("DATE_FORMAT(update_time, '%Y年%m月')", month);
                map.put(month, this.baseMapper.selectList(queryWrapper));
            }
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return new Blog().selectCount(getBlogQueryWrapper()).longValue();
    }

    @Override
    public void deleteBlog(Long id) {
        BlogTags blogTags = new BlogTags();
        blogTags.delete(Wrappers.<BlogTags>query().eq("blogs_id", id));
        this.baseMapper.deleteById(id);
    }

    @Override
    public boolean saveOrUpdateBlog(Blog blog) {
        String tagIds = blog.getTagIds();
        boolean success = saveOrUpdate(blog);
        //处理关联的标签
        if(StrUtil.isNotBlank(tagIds)){
            BlogTags blogTags = new BlogTags();
            blogTags.delete(Wrappers.<BlogTags>query().eq("blogs_id", blog.getId())); //先删
            long[] tagIdArr = StrUtil.splitToLong(tagIds, ",");
            for (int i = 0; i < tagIdArr.length; i++) {
                blogTags.setBlogsId(blog.getId()).setTagsId(tagIdArr[i]); //再插
                blogTags.insert();
            }
        }

        return success;
    }

}
