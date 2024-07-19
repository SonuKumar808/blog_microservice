package com.myblog.comment.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {
    private String commentId;
    private String name;
    private String email;
    private String body;
    private String postId;
}
