package io.jotech.repository;

import io.jotech.entity.Todo;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo,Long>{
    List<Todo> findAllByUserId(long userId);
}
