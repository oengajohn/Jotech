package io.jotech.repository.impl;

import io.jotech.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CrudRepositoryImpl<T, ID> implements CrudRepository<T, ID> {
    private final Class<T> entityClass;

    protected abstract EntityManager getEntityManager();

    public CrudRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public long count() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> from = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.count(from));
        TypedQuery<Long> q = getEntityManager().createQuery(criteriaQuery);
        return q.getSingleResult();
    }

    @Override
    public void delete(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public void deleteAll() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> from = criteriaQuery.from(entityClass);
        criteriaQuery.select(from);
        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
        List<T> resultList = query.getResultList();
        for (T entity : resultList) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll(List<T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteById(ID id) {
        Optional<T> optionalT = findById(id);
        optionalT.ifPresent(this::delete);
    }

    @Override
    public boolean existsById(ID id) {
        Optional<T> optionalT = findById(id);
        return optionalT.isPresent();
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = criteriaBuilder.createQuery(entityClass);
        Root<T> from = cq.from(entityClass);
        cq.select(from);
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<T> findAllById(List<ID> ids) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = criteriaBuilder.createQuery(entityClass);
        Root<T> from = cq.from(entityClass);
        cq.select(from).where(from.get("id").in(ids));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public Optional<T> findById(ID id) {
        T t = getEntityManager().find(entityClass, id);
        if (t != null) {
            return Optional.of(t);
        }
        return Optional.empty();
    }

    @Override
    public List<T> findRange(int[] range) {
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        TypedQuery<T> q = getEntityManager().createQuery(cq);
        q.setFirstResult(range[0]);//set offset
        q.setMaxResults(range[1] - range[0] + 1);//limit
        return q.getResultList();
    }

    @Override
    public T save(T entity) {
        getEntityManager().persist(entity);
          return entity;
    }

    @Override
    public  List<T> saveAll(List<T> entities) {
        List<T> savedEntities =new ArrayList<>();
        for (T entity : entities) {
            savedEntities.add(save(entity));
        }
        return savedEntities;
    }

    @Override
    public T update(T entity) {
        getEntityManager().merge(entity);
        return entity;
    }

    @Override
    public List<T> updateAll(List<T> entities) {
        List<T> updatedEntities =new ArrayList<>();
        for (T entity : entities) {
            updatedEntities.add(update(entity));
        }
        return updatedEntities;
    }
}
