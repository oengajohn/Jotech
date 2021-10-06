package io.jotech.repository.impl;

import io.jotech.entity.Comment;
import io.jotech.repository.CommentRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;


public class CommentRepositoryImpl extends JpaRepositoryImplementation<Comment, Long> implements
    CommentRepository {

  @Inject
  private EntityManager entityManager;

  public CommentRepositoryImpl() {
    super(Comment.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public List<Comment> findAllByPostId(Long postId, int start, int limit) {
    var list = findAll();
    if (postId != null) {
      return list.stream().filter(comment -> comment.getPost().getId() == postId)
          .skip(start)
          .limit(limit)
          .collect(
              Collectors.toList());
    }
    return list.stream().skip(start).limit(limit).collect(Collectors.toList());

  }
}
