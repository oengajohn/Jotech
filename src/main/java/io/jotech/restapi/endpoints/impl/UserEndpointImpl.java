package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.User;
import io.jotech.repository.UserRepository;
import io.jotech.restapi.endpoints.UserEndpoint;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserEndpointImpl implements UserEndpoint {
    @EJB
    private UserRepository userRepository;
    @Context
    private ResourceContext resourceContext;

    @Override
    public Response listAllUsers() {
        List<User> users = userRepository.findAll();
        return Response.ok(users).build();
    }

    @Override
    public Response createUser(User user, UriInfo uriInfo) {
        System.out.println(user);
        if (userRepository.existsById(user.getId())) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        User savedUser = userRepository.save(user);
        URI location = uriInfo.getBaseUriBuilder()
                .path(UserEndpoint.class)
                .path(String.valueOf(savedUser.getId()))
                .build();
        return Response.created(location).entity(savedUser).build();
    }

    @Override
    public Response getUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.isPresent() ?
                Response.ok().entity(optionalUser.get()).build()
                :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response updateUser(long id, User user, UriInfo uriInfo) {
        if(!userRepository.existsById(id)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        User updateUser = userRepository.update(user);
        URI location = uriInfo.getBaseUriBuilder()
                .path(UserEndpoint.class)
                .path(String.valueOf(updateUser.getId()))
                .build();
        return Response.created(location).entity(updateUser).build();

    }

    @Override
    public Response deleteUser(long id) {
        if(!userRepository.existsById(id)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userRepository.deleteById(id);
        return Response.ok().build();
    }


}
