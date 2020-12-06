package io.jotech.repository.impl;

import io.jotech.entity.Post;
import io.jotech.repository.PostRepository;
import io.jotech.util.Constants;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class PostRepositoryImpl extends CrudRepositoryImpl<Post, Long> implements PostRepository {
    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public PostRepositoryImpl() {
        super(Post.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Post> findAllByUserId(long userId) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
        Root<Post> from = query.from(Post.class);
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
