package com.ll.topcastingbe.domain.qna.post.repository;

import com.ll.topcastingbe.domain.qna.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
