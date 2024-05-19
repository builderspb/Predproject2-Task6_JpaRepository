package web.service.UserLogicService;

import web.model.User;

import java.util.List;

public interface UserCacheAndSortedService {
    List<User> getUserListCache();

    void setUserList(List<User> userList);

    User getUserByIdFromList(long id);

    void addUserToCache(User user);

    void updateUserInCache(long id, String newName, String newLastName, String newPhoneNumber, String newEmail);

    void removeUserFromCache(long id);

    List<User> getAllUsersSorted(List<User> users);
}