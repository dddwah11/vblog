package com.xiaoliu.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoliu.system.common.config.RedisCache;
import com.xiaoliu.system.common.constants.Constants;
import com.xiaoliu.system.common.handler.BusinessException;
import com.xiaoliu.system.common.response.Result;
import com.xiaoliu.system.common.response.ResultCode;
import com.xiaoliu.system.common.utils.MailUtil;
import com.xiaoliu.system.entity.Blog;
import com.xiaoliu.system.entity.Comment;
import com.xiaoliu.system.service.IBlogService;
import com.xiaoliu.system.service.ICommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-20
 */
@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ICommentService commentService;

//    @Resource
//    private IBlogService blogService;

    @Resource
    private RedisCache redisCache;

//    @Resource
//    private MailUtil mailUtil;

    /**
     * 新增
     * @param
     * @return
     */
    @PostMapping("/reg")
    public Result reg(@RequestBody Comment comment)  {
        commentService.save(comment);
        redisCache.delCacheMap(Constants.COMMENT);
//        Integer pid = comment.getPid();
//        if (pid!=null) {
//            String content = comment.getContent();
//            Integer blogId = comment.getBlogId();
//            Blog blog = blogService.getById(blogId);
//            String tittle = blog.getTittle();
//            String createBy = comment.getCreateBy();
//            Comment comment1 = commentService.getById(pid);
//            String email = comment1.getEmail();
//            try {
//                mailUtil.sendHtmlMail(email,content,tittle,createBy);
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//        }
        return Result.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/del/{id}")
    public Result del(@PathVariable Integer id){
        commentService.removeById(id);
        redisCache.delCacheMap(Constants.COMMENT);
        return Result.ok();
    }

    /**
     * 根据文章id查评论
     * @param blogId
     * @return
     */
    @GetMapping("/tree/{blogId}")
    public Result findTree(@PathVariable(value = "blogId") Integer blogId){
        List<Comment> comment = redisCache.getCacheMapValue(Constants.COMMENT, blogId.toString());
        if(comment==null) {
            List<Comment> comments = commentService.findTreeById(blogId);
            if (comments==null){
                return Result.ok();
            };
            Map<String, List<Comment>> map = new HashMap<>();
            map.put(blogId.toString(), comments);
            redisCache.setCacheMap(Constants.COMMENT, map);
            Object cacheMapValue = redisCache.getCacheMapValue(Constants.COMMENT, blogId.toString());
            redisCache.expire(Constants.COMMENT, 1, TimeUnit.HOURS);
            ObjectMapper mapper = new ObjectMapper();
             comment = mapper.convertValue(cacheMapValue, List.class);
        }
        return Result.ok().data("records",comment);
    }

    /**
     * 分页查询全部
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/list")
    public Result findAll(@RequestParam(defaultValue = "1") Integer current,
                          @RequestParam(defaultValue = "7") Integer size){
        Page<Comment> page = new Page<>(current,size);
        Page<Comment> commentPage = commentService.page(page);
        List<Comment> records = commentPage.getRecords();
        long total = commentPage.getTotal();
        return Result.ok().data("records", records).data("total", total);
    }


}

