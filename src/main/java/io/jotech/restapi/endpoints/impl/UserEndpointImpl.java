package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.User;
import io.jotech.repository.UserRepository;
import io.jotech.restapi.endpoints.RestApiResponse;
import io.jotech.restapi.endpoints.UserEndpoint;
import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class UserEndpointImpl implements UserEndpoint {

  @Inject
  private UserRepository userRepository;

  @Override
  public Response listAllUsers(int start, int limit) {
    var list = userRepository.findRange(start, limit);
    return Response.ok().entity(
        RestApiResponse.builder()
            .success(true)
            .data(list)
            .totalCount(userRepository.count())
            .build()
    ).build();
  }

  @Override
  public Response createUser(User user, UriInfo uriInfo) {
    if (userRepository.existsById(user.getId())) {
      return Response.status(Response.Status.CONFLICT).entity(
          RestApiResponse.builder()
              .success(true)
              .msg("Already exists a record")
              .build()
      ).build();
    }
    var savedUser = userRepository.create(user);
    URI location = uriInfo.getBaseUriBuilder()
        .path(UserEndpoint.class)
        .path(String.valueOf(savedUser.getId()))
        .build();

    return Response.created(location).entity(
        RestApiResponse.builder()
            .success(true)
            .data(savedUser)
            .build()
    ).build();
  }

  @Override
  public Response getUserById(long id) {
    var optionalUser = userRepository.findById(id);

    return optionalUser.isPresent() ?
        Response.ok().entity(
            RestApiResponse.builder()
                .success(true)
                .data(optionalUser.get())
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
  public Response updateUser(long id, User user, UriInfo uriInfo) {
    if (userExists(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    var updatedUser = userRepository.edit(user);
    URI location = uriInfo.getBaseUriBuilder()
        .path(UserEndpoint.class)
        .path(String.valueOf(updatedUser.getId()))
        .build();
    return Response.created(location).entity(
        RestApiResponse.builder()
            .data(updatedUser)
            .success(true)
            .build()
    ).build();
  }

  @Override
  public Response deleteUser(long id) {
    if (userExists(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    userRepository.deleteById(id);
    return Response.ok().entity(RestApiResponse.builder().success(true).build()).build();

  }

  private boolean userExists(long id) {
    return !userRepository.existsById(id);
  }
}
