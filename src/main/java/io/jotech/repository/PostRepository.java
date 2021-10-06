package io.jotech.repository;

import io.jotech.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByUserId(Long userId,int start,int limit);
}
