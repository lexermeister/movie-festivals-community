/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Festival;
import beans.Programfestivala;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Aleksa
 */
public class FestivalHelper {
    private Logger logger = Logger.getLogger("logger");
    private Session session = null;

    public FestivalHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public List<Festival> getFestivali(){
        List<Festival> festivali = new ArrayList<Festival>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Festival");
            festivali = (List<Festival>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return festivali;
    }
    
    public List<Festival> getFestivalByNaziv(String naziv){
        List<Festival> festivali = new ArrayList<Festival>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Festival WHERE Naziv='" + naziv + "'");
            festivali = (List<Festival>) q.list();
            for (Festival f : festivali) {
                System.out.println("Festival naziv: " + f.getNaziv());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return festivali;
    }
    
    public List<Festival> getFestivalByNazivAndDates(String naziv, Date d1, Date d2){
        List<Festival> festivali = new ArrayList<Festival>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Festival WHERE Naziv='" + naziv + "' AND DatumPocetka BETWEEN :date1 AND :date2")
                             .setParameter("date1", d1)
                             .setParameter("date2", d2);
            festivali = (List<Festival>) q.list();
            for (Festival f : festivali) {
                System.out.println("Festival naziv: " + f.getNaziv());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return festivali;
    }

    public List<Festival> getFestivaliByDates(Date d1, Date d2){
        List<Festival> festivali = new ArrayList<Festival>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Festival WHERE DatumPocetka BETWEEN :date1 AND :date2")
                             .setParameter("date1", d1)
                             .setParameter("date2", d2);
            festivali = (List<Festival>) q.list();
            for (Festival f : festivali) {
                System.out.println("Festival naziv: " + f.getNaziv());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return festivali;
    }

    public List<Festival> getPetAktuelnih(){
        List<Festival> festivali = new ArrayList<Festival>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Festival WHERE StatusFestival='u toku' OR StatusFestival='nije poceo' "
                    + "ORDER BY DatumPocetka asc").setMaxResults(5);
            festivali = (List<Festival>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return festivali;
    }
    
    public Festival getFestivalByProjekcija(int idProjekcija){
        List<Festival> festivali = new ArrayList<Festival>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Programfestivala WHERE IdProjekcija=" + idProjekcija);
            List<Programfestivala> prog = (List<Programfestivala>)q.list();
            int idFestival = prog.get(0).getIdFestival();
            q = session.createQuery("FROM Festival WHERE IdFestival=" + idFestival);
            festivali = (List<Festival>)q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return festivali.get(0);
    }
    
    public Festival getFestivalById(int idFestival){
        List<Festival> festivali = new ArrayList<Festival>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Festival WHERE IdFestival=" + idFestival);
            festivali = (List<Festival>) q.list();
            for (Festival f : festivali) {
                System.out.println("Festival naziv: " + f.getNaziv());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return festivali.get(0);
    }
    
    public List<Festival> getAktuelniFestivaliByFilm(int idFilm){
        List<Festival> festivali = new ArrayList<Festival>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Programfestivala WHERE IdFilm=" + idFilm);
            List<Programfestivala> prog = (List<Programfestivala>)q.list();
            for(Programfestivala pr : prog){
                logger.info("idfest:"  + pr.getIdFestival());
                q = session.createQuery("FROM Festival WHERE IdFestival=" + pr.getIdFestival());
                Festival fest = (Festival)q.list().get(0);
                logger.info("festfilm naziv:" + fest.getNaziv());
                if(!fest.getStatusFestival().equals("zavrsen")){
                    if(!festivali.contains(fest)){
                        festivali.add(fest);
                    }
                }
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return festivali;
    }

    public boolean insertFestival(Festival festival) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(festival);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
