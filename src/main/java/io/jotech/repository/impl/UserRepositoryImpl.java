package io.jotech.repository.impl;

import io.jotech.entity.User;
import io.jotech.repository.UserRepository;
import io.jotech.util.Constants;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Stateless
public class UserRepositoryImpl extends CrudRepositoryImpl<User,Long> implements UserRepository {
    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
