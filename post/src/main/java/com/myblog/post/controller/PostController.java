package com.myblog.post.controller;

import com.myblog.post.entity.Post;
import com.myblog.post.payload.PostDto;
import com.myblog.post.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto){
        PostDto savedPost = postService.savePost(postDto);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable String postId){
        PostDto postDto = postService.getPostById(postId);
        return postDto;
    }


    @GetMapping("/{postId}/comments")
    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "commentFallback")
    public ResponseEntity<PostDto> getAllCommentsForParticularPost(@PathVariable String postId){
       PostDto postDto =  postService.getAllCommentsForParticularPost(postId);
       return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    public ResponseEntity<PostDto> commentFallback(String postId, Exception ex) {

        System.out.println("Fallback is executed because service is down : "+ ex.getMessage());

        ex.printStackTrace();

        PostDto dto = new PostDto();
        dto.setId("1234");
        dto.setTitle("Service Down");
        dto.setContent("Service Down");
        dto.setDescription("Service Down");
        //dto.setComments(Arrays.("Service Down"));
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
