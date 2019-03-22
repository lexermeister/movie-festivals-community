/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Festival;
import beans.Rezervacija;
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
public class RezervacijaHelper {

    private Logger logger = Logger.getLogger("logger");
    private Session session = null;

    public RezervacijaHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public boolean insertRezervacija(Rezervacija rezervacija) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(rezervacija);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Rezervacija> getRezervacijaByKorisnik(int idKorisnik) {
        List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Rezervacija WHERE IdKorisnik=" + idKorisnik);
            rezervacije = (List<Rezervacija>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rezervacije;

    }

    public List<Rezervacija> getRezervacijaByKorisnikAndStatus(int idKorisnik, String status) {
        List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Rezervacija WHERE IdKorisnik=" + idKorisnik + " AND StatusRezervacije='" + status + "'");
            rezervacije = (List<Rezervacija>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rezervacije;
    }

    public boolean updateRezervacija(Rezervacija rezervacija) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(rezervacija);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRezervacija(Rezervacija rezervacija) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(rezervacija);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Rezervacija getRezervacijaById(int idRezervacija) {
        List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Rezervacija WHERE IdRezervacija=" + idRezervacija);
            rezervacije = (List<Rezervacija>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rezervacije.get(0);
    }

    public List<Rezervacija> getRezervacije() {
        List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Rezervacija");
            rezervacije = (List<Rezervacija>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rezervacije;
    }

    public List<Rezervacija> getRezervacijaByStatus(String status) {
        List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Rezervacija WHERE StatusRezervacije='" + status + "'");
            rezervacije = (List<Rezervacija>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rezervacije;
    }

    public List<Rezervacija> getRezervacijaByProjekcijaAndStatus(Integer idProjekcija, String status) {
        List<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Rezervacija WHERE IdProjekcija=" + idProjekcija + " AND StatusRezervacije='" + status + "'");
            rezervacije = (List<Rezervacija>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rezervacije;
    }

}
