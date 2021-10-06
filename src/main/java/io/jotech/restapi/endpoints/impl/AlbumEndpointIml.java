package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.Album;
import io.jotech.repository.AlbumRepository;
import io.jotech.restapi.endpoints.AlbumEndpoint;
import io.jotech.restapi.endpoints.RestApiResponse;
import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class AlbumEndpointIml implements AlbumEndpoint {
  @Inject
  private AlbumRepository albumRepository;

  @Override
  public Response listAllAlbums(Long userId, int start, int limit) {
    var list = albumRepository.findAllByUserId(userId, start, limit);
    return Response.ok().entity(
        RestApiResponse.builder()
            .success(true)
            .data(list)
            .totalCount(albumRepository.count())
            .build()
    ).build();
  }

  @Override
  public Response createAlbum(Album album, UriInfo uriInfo) {
    if (albumRepository.existsById(album.getId())) {
      return Response.status(Response.Status.CONFLICT).entity(
          RestApiResponse.builder()
              .success(true)
              .msg("Already exists a record")
              .build()
      ).build();
    }
    var savedPhoto = albumRepository.create(album);
    URI location = uriInfo.getBaseUriBuilder()
        .path(AlbumEndpoint.class)
        .path(String.valueOf(savedPhoto.getId()))
        .build();

    return Response.created(location).entity(
        RestApiResponse.builder()
            .success(true)
            .data(savedPhoto)
            .build()
    ).build();
  }

  @Override
  public Response getAlbumById(long id) {
    var optionalAlbum = albumRepository.findById(id);

    return optionalAlbum.isPresent() ?
        Response.ok().entity(
            RestApiResponse.builder()
                .success(true)
                .data(optionalAlbum.get())
                .build()
        ).build()
        :
            Response.status(Response.Status.NOT_FOUND)
                .entity(RestApiResponse.builder()
                    .success(false)
                    .msg("Not found")
                    .build()
                ).build();
  }

  @Override
  public Response updateAlbum(long id, Album album, UriInfo uriInfo) {
    if (!albumRepository.existsById(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    var updatedAlbum = albumRepository.edit(album);
    URI location = uriInfo.getBaseUriBuilder()
        .path(AlbumEndpoint.class)
        .path(String.valueOf(updatedAlbum.getId()))
        .build();
    return Response.created(location).entity(
        RestApiResponse.builder()
            .data(updatedAlbum)
            .success(true)
            .build()
    ).build();
  }

  @Override
  public Response deleteAlbum(long id) {
    if (!albumRepository.existsById(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    albumRepository.deleteById(id);
    return Response.ok().entity(RestApiResponse.builder().success(true).build()).build();

  }
}
