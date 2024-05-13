package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.UserDAO;
import web.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public List<User> allUsers() {
        return userDAO.allUsers();
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
