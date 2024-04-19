package com.spring.blog.service;

import com.spring.blog.Dto.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
