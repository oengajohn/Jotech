package io.jotech.restapi.endpoints;

import io.jotech.entity.User;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("users")
@Tag(name = "users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public interface UserEndpoint {
    @Operation(
            summary = "Get all users",
            responses = {
                    @ApiResponse(
                            description = "List containing all users",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = User.class
                                            )
                                    )
                            )
                    )

            })
    @GET
    Response listAllUsers();


    @Operation(
            summary = "Create user",
            responses = {
                    @ApiResponse(
                            description = "The created user",
                            responseCode = "201",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = User.class
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
    Response createUser(@Valid User user,
                        @Context UriInfo uriInfo);

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Get user by  id",
            responses = {
                    @ApiResponse(
                            description = "The user with id",
                            responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = User.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    )
            })
    Response getUserById(@PathParam("id") long id);

    @Operation(
            summary = "Update user",
            responses = {
                    @ApiResponse(
                            description = "The updated user",
                            responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = User.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User already exists"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }
    )
    @POST
    @Path("{id}")
    Response updateUser(@PathParam("id") long id, @Valid User user,
                        @Context UriInfo uriInfo);

    @Operation(
            summary = "delete user",
            responses = {
                    @ApiResponse(
                            description = "successful deletion of user",
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
    Response deleteUser(@PathParam("id") long id);



}
