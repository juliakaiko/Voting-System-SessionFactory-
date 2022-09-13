package com.step.springmvcapp.dao;

import com.step.springmvcapp.entity.CandidateDetails;
import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // под управление Spring'а, является производными от @Component
public class CandidateDetailsDAOImpl implements  CandidateDetailsDAO{
    
    private SessionFactory sessionFactory;   
    
    public CandidateDetailsDAOImpl() {}
    
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}
    
    public SessionFactory getSessionFactory() {return sessionFactory;}
    
    @Override
    public CandidateDetails findById(Long id) {
        CandidateDetails obj;
        Session session = this.sessionFactory.openSession();
        //Query query = session.createQuery("from Candidate c JOIN FETCH c.details WHERE c.id="+id);
        //Query query = session.createQuery("from CandidateDetails c WHERE c.id="+id);
        //obj = (CandidateDetails)query.uniqueResult();
        obj = (CandidateDetails)session.get(CandidateDetails.class, id);
        return obj; 
    }

    @Override
    public List<CandidateDetails> findAll() {
        Session session = this.sessionFactory.openSession();  
        return session.createQuery("from CandidateDetails").list();
    }

    @Override
    public void save(CandidateDetails obj) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
             //session.persist(obj);
            //session.saveOrUpdate(obj);
            if (obj.getId()==null){
                session.persist(obj);
            } else
                session.update(obj);
                //session.merge(obj); // почему только этот отрабатет???
            tx.commit();
        }catch (Exception ex){
            System.out.println(ex);
            tx.rollback();
        }finally{
            session.close();
        }   
    }

    @Override
    public void saveAll(Collection<CandidateDetails> obj) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            for (CandidateDetails c: obj){
                session.persist(c);
            }
            tx.commit();
        }catch (Exception ex){
            System.out.println(ex);
            tx.rollback();
        }finally{
            session.close();
        }           
    }

    @Override
    public void delete(Long id) {
       Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            Query query = session.createQuery("from CandidateDetails c WHERE c.id="+id);
            CandidateDetails obj = (CandidateDetails)query.uniqueResult();
            session.delete(obj);
            tx.commit();
        }catch (Exception ex){
            System.out.println(ex);
            tx.rollback();
        }finally{
            session.close();
        }   
    }
}
