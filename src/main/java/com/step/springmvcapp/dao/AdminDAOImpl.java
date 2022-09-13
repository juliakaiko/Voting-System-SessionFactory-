package com.step.springmvcapp.dao;

import com.step.springmvcapp.entity.Admin;
import com.step.springmvcapp.entity.Candidate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository 
public class AdminDAOImpl implements AdminDAO {

    private SessionFactory sessionFactory;

    public AdminDAOImpl() {}
    
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}
    
    public SessionFactory getSessionFactory() {return sessionFactory;}
    
    @Override
    public Admin getAdmin() {
        Admin obj;
        Session session = this.sessionFactory.openSession();
        //Query query = session.createQuery("from Candidate c JOIN FETCH c.electors WHERE c.id="+id);
        Query query = session.createQuery("from Admin c WHERE c.id="+1L);
        obj = (Admin)query.uniqueResult();
        //obj = (Admin)session.get(Admin.class, 1L);
        return obj; 
    }

    @Override
    public void save(Admin obj) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            // session.persist(obj);
            //session.saveOrUpdate(obj);
            if (obj.getId()==null){
                session.persist(obj);
            } else
                //session.update(obj);
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
