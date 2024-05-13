package web.service;

import web.model.User;

import java.util.List;

public interface UserService {

    public List<User> allUsers();

    public void addUser();

    public void changeUser();

    public void deleteUser();
}
