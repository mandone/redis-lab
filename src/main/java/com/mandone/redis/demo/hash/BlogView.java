package com.mandone.redis.demo.hash;

import com.mandone.redis.core.RedisService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class BlogView {
    @Resource(name = "redisServiceImpl")
    private RedisService redisService;


    public long getBlogId() {
        return redisService.incr("blog_id_counter", 1);
    }

    /**
     * 发表一篇博客
     */
    public void publishBlog(long id, Map<String, String> blog) {
        if (redisService.hexists("article::" + id, "title")) {
            return;
        }
        blog.put("content_length", String.valueOf(blog.get("content").length()));
        redisService.hmset("article::" + id, blog);
    }

    /**
     * 查看一篇博客
     *
     * @param id 博客id
     * @return 返回博客详情
     */
    public Map<String, String> viewBlog(long id) {
        Map<String, String> blog = redisService.hgetAll("article::" + id);
        incrementBlogViewCount(id);
        return blog;
    }

    /**
     * 更新一篇博客
     */
    public void updateBlog(long id, Map<String, String> updatedBlog) {
        String updatedContent = updatedBlog.get("content");
        if (updatedContent != null && !"".equals(updatedContent)) {
            updatedBlog.put("content_length", String.valueOf(updatedContent.length()));
        }

        redisService.hmset("article::" + id, updatedBlog);
    }

    /**
     * 对博客进行点赞
     *
     * @param id
     */
    public void incrementBlogLikeCount(long id) {
        redisService.hincr("article::" + id, "like_count", 1);
    }

    /**
     * 增加博客浏览次数
     *
     * @param id
     */
    public void incrementBlogViewCount(long id) {
        redisService.hincr("article::" + id, "view_count", 1);
    }
}
