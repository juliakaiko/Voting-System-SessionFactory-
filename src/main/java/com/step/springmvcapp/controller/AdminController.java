package com.step.springmvcapp.controller;

import com.step.springmvcapp.entity.Candidate;
import com.step.springmvcapp.entity.CandidateDetails;
import com.step.springmvcapp.entity.Elector;
import com.step.springmvcapp.entity.User;
import com.step.springmvcapp.service.VoitingServiceImpl;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller 
@SessionAttributes("user")
@ComponentScan(basePackages = "com.step.springmvcapp")
public class AdminController {
    
    @Autowired
    private VoitingServiceImpl service;
    
    private static final String REGISTRATION = "electors/registration";
    private static final String PROFILE = "electors/elector_profile";
    private static final String ADMIN= "users/admin";
    private static final String ELECTORS_LIST="electors/electors_list";
    private static final String CANDIDATE_LIST="candidates/candidates_list";
    
    @GetMapping(value = "/Electors")
    public String showElectors(@ModelAttribute("user") User user, ModelMap model) {
        List <Elector> electors = service.findElectors();
        if (electors.isEmpty()) {
            model.addAttribute("message", "The list of electors is empty");
            return ADMIN;
        }
        model.addAttribute("electors_list",  electors);
        return ELECTORS_LIST;
    }
    
    //@RequestMapping(value = "/Electors/edit/{id}", method = RequestMethod.GET)
    @GetMapping(value = "/Electors/edit/{id}")
    public ModelAndView editPageForElector(@PathVariable("id") Long id,
                                 @ModelAttribute("message") String message,
                                 HttpSession httpSession ) {
        Elector elector = service.findElectorById(id);
        ModelAndView modelAndView = new ModelAndView();
        
        User user =(User)httpSession.getAttribute("user");
        User admin = service.getAdmin();

        if (elector.isVoted()==true){
            modelAndView.addObject("novoting", "You can't change your data after you've voted");
            modelAndView.addObject("electorProfile", elector);
            modelAndView.setViewName(PROFILE);
        } else {
        //if (elector.isVoted()==false) {
            modelAndView.addObject("elector", elector);
            modelAndView.setViewName(REGISTRATION);
        }
        if (user.getLogin().equals(admin.getLogin())&
            user.getPassword().equals(admin.getPassword())   ) {
            modelAndView.addObject("elector", elector);
            modelAndView.setViewName(REGISTRATION);
        }
        return modelAndView;
    }

    //@RequestMapping(value = "/Electors/edit/{id}", method = RequestMethod.POST)
    @PostMapping (value = "/Electors/edit/{id}")
    public ModelAndView editElector(@ModelAttribute("elector") Elector elector, 
            HttpSession httpSession,BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName(REGISTRATION);
        }         
        User user =(User)httpSession.getAttribute("user");
        User admin = service.getAdmin();
        if (user.getLogin().equals(admin.getLogin())&
             user.getPassword().equals(admin.getPassword())   ) {
            this.service.saveElector(elector);
            //modelAndView.addObject("message","Data has been successfully changed");
            modelAndView.setViewName("redirect:/Electors/");
        } else {
            this.service.saveElector(elector);
            modelAndView.setViewName(PROFILE);
            modelAndView.addObject("electorProfile", elector);
        }
        return modelAndView;
    }
    
    
    @PostMapping(value = "/Electors/delete/{id}")
    public String deleteElector(@PathVariable("id") Long id, ModelMap model) {//@ModelAttribute("message") String message,
        Elector elector = service.findElectorById(id);
        service.deleteElectorById(id);
        model.addAttribute("message", elector.getFirstName() +" "+elector.getLastName()+ " has been deleted");
        return "redirect:/Electors/";
     
    }
    
    //@RequestMapping(value = "/Candidates/edit/{id}", method = RequestMethod.GET)
    @GetMapping(value = "/Candidates/edit/{id}")
    public ModelAndView editPageForCandidate(@PathVariable("id") Long id,
                                 //@ModelAttribute("message") String message,
                                 HttpSession httpSession ) {
        Candidate candidate = service.findCandidateById(id);
        //CandidateDetails details = service.findCandidateDetailsById(id);
        ModelAndView modelAndView = new ModelAndView();
        
        User user =(User)httpSession.getAttribute("user");
        User admin = service.getAdmin();

        if (user.getLogin().equals(admin.getLogin())&
            user.getPassword().equals(admin.getPassword())) {
            modelAndView.addObject("candidate", candidate);
            //modelAndView.addObject("details", details);
            modelAndView.setViewName("candidates/editPage"); //editPage
            return modelAndView;
        } 
        //else {
            modelAndView.addObject("message", "This feature is available for only  administrator");
            modelAndView.setViewName("redirect:/Candidates/");
        //}
        
        return modelAndView;
    }
    
    @PostMapping (value = "/Candidates/edit/{id}")
    public String editCandidate(@ModelAttribute("candidate") Candidate cand,
            BindingResult result,ModelMap model,@PathVariable("id") Long id) {      
        if (result.hasErrors()) {
            model.addAttribute("candidates/editPage");
        }  
        //Candidate candidate = service.findCandidateById(id);
        //CandidateDetails details=  service.findCandidateDetailsById(id);//candidate.getDetails();
        //cand.setDetails(details);
        //details.setId(id);
        this.service.saveCandidate(cand); 
        //model.addAttribute("test", cand);
        //return "test";
        //model.addAttribute("message","Data has been successfully changed");
        return "redirect:/Candidates/";
    }
    
    
    @PostMapping(value = "/Candidates/delete/{id}")
    public String deleteCandidate(@PathVariable("id") Long id, ModelMap model,HttpSession httpSession ) {//@ModelAttribute("message") String message,
        Candidate candidate = service.findCandidateById(id);
 
        User user =(User)httpSession.getAttribute("user");
        User admin = service.getAdmin();
        if (user.getLogin().equals(admin.getLogin())&
            user.getPassword().equals(admin.getPassword())) {
            service.deleteCandidateById(id);
            model.addAttribute("message", candidate.getFirstName() +" "+candidate.getLastName()+ " has been deleted");
        } else {
            model.addAttribute("message", "This feature is available for only  administrator");
        }
        return "redirect:/Candidates/";
    }
    
    @GetMapping(value="/CandidateDetails/edit/{id}")
    public ModelAndView editPageForCandidateDetails(@PathVariable("id") Long id,
                                 HttpSession httpSession ) { 
        ModelAndView modelAndView = new ModelAndView();
        //Candidate candidate = service.findCandidateById(id);
        //CandidateDetails details = candidate.getDetails() ;//service.findCandidateDetailsById(id); //         
         CandidateDetails details = service.findCandidateDetailsById(id);
        
        User user =(User)httpSession.getAttribute("user");
        User admin = service.getAdmin();
        if (user.getLogin().equals(admin.getLogin())&
            user.getPassword().equals(admin.getPassword())) {
//            if (details.getId()==null){
//                details = new CandidateDetails ();  
//                details.setId(id);
//                candidate.setDetails(details);
//                details.setCandidate(candidate);  
//                //this.service.saveCandidateDetails(details);
//                //this.service.saveCandidate(candidate);
//            }
            modelAndView.addObject("details", details);
            modelAndView.setViewName("candidates/editPageForDetails");
        } else {
            modelAndView.addObject("message", "This feature is available for only  administrator");
            modelAndView.setViewName("redirect:/Candidates/"+id);
        }
        
        return modelAndView;
    }
  
    @PostMapping (value="/CandidateDetails/edit/{id}")
    public ModelAndView editCandidateDetails(@PathVariable("id") Long id,
            @ModelAttribute("details") CandidateDetails details, 
            ModelMap model,BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        Candidate candidate = service.findCandidateById(id);
        if (result.hasErrors()) {
            modelAndView.setViewName("redirect:/Candidates/"+id);
        }
         if (details.getId()==null){
                details = new CandidateDetails ();  
                details.setId(id);
                candidate.setDetails(details);
                details.setCandidate(candidate);  
                //this.service.saveCandidateDetails(details);
                //this.service.saveCandidate(candidate);
            }

//        Candidate candidate = service.findCandidateById(id);
//        details.setId(id);
//        candidate.setDetails(details);
//        this.service.saveCandidate(candidate);
        //details.setId(id);
        
        //this.service.saveCandidateDetails(details); // не отрабатывает!
        
      //  modelAndView.addObject("test", candidate);
        modelAndView.addObject("test2", details);
        modelAndView.setViewName("test");
        //modelAndView.addObject("message","Data has been successfully changed");
        //modelAndView.setViewName("redirect:/Candidates/"+details.getId());
        return modelAndView;
    }

    @GetMapping(value = "/Candidates/add")
    public String addCandidate(ModelMap model, HttpSession httpSession ) {
        Candidate candidate = new Candidate();
        User user =(User)httpSession.getAttribute("user");
        User admin = service.getAdmin();

        if (user.getLogin().equals(admin.getLogin())&
            user.getPassword().equals(admin.getPassword())) {
            model.addAttribute("candidate", candidate);
            return "candidates/editPage";
        }
        model.addAttribute("message", "This feature is available for only  administrator");
        return "redirect:/Candidates/";
    }

    @PostMapping(value = "/Candidates/add")
    public String saveCandidate(@Valid Candidate candidate,BindingResult result, 
                                    ModelMap model) {
        if (result.hasErrors()) {
            return REGISTRATION;
        }
        if (!service.findCandidates().contains(candidate)) {
            this.service.saveCandidate(candidate);
        } else {
            model.addAttribute("failed_registration", "Such candidate is registered in the system");
        }
        //model.addAttribute("candidate", candidate);
        return "redirect:/Candidates/";
    }
    
    
}
