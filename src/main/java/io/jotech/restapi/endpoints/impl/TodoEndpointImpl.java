package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.Todo;
import io.jotech.repository.TodoRepository;
import io.jotech.restapi.endpoints.RestApiResponse;
import io.jotech.restapi.endpoints.TodoEndpoint;
import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class TodoEndpointImpl implements TodoEndpoint {
  @Inject
  private TodoRepository todoRepository;

  @Override
  public Response listAllTodos(Long userId, int start, int limit) {
    var list = todoRepository.findAllByUserId(userId, start, limit);
    return Response.ok().entity(
        RestApiResponse.builder()
            .success(true)
            .data(list)
            .totalCount(todoRepository.count())
            .build()
    ).build();
  }

  @Override
  public Response createTodo(Todo todo, UriInfo uriInfo) {
    if (todoRepository.existsById(todo.getId())) {
      return Response.status(Response.Status.CONFLICT).entity(
          RestApiResponse.builder()
              .success(true)
              .msg("Already exists a record")
              .build()
      ).build();
    }
    var savedTodo = todoRepository.create(todo);
    URI location = uriInfo.getBaseUriBuilder()
        .path(TodoEndpoint.class)
        .path(String.valueOf(savedTodo.getId()))
        .build();

    return Response.created(location).entity(
        RestApiResponse.builder()
            .success(true)
            .data(savedTodo)
            .build()
    ).build();
  }

  @Override
  public Response getTodoById(long id) {
    var optionalTodo = todoRepository.findById(id);

    return optionalTodo.isPresent() ?
        Response.ok().entity(
            RestApiResponse.builder()
                .success(true)
                .data(optionalTodo.get())
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
  public Response updateTodo(long id, Todo todo, UriInfo uriInfo) {
    if (!todoRepository.existsById(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    var updatedTodo = todoRepository.edit(todo);
    URI location = uriInfo.getBaseUriBuilder()
        .path(TodoEndpoint.class)
        .path(String.valueOf(updatedTodo.getId()))
        .build();
    return Response.created(location).entity(
        RestApiResponse.builder()
            .data(updatedTodo)
            .success(true)
            .build()
    ).build();
  }

  @Override
  public Response deleteTodo(long id) {
    if (!todoRepository.existsById(id)) {
      return Response.status(Response.Status.NOT_FOUND).entity(RestApiResponse.builder()
          .success(false)
          .msg("Not found")
          .build()
      ).build();
    }
    todoRepository.deleteById(id);
    return Response.ok().entity(RestApiResponse.builder().success(true).build()).build();

  }
}
