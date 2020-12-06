package io.jotech.restapi.endpoints.impl;

import io.jotech.entity.Comment;
import io.jotech.repository.CommentRepository;
import io.jotech.restapi.endpoints.CommentEndpoint;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Stateless
public class CommentEndpointImpl implements CommentEndpoint {
    @EJB
    private CommentRepository commentRepository;
    @Override
    public Response listAllComments(long postId) {
        return postId > 0 ? Response.ok(commentRepository.findAllByPostId(postId)).build()
                : Response.ok(commentRepository.findAll()).build();

    }

    @Override
    public Response createComment(@Valid Comment comment, UriInfo uriInfo) {
        if (commentRepository.existsById(comment.getId())) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        Comment savedComment = commentRepository.save(comment);
        URI location = uriInfo.getBaseUriBuilder()
                .path(CommentEndpoint.class)
                .path(String.valueOf(savedComment.getId()))
                .build();
        return Response.created(location).entity(savedComment).build();
    }

    @Override
    public Response getCommentById(long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.isPresent() ?
                Response.ok().entity(optionalComment.get()).build()
                :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response updateComment(long id, @Valid Comment comment, UriInfo uriInfo) {
        if (!commentRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Comment updateComment = commentRepository.update(comment);
        URI location = uriInfo.getBaseUriBuilder()
                .path(CommentEndpoint.class)
                .path(String.valueOf(updateComment.getId()))
                .build();
        return Response.created(location).entity(updateComment).build();
    }

    @Override
    public Response deleteComment(long id) {
        if (!commentRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        commentRepository.deleteById(id);
        return Response.ok().build();
    }
}
