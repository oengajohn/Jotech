package io.jotech.repository.impl;

import io.jotech.entity.Post;
import io.jotech.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;


public class PostRepositoryImpl extends JpaRepositoryImplementation<Post, Long> implements
    PostRepository {

  @Inject
  private EntityManager entityManager;

  public PostRepositoryImpl() {
    super(Post.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public List<Post> findAllByUserId(Long userId, int start, int limit) {
    var list = findAll();
    if (userId != null) {
      return list.stream().filter(post -> post.getUser().getId() == userId)
          .skip(start)
          .limit(limit)
          .collect(Collectors.toList());
    }
    return list.stream().skip(start).limit(limit).collect(Collectors.toList());
  }
}
