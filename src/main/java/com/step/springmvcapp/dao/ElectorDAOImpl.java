package com.step.springmvcapp.dao;

import com.step.springmvcapp.entity.Elector;
import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // под управление Spring'а, является производными от @Component
public class ElectorDAOImpl implements ElectorDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}
    
    public SessionFactory getSessionFactory() {return sessionFactory;}
    
    @Override
    public Elector findById(Long id) {
        Session session = this.sessionFactory.openSession(); 
        Elector obj;
        obj = (Elector)session.get(Elector.class, id);
//        Session session = this.sessionFactory.openSession();
//        Query query = session.createQuery("from Elector e WHERE e.id="+id);
//        obj = (Elector)query.uniqueResult();
        return obj; 
    }

    @Override
    public List<Elector> findAll() {
        Session session = this.sessionFactory.openSession();  
        return session.createQuery("from Elector").list();
    }

    @Override
    public void save(Elector obj) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            //session.persist(obj);
            //session.saveOrUpdate(obj);
            if (obj.getId()==null){
                session.persist(obj);
            } else
                session.update(obj);
             tx.commit();
        }catch (Exception ex){
            System.out.println(ex);
            //ex.printStackTrace();
            tx.rollback();
        }finally{
            session.close();
        }   
    }

    @Override
    public void saveAll(Collection<Elector> obj) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            for (Elector c: obj){
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
            Query query = session.createQuery("from Elector e WHERE e.id="+id);
            Elector obj = (Elector)query.uniqueResult();
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
