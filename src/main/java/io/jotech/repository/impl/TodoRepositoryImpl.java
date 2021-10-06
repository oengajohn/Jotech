package io.jotech.repository.impl;

import io.jotech.entity.Todo;
import io.jotech.repository.TodoRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;


public class TodoRepositoryImpl extends JpaRepositoryImplementation<Todo, Long> implements TodoRepository {
    @Inject
    private EntityManager entityManager;

    public TodoRepositoryImpl() {
        super(Todo.class);
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Todo> findAllByUserId(Long userId,int start, int limit) {
        var list = findAll();
        if (userId != null) {
            return list.stream().filter(todo -> todo.getUser().getId() == userId)
                .skip(start)
                .limit(limit)
                .collect(Collectors.toList());
        }
        return list.stream().skip(start).limit(limit).collect(Collectors.toList());

    }
}
