package io.jotech.repository;

import io.jotech.entity.Photo;

import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo,Long> {
    List<Photo> findAllByAlbumId(long albumId);
}
