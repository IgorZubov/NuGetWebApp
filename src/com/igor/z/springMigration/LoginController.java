package com.igor.z.springMigration;

import com.igor.z.entity.User;
import com.igor.z.daos.UserEJB;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping(value = "/")
    public String welcome(Model model) {
        model.addAttribute("name", "Home Page");
        model.addAttribute("description", "unsecured page !");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/sigin", method = RequestMethod.POST)
    String signin(HttpServletRequest request, ModelMap modelMap, @RequestParam("username") String username,
                        @RequestParam("password") String password, Model model) {

        try {
            request.login(username, password);
        } catch (ServletException e) {
                model.addAttribute("error", e.getMessage());
                return "/login";
        }
        Principal principal = request.getUserPrincipal();
        UserEJB u = new UserEJB();
        User user = u.findUserById(principal.getName());
        modelMap.put("User", user);
        if (request.isUserInRole("users")) {
            return "redirect:/user/feedmanager";
        } else {
            return "redirect:/login";
        }
    }
}
