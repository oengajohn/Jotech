package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.Comment;
import io.jotech.repository.CommentRepository;
import io.jotech.restapi.endpoints.CommentEndpoint;
import io.jotech.restapi.endpoints.RestApiResponse;
import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class CommentEndpointImpl implements CommentEndpoint {
@Inject
private CommentRepository commentRepository;
  @Override
  public Response listAllComments(Long postId, int start, int limit) {
    var list = commentRepository.findAllByPostId(postId, start, limit);
    return Response.ok().entity(
        RestApiResponse.builder()
            .success(true)
            .data(list)
            .totalCount(commentRepository.count())
            .build()
    ).build();
  }

  @Override
  public Response createComment(Comment comment, UriInfo uriInfo) {
    if (commentRepository.existsById(comment.getId())) {
      return Response.status(Response.Status.CONFLICT).entity(
          RestApiResponse.builder()
              .success(true)
              .msg("Already exists a record")
              .build()
      ).build();
    }
    var savedPhoto = commentRepository.create(comment);
    URI location = uriInfo.getBaseUriBuilder()
        .path(CommentEndpoint.class)
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
  public Response getCommentById(long id) {
    var optionalComment = commentRepository.findById(id);

    return optionalComment.isPresent() ?
        Response.ok().entity(
            RestApiResponse.builder()
                .success(true)
                .data(optionalComment.get())
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
  public Response updateComment(long id, Comment comment, UriInfo uriInfo) {
    if (!commentRepository.existsById(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    var updatedComment = commentRepository.edit(comment);
    URI location = uriInfo.getBaseUriBuilder()
        .path(CommentEndpoint.class)
        .path(String.valueOf(updatedComment.getId()))
        .build();
    return Response.created(location).entity(
        RestApiResponse.builder()
            .data(updatedComment)
            .success(true)
            .build()
    ).build();
  }

  @Override
  public Response deleteComment(long id) {
    if (!commentRepository.existsById(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    commentRepository.deleteById(id);
    return Response.ok().entity(RestApiResponse.builder().success(true).build()).build();

  }
}
