package com.igor.z.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping(value = "/")
    public String welcome(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if(principal!= null && principal.getName() != null)
            return "redirect:/user/feedmanager";
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if(principal!= null && principal.getName() != null)
            return "redirect:/user/feedmanager";
        return "login";
    }

    @RequestMapping(value = "/sigin", method = RequestMethod.POST)
    String signin(HttpServletRequest request, @RequestParam("username") String username,
                  @RequestParam("password") String password, Model model) {
        Principal principal = request.getUserPrincipal();
        if(principal!= null && principal.getName() != null)
            return "redirect:/user/feedmanager";
        try {
            request.login(username, password);
        } catch (ServletException e) {
                model.addAttribute("error", e.getMessage());
                return "/login";
        }
        if (request.isUserInRole("users")) {
            return "redirect:/user/feedmanager";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    String logout(HttpServletRequest request,
                  Model model, final RedirectAttributes redirectAttributes) {
        try {
            request.logout();
        } catch (ServletException e) {
            model.addAttribute("error", e.getMessage());
            return "/login";
        }
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "You are successfully logged out!");
        return "redirect:/login";
    }
}
