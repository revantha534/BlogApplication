package com.spring.blog.service;

import com.spring.blog.Dto.PostDto;
import com.spring.blog.Dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo , int pageSize , String sortBy);

    PostDto getPostById(long id);

    PostDto updatePost( PostDto postDto , long id);

    void deletePostById(long id);

}
