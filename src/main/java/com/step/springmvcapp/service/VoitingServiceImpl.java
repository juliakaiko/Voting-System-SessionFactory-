package com.step.springmvcapp.service;

import com.step.springmvcapp.dao.AdminDAO;
import com.step.springmvcapp.dao.CandidateDAO;
import com.step.springmvcapp.dao.CandidateDetailsDAO;
import com.step.springmvcapp.dao.ElectorDAO;
import com.step.springmvcapp.entity.Admin;
import com.step.springmvcapp.entity.Candidate;
import com.step.springmvcapp.entity.CandidateDetails;
import com.step.springmvcapp.entity.Elector;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoitingServiceImpl implements VoitingService { 
    
    @Autowired
    private CandidateDAO candidateDao;
    @Autowired
    private ElectorDAO electorDao;
    @Autowired
    private CandidateDetailsDAO detailsDao;
    @Autowired
    private AdminDAO adminDao;

    public VoitingServiceImpl() {}     

    @Override
    //@Transactional(readOnly = true) // в 10 раз быстрее будет работать, только для чтения
    public List<Candidate> findCandidates() {
        return candidateDao.findAll();
    }

    @Override
   // @Transactional(readOnly = true) // в 10 раз быстрее будет работать, только для чтения
    public Candidate findCandidateById(Long id) {
        return candidateDao.findById(id);
    }

    @Override
    //@Transactional // добавляет транзакцию!
    public void saveCandidate(Candidate candidate) {
        candidateDao.save(candidate);
    }

    @Override
    //@Transactional
    public void addAllCandidates(Collection<Candidate> candidates) {
        for (Candidate c: candidates){
            candidateDao.save(c);
        }
    }

    @Override
    //@Transactional
    public void deleteCandidateById(Long id) {
       candidateDao.delete(id);
    }

    @Override
    //@Transactional(readOnly = true) // в 10 раз быстрее будет работать, только для чтения
    public List <Elector> findElectors() {
       return electorDao.findAll();
    }

    @Override
    //@Transactional(readOnly = true) // в 10 раз быстрее будет работать, только для чтения
    public Elector findElectorById(Long id) {
        return electorDao.findById(id);
    }

    @Override
    //@Transactional
    public void saveElector(Elector elector) {
        electorDao.save(elector);
    }

    @Override
    //@Transactional
    public void addAllElectors(Collection<Elector> electors) {
        for (Elector e: electors){
            electorDao.save(e);
        }
    }

    @Override
   // @Transactional
    public void deleteElectorById(Long id) {
        Elector elector = findElectorById(id);
        if (elector.isVoted()){
            Candidate candidate = candidateDao.findById(elector.getCandidate().getId());
            candidate.getElectors().remove(elector);
            Long voices = candidate.getVoices()-1L;
            candidate.setVoices(voices);
            candidateDao.save(candidate);
        }
        electorDao.delete(id);
    }
    
    //@Transactional
    @Override
    public void saveCandidateDetails(CandidateDetails details) {
        detailsDao.save(details);
    }
    
    //@Transactional (readOnly = true)
    @Override
    public CandidateDetails findCandidateDetailsById(Long id){
         return detailsDao.findById(id);
    }
    
    //@Transactional (readOnly = true)
    @Override
    public Admin getAdmin(){
        return adminDao.getAdmin();
    }

    
}
