package io.jotech.repository;

import io.jotech.entity.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Long>  {
    List<Post> findAllByUserId(long userId);
}
