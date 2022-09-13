package com.step.springmvcapp.dao;

import com.step.springmvcapp.entity.Candidate;
import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // под управление Spring'а, является производными от @Component
public class CandidateDAOImpl implements CandidateDAO{ 

    private SessionFactory sessionFactory;

    public CandidateDAOImpl() {}

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}
    
    public SessionFactory getSessionFactory() {return sessionFactory;}
    
    @Override
    public Candidate findById(Long id) {
        Candidate obj;
        Session session = this.sessionFactory.openSession();
        //Query query = session.createQuery("from Candidate c JOIN FETCH c.electors WHERE c.id="+id);
        //Query query = session.createQuery("from Candidate c WHERE c.id="+id);
        //obj = (Candidate)query.uniqueResult();
        obj = (Candidate)session.get(Candidate.class, id);
        return obj; 
    }
    @Override
    public List<Candidate> findAll() {
        Session session = this.sessionFactory.openSession();  
        return session.createQuery("from Candidate").list();
    }
    
    @Override
    public void save(Candidate obj) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            // session.persist(obj);
            //session.saveOrUpdate(obj);
            if (obj.getId()==null){
                session.persist(obj);
            } else
                //session.update(obj);
                //session.save(obj);
                session.merge(obj); // почему только этот отрабатет???
            tx.commit();
        }catch (Exception ex){
            System.out.println(ex);
            tx.rollback();
        }finally{
            session.close();
        }   
    }
    
    @Override
    public void saveAll(Collection<Candidate> obj) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            for (Candidate c: obj){
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
            Query query = session.createQuery("from Candidate c WHERE c.id="+id);
            Candidate obj = (Candidate)query.uniqueResult();
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
