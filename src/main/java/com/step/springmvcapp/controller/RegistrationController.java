package com.step.springmvcapp.controller;

import com.step.springmvcapp.entity.Elector;
import com.step.springmvcapp.entity.User;
import com.step.springmvcapp.service.VoitingServiceImpl;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller 
@EnableWebMvc
@ComponentScan(basePackages = "com.step.springmvcapp")
public class RegistrationController {

    @Autowired
    private VoitingServiceImpl service;

    private static final String REGISTRATION = "electors/registration";
    private static final String PROFILE = "electors/elector_profile";

    @GetMapping(value = "/registration")
    public String registerElector(ModelMap model) {
        Elector electorProfile = new Elector();
        model.addAttribute("elector", electorProfile);
        return REGISTRATION;
    }

    @PostMapping(value = "/registration")
    public String saveRegistration(@Valid Elector electorProfile,BindingResult result, 
                                    ModelMap model,@ModelAttribute("user") User user,
                                    HttpSession httpSession) {
        if (result.hasErrors()) {
            return REGISTRATION;
        }
        if (!service.findElectors().contains(electorProfile)) {
            this.service.saveElector(electorProfile);
            model.addAttribute("electorProfile", electorProfile);
            //Adding new user in the session 
            user = new User ();
            user.setLogin(electorProfile.getLogin());
            user.setPassword(electorProfile.getPassword());
            httpSession.setAttribute("user", user);
            return PROFILE;
        } else {
            model.addAttribute("failed_registration", "Such user is registered in the system");
            model.addAttribute("elector", electorProfile);
            return REGISTRATION;
        }
    }
    
}
