package io.jotech.restapi.endpoints;

import io.jotech.entity.Post;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("posts")
@Tag(name = "posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PostEndpoint {
    @Operation(
            summary = "Get all posts",
            responses = {
                    @ApiResponse(
                            description = "List containing all posts",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Post.class
                                            )
                                    )
                            )
                    )

            })
    @GET
    Response listAllPosts(@QueryParam("userId") long userId);

    @Operation(
            summary = "Create post",
            responses = {
                    @ApiResponse(
                            description = "The created post",
                            responseCode = "201",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Post.class
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
    Response createPost(@Valid Post user,
                        @Context UriInfo uriInfo);

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Get post by  id",
            responses = {
                    @ApiResponse(
                            description = "The post with id",
                            responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Post.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "post not found"
                    )
            })
    Response getPostById(@PathParam("id") long id);

    @Operation(
            summary = "Update post",
            responses = {
                    @ApiResponse(
                            description = "The updated post",
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
                            description = "post already exists"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }
    )
    @PUT
    @Path("{id}")
    Response updatePost(@PathParam("id") long id, @Valid Post user,
                        @Context UriInfo uriInfo);

    @Operation(
            summary = "delete post",
            responses = {
                    @ApiResponse(
                            description = "successful deletion of post",
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
    Response deletePost(@PathParam("id") long id);

}
