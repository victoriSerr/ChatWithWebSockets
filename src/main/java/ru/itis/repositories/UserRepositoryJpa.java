package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.AppUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryJpa implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<AppUser> findByLogin(String login) {
        TypedQuery<AppUser> query =
                entityManager.createQuery("select distinct u from AppUser u where u.login = :login", AppUser.class);

        query.setParameter("login", login);
        AppUser user = query.getSingleResult();
        System.out.println(user);
        return Optional.ofNullable(user);
    }


    @Override
    public Optional<AppUser> findOne(Long aLong) {
        return Optional.ofNullable(entityManager.find(AppUser.class, aLong));
    }

    @Override
    public List<AppUser> findAll() {
        return null;
    }


    @Override
    @Transactional
    public void save(AppUser entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void update(AppUser entity) {
        entityManager.merge(entity);
    }
}
