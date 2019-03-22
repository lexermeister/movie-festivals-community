/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Film;
import beans.Korisnik;
import beans.Ocena;
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
public class OcenaHelper {
    Logger logger = Logger.getLogger("logger");
    Session session = null;

    public OcenaHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public boolean insertOcena(Ocena ocena) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(ocena);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Ocena> getOcenaZaKorisnikAndFilm(int idKorisnik, int idFilm) {
        List<Ocena> ocene = new ArrayList<Ocena>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Ocena WHERE IdKorisnik=" + idKorisnik + " AND IdFilm=" + idFilm);
            ocene = (List<Ocena>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ocene;
    }
    
    public List<Ocena> getOcenaZaFilm(int idFilm){
        List<Ocena> ocene = new ArrayList<Ocena>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Ocena WHERE IdFilm=" + idFilm);
            ocene = (List<Ocena>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ocene;
    }
}
