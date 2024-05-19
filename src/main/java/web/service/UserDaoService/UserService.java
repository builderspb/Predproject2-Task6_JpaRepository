package web.service.UserDaoService;

import web.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(long id);

    void saveUser(User user);

    void updateUser(long id, String newName, String newLastName, String newPhoneNumber, String newEmail);

    void deleteUser(long id);

}
