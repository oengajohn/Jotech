package io.jotech.repository.impl;

import io.jotech.entity.Todo;
import io.jotech.repository.TodoRepository;
import io.jotech.util.Constants;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class TodoRepositoryImpl extends CrudRepositoryImpl<Todo, Long> implements TodoRepository {
    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public TodoRepositoryImpl() {
        super(Todo.class);
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Todo> findAllByUserId(long userId) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Todo> query = criteriaBuilder.createQuery(Todo.class);
        Root<Todo> from = query.from(Todo.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("user").get("id"),
                                userId
                        )
                );
        return getEntityManager().createQuery(query).getResultList();
    }
}
