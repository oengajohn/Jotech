package io.jotech.restapi.endpoints;

import io.jotech.entity.Comment;
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

@Path("comments")
@Tag(name = "comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CommentEndpoint {
    @Operation(
            summary = "Get all comments",
            responses = {
                    @ApiResponse(
                            description = "List containing all comments",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Comment.class
                                            )
                                    )
                            )
                    )

            })
    @GET
    Response listAllComments(@QueryParam("postId") long postId);

    @Operation(
            summary = "Create comment",
            responses = {
                    @ApiResponse(
                            description = "The created comment",
                            responseCode = "201",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Comment.class
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
    Response createComment(@Valid Comment comment,
                        @Context UriInfo uriInfo);

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Get comment by id",
            responses = {
                    @ApiResponse(
                            description = "The comment with id",
                            responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Comment.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "comment not found"
                    )
            })
    Response getCommentById(@PathParam("id") long id);

    @Operation(
            summary = "Update comment",
            responses = {
                    @ApiResponse(
                            description = "The comment",
                            responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Comment.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "comment already exists"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }
    )
    @PUT
    @Path("{id}")
    Response updateComment(@PathParam("id") long id, @Valid Comment comment,
                        @Context UriInfo uriInfo);

    @Operation(
            summary = "delete comment",
            responses = {
                    @ApiResponse(
                            description = "successful deletion of comment",
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
    Response deleteComment(@PathParam("id") long id);

}
