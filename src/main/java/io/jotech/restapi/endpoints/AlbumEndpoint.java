package io.jotech.restapi.endpoints;

import io.jotech.entity.Album;
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

@Path("albums")
@Tag(name = "albums")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public interface AlbumEndpoint {
    @Operation(
            summary = "Get all albums",
            responses = {
                    @ApiResponse(
                            description = "List containing all albums",
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
    Response listAllAlbums(@QueryParam("userId") long userId);


    @Operation(
            summary = "Create album",
            responses = {
                    @ApiResponse(
                            description = "The created album",
                            responseCode = "201",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Album.class
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
    Response createAlbum(@Valid Album album,
                        @Context UriInfo uriInfo);

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Get album by  id",
            responses = {
                    @ApiResponse(
                            description = "The album with id",
                            responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Album.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "album not found"
                    )
            })
    Response getAlbumById(@PathParam("id") long id);

    @Operation(
            summary = "Update album",
            responses = {
                    @ApiResponse(
                            description = "The updated album",
                            responseCode = "200",
                            content =
                            @Content(
                                    schema = @Schema(
                                            implementation = Album.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "album already exists"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }
    )
    @PUT
    @Path("{id}")
    Response updateAlbum(@PathParam("id") long id, @Valid Album album,
                        @Context UriInfo uriInfo);

    @Operation(
            summary = "delete album",
            responses = {
                    @ApiResponse(
                            description = "successful deletion of album",
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
    Response deleteAlbum(@PathParam("id") long id);



}
