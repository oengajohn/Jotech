package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.Album;
import io.jotech.repository.AlbumRepository;
import io.jotech.restapi.endpoints.AlbumEndpoint;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Stateless
public class AlbumEndpointImpl implements AlbumEndpoint {
    @EJB
    private AlbumRepository albumRepository;

    @Override
    public Response listAllAlbums(long userId) {
        return userId > 0 ? Response.ok(albumRepository.findAllByUserId(userId)).build()
                : Response.ok(albumRepository.findAll()).build();
    }

    @Override
    public Response createAlbum(@Valid Album album, UriInfo uriInfo) {
        if (albumRepository.existsById(album.getId())) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        Album savedAlbum = albumRepository.save(album);
        URI location = uriInfo.getBaseUriBuilder()
                .path(AlbumEndpoint.class)
                .path(String.valueOf(savedAlbum.getId()))
                .build();
        return Response.created(location).entity(savedAlbum).build();
    }

    @Override
    public Response getAlbumById(long id) {
        Optional<Album> optionalAlbum = albumRepository.findById(id);
        return optionalAlbum.isPresent() ?
                Response.ok().entity(optionalAlbum.get()).build()
                :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response updateAlbum(long id, @Valid Album album, UriInfo uriInfo) {
        if (!albumRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Album updateAlbum = albumRepository.update(album);
        URI location = uriInfo.getBaseUriBuilder()
                .path(AlbumEndpoint.class)
                .path(String.valueOf(updateAlbum.getId()))
                .build();
        return Response.created(location).entity(updateAlbum).build();
    }

    @Override
    public Response deleteAlbum(long id) {
        if (!albumRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        albumRepository.deleteById(id);
        return Response.ok().build();
    }
}
