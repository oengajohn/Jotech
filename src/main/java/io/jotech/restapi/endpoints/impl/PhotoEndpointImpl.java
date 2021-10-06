package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.Photo;
import io.jotech.repository.PhotoRepository;
import io.jotech.restapi.endpoints.PhotoEndpoint;
import io.jotech.restapi.endpoints.PostEndpoint;
import io.jotech.restapi.endpoints.RestApiResponse;
import java.net.URI;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PhotoEndpointImpl implements PhotoEndpoint {

  @Inject
  private PhotoRepository photoRepository;

  @Override
  public Response listAllPhotos(Long albumId, int start, int limit) {
    var list = photoRepository.findAllByAlbumId(albumId, start, limit);
    return Response.ok().entity(
        RestApiResponse.builder()
            .success(true)
            .data(list)
            .totalCount(photoRepository.count())
            .build()
    ).build();

  }

  @Override
  public Response createPhoto(Photo photo, UriInfo uriInfo) {
    if (photoRepository.existsById(photo.getId())) {
      return Response.status(Response.Status.CONFLICT).entity(
          RestApiResponse.builder()
              .success(true)
              .msg("Already exists a record")
              .build()
      ).build();
    }
    var savedPhoto = photoRepository.create(photo);
    URI location = uriInfo.getBaseUriBuilder()
        .path(PhotoEndpoint.class)
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
  public Response getPhotoById(long id) {
    Optional<Photo> optionalPost = photoRepository.findById(id);

    return optionalPost.isPresent() ?
        Response.ok().entity(
            RestApiResponse.builder()
                .success(true)
                .data(optionalPost.get())
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
  public Response updatePhoto(long id, Photo photo, UriInfo uriInfo) {
    if (!photoRepository.existsById(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    var updatedPost = photoRepository.edit(photo);
    URI location = uriInfo.getBaseUriBuilder()
        .path(PostEndpoint.class)
        .path(String.valueOf(updatedPost.getId()))
        .build();
    return Response.created(location).entity(
        RestApiResponse.builder()
            .data(updatedPost)
            .success(true)
            .build()
    ).build();

  }

  @Override
  public Response deletePhoto(long id) {
    if (!photoRepository.existsById(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    photoRepository.deleteById(id);
    return Response.ok().entity(RestApiResponse.builder().success(true).build()).build();
  }
}
