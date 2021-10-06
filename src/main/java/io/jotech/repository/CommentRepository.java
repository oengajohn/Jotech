package io.jotech.repository;

import io.jotech.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostId(Long postId,int start, int limit);
}
