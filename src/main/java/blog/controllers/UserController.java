package blog.controllers;

import blog.forms.LoginForm;
import blog.services.NotificationService;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notifService;

    @RequestMapping("/users/login")
    public String showLoginForm(LoginForm loginForm) {
        return "users/login";
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String loginPage(@Valid LoginForm lf, BindingResult br) {
        if(br.hasErrors()) {
            notifService.addErrorMessage("Please enter valid login details!");
            return "users/login";
        }
        if(!userService.authenticate(lf.getUsername(), lf.getPassword())) {
            notifService.addErrorMessage("Invalid login!");
            return "users/login";
        }
        //login successful
        notifService.addInfoMessage("Login successful!");
        return "redirect:/";
    }
}
