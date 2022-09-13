package com.step.springmvcapp.service;

import com.step.springmvcapp.entity.Admin;
import com.step.springmvcapp.entity.Candidate;
import com.step.springmvcapp.entity.CandidateDetails;
import com.step.springmvcapp.entity.Elector;
import java.util.Collection;
import java.util.List;

public interface VoitingService {
    List <Candidate> findCandidates();
    Candidate findCandidateById(Long id);    
    void saveCandidate (Candidate candidate);    
    void addAllCandidates (Collection <Candidate> candidates);    
    void deleteCandidateById (Long id);
    
    List <Elector> findElectors();
    Elector findElectorById(Long id);
    void saveElector(Elector elector);
    void addAllElectors (Collection <Elector> electors);
    void deleteElectorById (Long id);
        
    void saveCandidateDetails(CandidateDetails details);
    CandidateDetails findCandidateDetailsById(Long id);

    Admin getAdmin();

}
