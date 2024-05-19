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


    // Получить полный список юзеров
    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    // Получить юзера по ID
    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }


    // Сохранить нового/измененного юзера
    @Override
    public void saveUser(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }


    // Обновление юзера по ID
    @Override
    public void updateUser(long id, String newName, String newLastName,  String newPhoneNumber, String newEmail) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setName(newName);
            user.setLastName(newLastName);
            user.setPhoneNumber(newPhoneNumber);
            user.setEmail(newEmail);
        }
    }


    // Удаление юзера по ID
    @Override
    public void deleteUser(long id) {
        User user = entityManager.find(User.class, id);
        if (user.getId() != null) {
            entityManager.remove(user);
        }
    }
}
