/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Korisnik;
import beans.Mestoodrzavanja;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class MestoOdrzavanjaHelper {
    Logger logger = Logger.getLogger("logger");
    Session session = null;

    public MestoOdrzavanjaHelper() {
    }
    
    public List<Mestoodrzavanja> getMestaByFestivalId(int idF){
        List<Mestoodrzavanja> mesta = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Mestoodrzavanja WHERE IdFestival=" + idF);
            mesta = (List<Mestoodrzavanja>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesta; 
    }

    public List<Mestoodrzavanja> getMesta() {
        List<Mestoodrzavanja> mesta = new ArrayList<Mestoodrzavanja>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Mestoodrzavanja");
            mesta = (List<Mestoodrzavanja>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesta; 
    }

    public List<Mestoodrzavanja> getMestoByNaziv(String mesto) {
        List<Mestoodrzavanja> mesta = new ArrayList<Mestoodrzavanja>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Mestoodrzavanja WHERE NazivMesta='" + mesto + "'");
            mesta = (List<Mestoodrzavanja>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesta; 
    }

    public void insertMesto(Mestoodrzavanja mesto) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(mesto);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Mestoodrzavanja getMestoByLokacija(String mesto, String lokacija) {
        List<Mestoodrzavanja> mesta = new ArrayList<Mestoodrzavanja>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Mestoodrzavanja WHERE NazivMesta='" + mesto + "' AND Lokacija='" + lokacija + "'");
            mesta = (List<Mestoodrzavanja>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(mesta.size() == 0){
            return null;
        }
        return mesta.get(0); 
    }

    public List<Mestoodrzavanja> getMestaDistinct() {
        List<Mestoodrzavanja> mesta = new ArrayList<Mestoodrzavanja>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Mestoodrzavanja");
            q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            mesta = (List<Mestoodrzavanja>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesta; 
    }

 

    
}
