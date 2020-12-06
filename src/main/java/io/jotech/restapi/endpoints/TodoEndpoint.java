package io.jotech.restapi.endpoints;

import io.jotech.entity.Todo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("todos")
@Tag(name = "todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public interface TodoEndpoint {
    @Operation(
            summary = "Get all todos",
            responses = {
                    @ApiResponse(
                            description = "List containing all todos",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Todo.class
                                            )
                                    )
                            )
                    )

            })
    @GET
    Response listAllTodos(@QueryParam("userId") long userId);


    @Operation(
            summary = "Create todo",
            responses = {
                    @ApiResponse(
                            description = "The created todo",
                            responseCode = "201",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Todo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request"
                    )


            }
    )
    @POST
    Response createTodo(@Valid Todo todo,
                        @Context UriInfo uriInfo);

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Get todo by  id",
            responses = {
                    @ApiResponse(
                            description = "The todo with id",
                            responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Todo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "todo not found"
                    )
            })
    Response getTodoById(@PathParam("id") long id);

    @Operation(
            summary = "Update todo",
            responses = {
                    @ApiResponse(
                            description = "The updated todo",
                            responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Todo.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "todo already exists"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }
    )
    @PUT
    @Path("{id}")
    Response updateTodo(@PathParam("id") long id, @Valid Todo todo,
                        @Context UriInfo uriInfo);

    @Operation(
            summary = "delete todo",
            responses = {
                    @ApiResponse(
                            description = "successful deletion of todo",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }
    )
    @DELETE
    @Path("{id}")
    Response deleteTodo(@PathParam("id") long id);



}
