package com.spring.blog.controller;

import com.spring.blog.Dto.PostDto;
import com.spring.blog.Dto.PostResponse;
import com.spring.blog.service.PostService;
import com.spring.blog.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    //creating blog post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        postService.createPost(postDto);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNo",defaultValue= AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                                    @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                                    @RequestParam(value="sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy){

        return new ResponseEntity<>(postService.getAllPosts(pageNo , pageSize , sortBy),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id){

        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name="id") long id){
        PostDto response = postService.updatePost(postDto,id);
        return new ResponseEntity<>( response ,HttpStatus.OK );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name="id") long id){
        postService.deletePostById(id);
        return new ResponseEntity("Deleted successfully" , HttpStatus.OK);
    }


}
