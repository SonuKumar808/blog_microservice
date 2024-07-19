package com.myblog.post.service.impl;

import com.myblog.post.config.RestTemplateConfig;
import com.myblog.post.entity.Post;
import com.myblog.post.payload.PostDto;
import com.myblog.post.repository.PostRepository;
import com.myblog.post.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestTemplateConfig restTemplate;


    @Override
    public PostDto savePost(PostDto dto) {
        Post post = mapToEntity(dto);
        String id = UUID.randomUUID().toString();
        post.setId(id);
        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public PostDto getPostById(String postId) {
        Post post = postRepository.findById(postId).get();
        return mapToDto(post);
    }

    @Override
    public PostDto getAllCommentsForParticularPost(String postId) {
        Post post = postRepository.findById(postId).get();
        ArrayList postObj = restTemplate.getRestTemplate().getForObject("http://COMMENT-SERVICE/api/comments/" + postId, ArrayList.class);
        PostDto postDto = mapToDto(post);
        postDto.setComments(postObj);
        return postDto;
    }


    /*Model Mapper Implementation - map-to-entity*/
    Post mapToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    /*Model Mapper Implementation - map-to-dto*/
    PostDto mapToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
}

