package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.dao.UserDAO;
import web.model.User;

import java.util.List;


@Controller
public class UserController {

    private final UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @GetMapping("/")
    public String getUsers(Model model) {
        List<User> users = userDAO.allUsers(); // Получение списка пользователей
        model.addAttribute("users", users); // Добавление списка пользователей в модель
        return "view/user-list"; // Возвращаем имя HTML-файла для отображения
    }


}