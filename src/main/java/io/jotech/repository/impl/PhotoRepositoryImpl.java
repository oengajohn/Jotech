package io.jotech.repository.impl;

import io.jotech.entity.Photo;
import io.jotech.repository.PhotoRepository;
import io.jotech.util.Constants;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
@Stateless
public class PhotoRepositoryImpl extends CrudRepositoryImpl<Photo,Long> implements PhotoRepository {
    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    public PhotoRepositoryImpl() {
        super(Photo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Photo> findAllByAlbumId(long postId) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Photo> query = criteriaBuilder.createQuery(Photo.class);
        Root<Photo> from = query.from(Photo.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("album").get("id"),
                                postId
                        )
                );
        return getEntityManager().createQuery(query).getResultList();
    }
}
