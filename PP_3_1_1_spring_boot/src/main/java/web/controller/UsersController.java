package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userslist";
    }

    @GetMapping("/users/new")
    public String showAddPage(Model model) {
        model.addAttribute("users", new User());
        return "addoredituser";
    }

    @PostMapping("/users/add")
    public String addUser(@Valid @ModelAttribute("users") User user,
                          BindingResult result) {

        if (result.hasErrors()) {
            return "addoredituser";
        }

        userService.saveUser(user);

        return "redirect:/users";
    }

    @GetMapping("/users/view")
    public String showEditPage(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "userview";
    }

    @PostMapping("/users/update")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "addoredituser";
        }

        User existing = userService.getUserById(user.getId());
        if (existing == null) {
            return "redirect:/users";
        }

        userService.updateUser(user);

        return "redirect:/users";
    }

    @GetMapping("/users/edit")
    public String editUser(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "addoredituser";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/users";
        }
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
