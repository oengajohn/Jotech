package io.jotech.repository;

import io.jotech.entity.Album;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album,Long> {
    List<Album> findAllByUserId(Long userId,int start, int limit);
}
