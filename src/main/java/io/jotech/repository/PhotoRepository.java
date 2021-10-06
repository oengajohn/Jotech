package io.jotech.repository;

import io.jotech.entity.Photo;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo,Long> {

    List<Photo> findAllByAlbumId(Long albumId, int start, int limit);
}
