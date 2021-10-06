package io.jotech.repository.impl;

import io.jotech.entity.Album;
import io.jotech.repository.AlbumRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;


public class AlbumRepositoryImpl extends JpaRepositoryImplementation<Album, Long> implements AlbumRepository {
    @Inject
    private EntityManager entityManager;

    public AlbumRepositoryImpl() {
        super(Album.class);
    }
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Album> findAllByUserId(Long userId,int start, int limit) {
      var list=  findAll();
        if (userId!=null){
            return list.stream().filter(album -> album.getUser().getId()==userId)
                .skip(start)
                .limit(limit)
                .collect(Collectors.toList());
        }
        return list.stream().skip(start).limit(limit).collect(Collectors.toList());
    }
}
