package com.spring.blog.service.impl;

import com.spring.blog.Dto.PostDto;
import com.spring.blog.Dto.PostResponse;
import com.spring.blog.entity.Post;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.repositories.PostRepository;
import com.spring.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        PostDto postResponse = mapToDto(newPost);
//
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo , int pageSize, String sortBy) {
        //pageable instance
        PageRequest pageable= PageRequest.of(pageNo,pageSize, Sort.by(sortBy));

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content= listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
       Post post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
       return mapToDto(post);

    }

    @Override
    public PostDto updatePost(PostDto postDto , long id) {
        Post post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost= postRepository.save(post);
        return mapToDto(updatedPost);


    }

    @Override
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }


    //converting entity into Dto
    private PostDto mapToDto(Post post){
        PostDto postResponse = new PostDto();
        postResponse.setId(post.getId());
        postResponse.setContent(post.getContent());
        postResponse.setDescription(post.getDescription());
        postResponse.setTitle(post.getTitle());
        return postResponse;
    }
    //converting DTO to entity
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setId( postDto.getId());
        post.setContent( postDto.getContent());
        post.setDescription( postDto.getDescription());
        post.setTitle( postDto.getTitle());
        return post;
    }
}
