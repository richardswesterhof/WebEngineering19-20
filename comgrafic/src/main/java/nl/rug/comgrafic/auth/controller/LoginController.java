package nl.rug.comgrafic.auth.controller;

import nl.rug.comgrafic.auth.model.User;
//import nl.rug.comgrafic.auth.service.UserService;
import nl.rug.comgrafic.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for login related actions
 */
@Controller
@RequestMapping("login")
public class  LoginController {

    private UserService userService;

    @Autowired
    public LoginController (UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("allUsers")
    public List<User> allUsers() {
        return userService.getAllUsers ();
    }

    @GetMapping
    public String showPage () {
        return "login";
    }
}
