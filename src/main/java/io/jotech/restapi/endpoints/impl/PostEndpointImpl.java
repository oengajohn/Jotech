package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.Post;
import io.jotech.repository.PostRepository;
import io.jotech.restapi.endpoints.PostEndpoint;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Stateless
public class PostEndpointImpl implements PostEndpoint {
    @EJB
    private PostRepository postRepository;

    @Override
    public Response listAllPosts(long userId) {
        return userId > 0 ? Response.ok(postRepository.findAllByUserId(userId)).build()
                : Response.ok(postRepository.findAll()).build();

    }

    @Override
    public Response createPost(Post post, UriInfo uriInfo) {
        if (postRepository.existsById(post.getId())) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        Post savedPost = postRepository.save(post);
        URI location = uriInfo.getBaseUriBuilder()
                .path(PostEndpoint.class)
                .path(String.valueOf(savedPost.getId()))
                .build();
        return Response.created(location).entity(savedPost).build();
    }

    @Override
    public Response getPostById(long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.isPresent() ?
                Response.ok().entity(optionalPost.get()).build()
                :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response updatePost(long id, Post post, UriInfo uriInfo) {
        if (!postRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Post updatePost = postRepository.update(post);
        URI location = uriInfo.getBaseUriBuilder()
                .path(PostEndpoint.class)
                .path(String.valueOf(updatePost.getId()))
                .build();
        return Response.created(location).entity(updatePost).build();

    }

    @Override
    public Response deletePost(long id) {
        if (!postRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        postRepository.deleteById(id);
        return Response.ok().build();
    }
}
