package web.service.UserDaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDAO;
import web.model.User;
import web.service.UserLogicService.UserCacheAndSortedService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserCacheAndSortedService userCacheAndSortedService;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserCacheAndSortedService userCacheAndSortedService) {
        this.userDAO = userDAO;
        this.userCacheAndSortedService = userCacheAndSortedService;
    }


    // Получить полный список пользователей
    @Override
    @Transactional
    public List<User> getAllUsers() {
        List<User> users = userCacheAndSortedService.getUserListCache();
        if (users == null || users.isEmpty()) {
            users = userDAO.getAllUsers();
            userCacheAndSortedService.setUserList(users);
        }
        return users;
    }


    // Получить пользователя по ID
    @Override
    @Transactional
    public User getUserById(long id) {
        return userDAO.getUserById(id);
    } // не забыть добавить проверку на нал


    // Сохранить нового/измененного пользователя
    @Override
    @Transactional
    public void saveUser(User user) {
        if (user != null) {
            userDAO.saveUser(user);
            userCacheAndSortedService.addUserToCache(user);
        }
    }


    // Обновление пользователя
    @Override
    @Transactional
    public void updateUser(long id, String newName, String newLastName,  String newPhoneNumber, String newEmail) {
        userDAO.updateUser(id, newName, newLastName,newPhoneNumber,newEmail);
        userCacheAndSortedService.updateUserInCache(id, newName, newLastName, newPhoneNumber, newEmail);
    }


    // Удаление пользователя
    @Override
    @Transactional
    public void deleteUser(long id) {
        userDAO.deleteUser(id);
        userCacheAndSortedService.removeUserFromCache(id);
    }
}



