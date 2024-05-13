package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.dao.UserDAO;
import web.model.User;
import web.service.UserService;

import java.util.List;


@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String allUsers(Model model) {
        List<User> users = userService.allUsers(); // Получение списка пользователей
        model.addAttribute("users", users); // Добавление списка пользователей в модель
        return "view/user-list"; // Возвращаем имя HTML-файла для отображения
    }


}