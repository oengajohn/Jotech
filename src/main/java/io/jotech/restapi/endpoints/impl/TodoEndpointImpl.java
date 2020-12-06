package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.Todo;
import io.jotech.repository.TodoRepository;
import io.jotech.restapi.endpoints.TodoEndpoint;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Stateless
public class TodoEndpointImpl implements TodoEndpoint {
    @EJB
    private TodoRepository todoRepository;

    @Override
    public Response listAllTodos(long userId) {
        return userId > 0 ? Response.ok(todoRepository.findAllByUserId(userId)).build()
                : Response.ok(todoRepository.findAll()).build();
    }

    @Override
    public Response createTodo(@Valid Todo todo, UriInfo uriInfo) {
        if (todoRepository.existsById(todo.getId())) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        Todo savedTodo = todoRepository.save(todo);
        URI location = uriInfo.getBaseUriBuilder()
                .path(TodoEndpoint.class)
                .path(String.valueOf(savedTodo.getId()))
                .build();
        return Response.created(location).entity(savedTodo).build();
    }

    @Override
    public Response getTodoById(long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        return optionalTodo.isPresent() ?
                Response.ok().entity(optionalTodo.get()).build()
                :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response updateTodo(long id, @Valid Todo todo, UriInfo uriInfo) {
        if (!todoRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Todo updateTodo = todoRepository.update(todo);
        URI location = uriInfo.getBaseUriBuilder()
                .path(TodoEndpoint.class)
                .path(String.valueOf(updateTodo.getId()))
                .build();
        return Response.created(location).entity(updateTodo).build();
    }

    @Override
    public Response deleteTodo(long id) {
        if (!todoRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        todoRepository.deleteById(id);
        return Response.ok().build();
    }
}
