package com.step.springmvcapp.controller;

import com.step.springmvcapp.entity.Elector;
import com.step.springmvcapp.entity.User;
import com.step.springmvcapp.service.VoitingServiceImpl;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller 
@RequestMapping ("/") 
@ComponentScan(basePackages = "com.step.springmvcapp")
public class UserController {
    @Autowired
    private VoitingServiceImpl service; 
    
    private static final String LOGIN = "users/login";
    private static final String PROFILE = "electors/elector_profile";
    private static final String ADMIN= "users/admin";

    @GetMapping (value="/")
    public String logUser(@ModelAttribute User user, ModelMap model) {
        user = new User();
        model.addAttribute("user", user);
        return LOGIN;  
    } 
    
    @PostMapping (value ="/")
    public String authorizeUser (@ModelAttribute("user") @Valid User user, BindingResult result, 
                                 ModelMap model,HttpSession httpSession){
        if (result.hasErrors()) { 
            return LOGIN;
        }
        List <Elector> list = service.findElectors();
        
        if (service.getAdmin().getLogin().equals(user.getLogin())&
             service.getAdmin().getPassword().equals(user.getPassword())){
            model.addAttribute("electors_list",  list);
            httpSession.setAttribute("user", user);
            return ADMIN; 
        } 
       
        for (Elector electorProfile: list){ 
            if (electorProfile.getLogin().equals(user.getLogin())
                & electorProfile.getPassword().equals(user.getPassword())){
                model.addAttribute("electorProfile",  electorProfile);//!!!var="elector" items="${profile}"
                httpSession.setAttribute("user", user);
                return PROFILE; //"electors/profile"
            }
        }
        model.addAttribute("message", "Wrong username or password!"); 
        model.addAttribute("user", user);
        return LOGIN ; 
        
    }    
       
}
