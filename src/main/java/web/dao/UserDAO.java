package web.dao;

import web.model.User;

import java.util.List;

public interface UserDAO {

    public List<User> allUsers();

    public void addUser();

    public void changeUser();

    public void deleteUser();


}
