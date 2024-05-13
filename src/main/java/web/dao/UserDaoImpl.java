package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {
    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
