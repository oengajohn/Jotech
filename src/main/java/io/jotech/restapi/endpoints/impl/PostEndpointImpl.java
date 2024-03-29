package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.Post;
import io.jotech.repository.PostRepository;
import io.jotech.restapi.endpoints.PostEndpoint;
import io.jotech.restapi.endpoints.RestApiResponse;
import java.net.URI;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PostEndpointImpl implements PostEndpoint {

  @Inject
  private PostRepository postRepository;

  @Override
  public Response listAllPosts(Long userId, int start, int limit) {
    var list = postRepository.findAllByUserId(userId, start, limit);
    return Response.ok().entity(
        RestApiResponse.builder()
            .success(true)
            .data(list)
            .totalCount(postRepository.count())
            .build()
    ).build();

  }

  @Override
  public Response createPost(Post post, UriInfo uriInfo) {
    if (postRepository.existsById(post.getId())) {
      return Response.status(Response.Status.CONFLICT).entity(
          RestApiResponse.builder()
              .success(true)
              .msg("Already exists a record")
              .build()
      ).build();
    }
    Post savedPost = postRepository.create(post);
    URI location = uriInfo.getBaseUriBuilder()
        .path(PostEndpoint.class)
        .path(String.valueOf(savedPost.getId()))
        .build();

    return Response.created(location).entity(
        RestApiResponse.builder()
            .success(true)
            .data(savedPost)
            .build()
    ).build();
  }

  @Override
  public Response getPostById(long id) {
    Optional<Post> optionalPost = postRepository.findById(id);

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
  public Response updatePost(long id, Post post, UriInfo uriInfo) {
    if (!postRepository.existsById(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    var updatedPost = postRepository.edit(post);
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
  public Response deletePost(long id) {
    if (!postRepository.existsById(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    postRepository.deleteById(id);
    return Response.ok().entity(RestApiResponse.builder().success(true).build()).build();
  }
}
