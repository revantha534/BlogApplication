package com.spring.blog.repositories;

import com.spring.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 // no need to add bcz jpainternally takes care @Repository and @transactional
public interface PostRepository extends JpaRepository<Post,Long> {

}
