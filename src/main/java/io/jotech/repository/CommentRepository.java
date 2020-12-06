package io.jotech.repository;

import io.jotech.entity.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment,Long> {
    List<Comment> findAllByPostId(long postId);
}
