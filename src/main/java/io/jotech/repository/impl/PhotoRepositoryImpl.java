package io.jotech.repository.impl;

import io.jotech.entity.Photo;
import io.jotech.repository.PhotoRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class PhotoRepositoryImpl extends JpaRepositoryImplementation<Photo,Long> implements PhotoRepository {
    @Inject
    private EntityManager entityManager;
    public PhotoRepositoryImpl() {
        super(Photo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Photo> findAllByAlbumId(Long albumId, int start, int limit) {
      var list= findAll();
      if (albumId!=null){
          return list.stream().filter(photo -> photo.getAlbum().getId()==albumId)
              .skip(start)
              .limit(limit)
              .collect(
              Collectors.toList());
      }
      return list.stream().skip(start).limit(limit).collect(Collectors.toList());
    }
}
