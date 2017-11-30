package com.igor.z.springMigration;

import com.igor.z.entity.User;
import com.igor.z.modelAttributes.RegistrationItem;
import com.igor.z.daos.UserEJB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

    @Autowired
    @Qualifier("registrationFormValidator")
    private Validator validator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView show(ModelMap model) {
        return new ModelAndView("registration", "registrationItem", new RegistrationItem());
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createAccount(@ModelAttribute("registrationItem") @Validated RegistrationItem regItem,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        UserEJB u = new UserEJB();
        if (u.findUserById(regItem.getEmail()) != null) {
            return "registration";
        }
        User user = new User(regItem.getEmail(), regItem.getPassword(), regItem.getUserName());
        u.createUser(user);
        return "notauth";
    }

}
