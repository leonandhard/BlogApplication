package com.leonhard.blog.repository;

import com.leonhard.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<Comment, Long> {
}
