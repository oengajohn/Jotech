package io.jotech.repository;

import io.jotech.entity.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findAllByUserId(Long userId,int start, int limit);
}
