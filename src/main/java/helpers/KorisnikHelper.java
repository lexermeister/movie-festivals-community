/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Korisnik;
import beans.Rezervacija;
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
public class KorisnikHelper {

    Logger logger = Logger.getLogger("logger");
    Session session = null;

    public KorisnikHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public List getKorisnici() {
        List<Korisnik> korisnici = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Korisnik");
            korisnici = (List<Korisnik>) q.list();
            for (Korisnik k : korisnici) {
                System.out.println("Username: " + k.getUsername());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return korisnici;
    }

    public Korisnik getKorisnikByUsername(String username) {
        Korisnik korisnik = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Korisnik where Username='" + username + "'");
            korisnik = (Korisnik) q.uniqueResult();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return korisnik;
    }

    public Korisnik getKorisnikByUsernameAndPassword(String username, String password) {
        Korisnik korisnik = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Korisnik WHERE Username='" + username + "' AND Lozinka='" + password + "'");
            logger.info("query: " + q.getQueryString());
            korisnik = (Korisnik) q.uniqueResult();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return korisnik;
    }

    public boolean updateKorisnikLozinka(String username, String novaLozinka) {
        try {
            Korisnik korisnik = getKorisnikByUsername(username);
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            korisnik.setLozinka(novaLozinka);
            session.update(korisnik);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean insertKorisnik(Korisnik korisnik) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(korisnik);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateKorisnik(Korisnik korisnik){
        try{
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(korisnik);
            tx.commit();
            session.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Korisnik getKorisnikById(int idKorisnik) {
        List<Korisnik> korisnici = new ArrayList<Korisnik>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Korisnik WHERE IdKorisnik=" + idKorisnik);
            korisnici = (List<Korisnik>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return korisnici.get(0);
    }

}
