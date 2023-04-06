package com.xiaoliu.system.service.impl;

import com.xiaoliu.system.entity.Comment;
import com.xiaoliu.system.mapper.CommentMapper;
import com.xiaoliu.system.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2022-08-20
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findTreeById(Integer blogId) {
        List<Comment> comments = commentMapper.findTreeById(blogId);//查出当前所有评论
        if (comments.size()==0){
            return null;
        }
//        查出pid的list 用于查找评论者的名称
        List<Integer> collect = comments.stream().map(c -> c.getPid()).collect(Collectors.toList());
//        根据id查出评论者
        List<Comment> commentList1 = commentMapper.selectBatchIds(collect);
//        找出一级评论
        List<Comment> originId = comments.stream().filter(comment2 -> comment2.getOriginId() == null).collect(Collectors.toList());
        for (Comment comment : originId) {
//            找出二级评论
            List<Comment> commentList =comments.stream().filter(comment1 -> comment.getId().equals(comment1.getOriginId())).collect(Collectors.toList());

//            存入回复的对象
            for (Comment comment1 : commentList) {
                List<Comment> commentList2 = commentList1.stream().filter(a -> comment1.getPid().equals(a.getId())).collect(Collectors.toList());
                for (Comment comment2 : commentList2) {
                    comment1.setPUser(comment2.getCreateBy());
                }
            }
            comment.setChildren(commentList);
        }


        return originId;
    }
}
