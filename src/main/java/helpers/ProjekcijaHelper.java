/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Film;
import beans.Programfestivala;
import beans.Projekcija;
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
public class ProjekcijaHelper {

    private Logger logger = Logger.getLogger("logger");
    private Session session = null;

    public ProjekcijaHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public Projekcija getProjekcijaByFestivalAndFilm(int idFestival, int idFilm) {
        List<Projekcija> projekcije = new ArrayList<Projekcija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Programfestivala WHERE IdFestival=" + idFestival + " AND IdFilm=" + idFilm);
            List<Programfestivala> prog = (List<Programfestivala>) q.list();
            int idProjekcija = prog.get(0).getIdProjekcija();
            q = session.createQuery("FROM Projekcija WHERE IdProjekcija=" + idProjekcija);
            projekcije = (List<Projekcija>) q.list();
            for (Projekcija f : projekcije) {
                System.out.println("Projekcija id: " + f.getIdProjekcija());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projekcije.get(0);
    }

    public List<Projekcija> getProjekcijaByFilm(int idFilm) {
        List<Projekcija> projekcije = new ArrayList<Projekcija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Programfestivala WHERE IdFilm=" + idFilm);
            List<Programfestivala> prog = (List<Programfestivala>) q.list();
            for (Programfestivala pr : prog) {
                int idProjekcija = pr.getIdProjekcija();
                q = session.createQuery("FROM Projekcija WHERE IdProjekcija=" + idProjekcija);
                projekcije.add((Projekcija) q.list().get(0));
            }

//            int idProjekcija = prog.get(0).getIdProjekcija();
//            q = session.createQuery("FROM Projekcija WHERE IdProjekcija=" + idProjekcija);
//            projekcije = (List<Projekcija>) q.list();
            for (Projekcija f : projekcije) {
                System.out.println("Projekcija id: " + f.getIdProjekcija());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projekcije;
    }

    public List<Projekcija> getProjekcijaByFestival(int idFestival) {
        List<Projekcija> projekcije = new ArrayList<Projekcija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Programfestivala WHERE IdFestival=" + idFestival);
            List<Programfestivala> prog = (List<Programfestivala>) q.list();
            for (Programfestivala p : prog) {
                q = session.createQuery("FROM Projekcija WHERE IdProjekcija=" + p.getIdProjekcija());
                List<Projekcija> pr = (List<Projekcija>) q.list();
                if(pr.size() > 0){
                    projekcije.add(pr.get(0));
                }
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projekcije;
    }

    public List<Projekcija> getProjekcijeZaFestivalIFilm(int idFestival, int idFilm) {
        List<Projekcija> projekcije = new ArrayList<Projekcija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Programfestivala WHERE IdFestival=" + idFestival + " AND IdFilm=" + idFilm);
            List<Programfestivala> prog = (List<Programfestivala>) q.list();
            for (Programfestivala pr : prog) {
                q = session.createQuery("FROM Projekcija WHERE IdProjekcija=" + pr.getIdProjekcija());
                Projekcija proj = (Projekcija) q.list().get(0);
                projekcije.add(proj);
            }
            for (Projekcija f : projekcije) {
                System.out.println("Projekcija id: " + f.getIdProjekcija());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projekcije;
    }

    public Projekcija getProjekcijaById(int idProjekcija) {
        List<Projekcija> projekcije = new ArrayList<Projekcija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Projekcija WHERE IdProjekcija=" + idProjekcija);
            projekcije = (List<Projekcija>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projekcije.get(0);
    }

    public List<Projekcija> getProjekcije() {
        List<Projekcija> projekcije = new ArrayList<Projekcija>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Projekcija");
            projekcije = (List<Projekcija>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projekcije;
    }

    public boolean insertProjekcija(Projekcija projekcija) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(projekcija);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateProjekcija(Projekcija p) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(p);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProjekcija(Projekcija p) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(p);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
