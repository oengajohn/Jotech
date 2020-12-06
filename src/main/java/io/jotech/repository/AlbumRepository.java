package io.jotech.repository;

import io.jotech.entity.Album;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album,Long>{
    List<Album> findAllByUserId(long userId);
}
