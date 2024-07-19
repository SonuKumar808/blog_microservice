package com.myblog.comment.controller;

import com.myblog.comment.payload.CommentDto;
import com.myblog.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?>  saveComment(@RequestBody CommentDto commentDto){
        CommentDto savedComment = commentService.saveComment(commentDto);
      if(savedComment!=null) {
          return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
      }
      return new ResponseEntity<>("Post Not Found with id: "+commentDto.getPostId(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{postId}")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable String postId){
       return commentService.getCommentByPostId(postId);
    }
}
