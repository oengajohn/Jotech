package io.jotech.repository.impl;

import io.jotech.entity.Comment;
import io.jotech.repository.CommentRepository;
import io.jotech.util.Constants;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class CommentRepositoryImpl extends CrudRepositoryImpl<Comment,Long> implements CommentRepository{
    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    public CommentRepositoryImpl() {
        super(Comment.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Comment> findAllByPostId(long postId) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Comment> query = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> from = query.from(Comment.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("post").get("id"),
                                postId
                        )
                );
        return getEntityManager().createQuery(query).getResultList();
    }
}
