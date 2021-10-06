package io.jotech.repository.impl;

import io.jotech.entity.User;
import io.jotech.repository.UserRepository;
import javax.inject.Inject;
import javax.persistence.EntityManager;
public class UserRepositoryImpl extends JpaRepositoryImplementation<User,Long> implements UserRepository {
    @Inject
    private EntityManager entityManager;
    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
