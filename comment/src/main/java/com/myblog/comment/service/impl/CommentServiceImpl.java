package com.myblog.comment.service.impl;

import com.myblog.comment.config.RestTemplateConfig;
import com.myblog.comment.entity.Comment;
import com.myblog.comment.payload.CommentDto;
import com.myblog.comment.payload.Post;
import com.myblog.comment.repository.CommentRepository;
import com.myblog.comment.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RestTemplateConfig restTemplate;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDto saveComment(CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        String id = UUID.randomUUID().toString();
        comment.setCommentId(id);
        Post post = restTemplate.getRestTemplate().getForObject("http://POST-SERVICE/api/posts/" + comment.getPostId(), Post.class);
        if (post != null) {
            Comment saved = commentRepository.save(comment);
            return mapToDto(saved);
        }
        return null;
    }

    @Override
    public List<CommentDto> getCommentByPostId(String postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        return commentList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    /*Model Mapper Implementation - map-to-entity*/
    Comment mapToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    /*Model Mapper Implementation - map-to-dto*/
    CommentDto mapToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }
}
