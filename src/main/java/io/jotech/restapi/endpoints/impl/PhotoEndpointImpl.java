package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.Photo;
import io.jotech.repository.PhotoRepository;
import io.jotech.restapi.PhotoEndpoint;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Stateless
public class PhotoEndpointImpl implements PhotoEndpoint {
    @EJB
    private PhotoRepository photoRepository;
    @Override
    public Response listAllPhotos(long albumId) {
        return albumId > 0 ? Response.ok(photoRepository.findAllByAlbumId(albumId)).build()
                : Response.ok(photoRepository.findAll()).build();
    }

    @Override
    public Response createPhoto(@Valid Photo photo, UriInfo uriInfo) {
        if (photoRepository.existsById(photo.getId())) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        Photo savedPhoto = photoRepository.save(photo);
        URI location = uriInfo.getBaseUriBuilder()
                .path(PhotoEndpoint.class)
                .path(String.valueOf(savedPhoto.getId()))
                .build();
        return Response.created(location).entity(savedPhoto).build();
    }

    @Override
    public Response getPhotoById(long id) {
        Optional<Photo> optionalPhoto = photoRepository.findById(id);
        return optionalPhoto.isPresent() ?
                Response.ok().entity(optionalPhoto.get()).build()
                :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response updatePhoto(long id, @Valid Photo photo, UriInfo uriInfo) {
        if (!photoRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Photo updatedPhoto = photoRepository.update(photo);
        URI location = uriInfo.getBaseUriBuilder()
                .path(PhotoEndpoint.class)
                .path(String.valueOf(updatedPhoto.getId()))
                .build();
        return Response.created(location).entity(updatedPhoto).build();
    }

    @Override
    public Response deletePhoto(long id) {
        if (!photoRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        photoRepository.deleteById(id);
        return Response.ok().build();
    }
}
