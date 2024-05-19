package web.service.UserLogicService;


import org.springframework.stereotype.Service;
import web.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCacheAndSortedServiceImpl implements UserCacheAndSortedService {
    private List<User> userList = new ArrayList<>();


    // Получить всех пользователей из сохраненного списка(экономим запрос к бд при манипуляции с пользователями)
    @Override
    public List<User> getUserListCache() {
        return this.userList;
    }


    // Установить или перезаписать кэшированного списка пользователей
    @Override
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }


    // Получить пользователя из сохраненного списка(экономим запрос к бд при манипуляции с пользователями)

    @Override
    public User getUserByIdFromList(long id) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }


    // Добавить одного пользователя в существующий кэшированный список.
    @Override
    public void addUserToCache(User user) {
        if (userList != null) {
            userList.add(user);
        }
    }


    // Изменить существующего пользователя в списке
    @Override
    public void updateUserInCache(long id, String newName, String newLastName, String newPhoneNumber, String newEmail) {
        if (userList != null) {
            for (User user : userList) {
                if (user.getId() == id) {
                    user.setName(newName);
                    user.setLastName(newLastName);
                    user.setPhoneNumber(newPhoneNumber);
                    user.setEmail(newEmail);
                    break; // Завершаем цикл после обновления пользователя, чтобы не продолжать ходить по списку
                }
            }
        }
    }


    // Удалить пользователя из списка
    @Override
    public void removeUserFromCache(long id) {
        if (userList != null) {
            userList.removeIf(user -> user.getId() == id);
        }
    }


    // Сортировка пользователей в сохраненном списке
    @Override
    public List<User> getAllUsersSorted(List<User> users) {
        users.sort((u1, u2) -> Long.compare(u1.getId(), u2.getId()));
        return new ArrayList<>(userList); // Возвращаем копию списка для предотвращения изменений
    }
}

