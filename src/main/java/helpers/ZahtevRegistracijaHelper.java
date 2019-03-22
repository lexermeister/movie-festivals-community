/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import beans.Zahtevregistracija;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Aleksa
 */
public class ZahtevRegistracijaHelper {
    Logger logger = Logger.getLogger("logger");
    Session session = null;

    public ZahtevRegistracijaHelper() {
        this.session = HibernateUtil.HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public List getZahtevi(){
        List<Zahtevregistracija> zahtevi = new ArrayList<Zahtevregistracija>();
        try{
            this.session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Zahtevregistracija");
            zahtevi = (List<Zahtevregistracija>)q.list();
            tx.commit();
            session.close();
            return zahtevi;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public Zahtevregistracija getZahtevById(int id){
        Zahtevregistracija zahtev = null;
        try{
            this.session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Zahtevregistracija");
            zahtev = (Zahtevregistracija)q.uniqueResult();
            tx.commit();
            session.close();
            return zahtev;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean insertNoviZahtev(Zahtevregistracija zahtevregistracija){
        try{
            this.session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Zahtevregistracija zr = new Zahtevregistracija();
            zr.setUsername(zahtevregistracija.getUsername());
            zr.setLozinka(zahtevregistracija.getLozinka());
            zr.setIme(zahtevregistracija.getIme());
            zr.setPrezime(zahtevregistracija.getPrezime());
            zr.setKontakt(zahtevregistracija.getKontakt());
            zr.setEmail(zahtevregistracija.getEmail());
            
            session.save(zr);
            tx.commit();
            session.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteZahtev(Zahtevregistracija zahtev){
        try{
            this.session = HibernateUtil.HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();            
            session.delete(zahtev);
            tx.commit();
            session.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
}
