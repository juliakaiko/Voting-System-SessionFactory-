package com.step.springmvcapp.controller;

import com.step.springmvcapp.entity.Candidate;
import com.step.springmvcapp.entity.CandidateDetails;
import com.step.springmvcapp.entity.Elector;
import com.step.springmvcapp.entity.User;
import com.step.springmvcapp.service.VoitingServiceImpl;
import java.util.ArrayList;
import java.util.List;
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

@Controller
@SessionAttributes("user")
@ComponentScan(basePackages = "com.step.springmvcapp")
public class VotingControler {
    @Autowired
    private VoitingServiceImpl service;
    
    private static final String ELECTOR_PROFILE = "electors/elector_profile";
    private static final String CANDIDATES_LIST = "candidates/candidates_list";
    private static final String CANDIDATES_DETAILS = "candidates/details";
    private static final String WINNERS = "candidates/winner";
    
    @GetMapping(value = "/Candidates") // /vote
    public String showCandidates(@ModelAttribute("user") User user, ModelMap model) {
        List<Candidate> candidates = service.findCandidates();
        if (candidates.isEmpty()) {
            List<Elector> list = service.findElectors();
            for (Elector electorProfile : list) { //el.getLogin().equals(user.getLogin())& el.getPassword().equals(user.getPassword())
                if (electorProfile.getLogin().equals(user.getLogin())
                    & electorProfile.getPassword().equals(user.getPassword())) {
                    model.addAttribute("message", "The list of candidates is empty");
                    model.addAttribute("elector", electorProfile);
                    return ELECTOR_PROFILE;
                }
            }
        }
        model.addAttribute("candidates", candidates);
        return CANDIDATES_LIST;
    }

   @GetMapping(value = "/Candidates/{id}")
    public String showCandidateDetails(@PathVariable("id") Long id, ModelMap model) { //
        Candidate candidate = service.findCandidateById(id);
        if (candidate== null) {
            return CANDIDATES_LIST;
        }
        model.addAttribute("candidate", candidate);
        CandidateDetails details = candidate.getDetails();
        if(details==null){
            details = new CandidateDetails ();
            details.setCandidate(candidate); //!!!!
            details.setId(id);
            service.saveCandidateDetails(details);
            candidate.setDetails(details);
            service.saveCandidate(candidate);
        }else
            details = service.findCandidateDetailsById(id);
        model.addAttribute("details", details);
        return CANDIDATES_DETAILS;
    }

    @PostMapping(value = "/Candidates/voting/{id}")
    public String votedForCandidateFromTable(@PathVariable("id") Long id, @ModelAttribute("user") User user,
            BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return CANDIDATES_LIST;
        }
        List<Elector> electors_list = service.findElectors();
        Candidate candidate = service.findCandidateById(id);
        for (Elector electorVote : electors_list) { 
            if (electorVote.getLogin().equals(user.getLogin())& electorVote.getPassword().equals(user.getPassword())) {
                if (electorVote.isVoted()==false){
                    electorVote.vote(candidate);
                    service.saveCandidate(candidate);
                    service.saveElector(electorVote);
                    return "redirect:/Candidates";
                }else{
                   List<Candidate> candidates = service.findCandidates();
                   model.addAttribute("candidates", candidates);
                   model.addAttribute("message", "You have already voted!"); 
                   //return "candidates/candidates_list";
                   //return "redirect:/Candidates";
                }
            }
        }
        return CANDIDATES_LIST;
    }
    
    @GetMapping(value = "/Candidates/results")
    public String showVotingResults(ModelMap model){
        List <Candidate> candidates = service.findCandidates();  
        Long max = 0L;
        Candidate firstWinner = null;
        Candidate secondWinner = null;       
        
        List <Long> voices = new ArrayList<>();
        for (Candidate candidate: candidates){
            if(candidate.getVoices()==null)
                candidate.setVoices(0L); 
            voices.add(candidate.getVoices());
        }
        
        int pos = -1;       
        for (int j = 0; j<candidates.size(); j++){
            if (pos != -1){
                break;
            }
            Long voice =  candidates.get(j).getVoices();
            for (int i=0; i<voices.size(); i++){
                if (!voice.equals(voices.get(i))){
                    pos=i;
                    break;
                }else
                   pos=-1; 
            }  
        }
        if (pos == -1) {
            model.addAttribute("message", "The elections did not take place because of the  equal number of votes for all candidates");
            model.addAttribute("candidates", candidates);
            return CANDIDATES_LIST;
        }

        for (Candidate candidate: candidates){
            if(candidate.getVoices()==null){
                candidate.setVoices(0L); 
            }
            if(candidate.getVoices()>max){
                max=candidate.getVoices();
                firstWinner = candidate;  
            } 
        }
        candidates.remove(firstWinner);
        for (Candidate candidate: candidates){
            if(candidate.getVoices().equals(max)){
                secondWinner = candidate;
            }
        }
        if (firstWinner!=null&secondWinner!=null){
            model.addAttribute("winners", "wile take place in the second stage of voting!");
            model.addAttribute("fistWinnerFistName", firstWinner.getFirstName());
            model.addAttribute("fistWinnerLastName", firstWinner.getLastName());
            model.addAttribute("secondWinnerFistName"," and "+ secondWinner.getFirstName());
            model.addAttribute("secondWinnerLastName", secondWinner.getLastName());
            return WINNERS;
        }
        model.addAttribute("winner", "The winner of the voting is");
        model.addAttribute("WinnerFistName", firstWinner.getFirstName());
        model.addAttribute("WinnerLastName", firstWinner.getLastName()+" !");
        return WINNERS;
    }
}
