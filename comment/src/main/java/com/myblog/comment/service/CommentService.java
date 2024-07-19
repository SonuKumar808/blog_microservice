package com.myblog.comment.service;

import com.myblog.comment.config.RestTemplateConfig;
import com.myblog.comment.entity.Comment;
import com.myblog.comment.payload.CommentDto;
import com.myblog.comment.payload.Post;
import com.myblog.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface CommentService {




    public CommentDto saveComment(CommentDto dto);

    public List<CommentDto> getCommentByPostId(String postId);




}
