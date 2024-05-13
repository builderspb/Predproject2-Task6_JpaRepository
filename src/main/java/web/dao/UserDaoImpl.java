package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> allUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Override
    public void addUser() {

    }

    @Override
    public void changeUser() {

    }

    @Override
    public void deleteUser() {

    }
}
