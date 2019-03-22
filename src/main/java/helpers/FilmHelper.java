/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Festival;
import beans.Film;
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
public class FilmHelper {

    private Logger logger = Logger.getLogger("logger");
    private Session session = null;

    public FilmHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public Film getFilmByOriginalniNaziv(String originalniNaziv) {
        List<Film> filmovi = new ArrayList<Film>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Film WHERE OriginalniNaziv='" + originalniNaziv + "'");
            filmovi = (List<Film>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filmovi.get(0);
    }

    public Film getFilmById(int idFilm) {
        List<Film> filmovi = new ArrayList<Film>();
        try {
//            logger.info("usao u getfilmbyid");
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Film WHERE IdFilm=" + idFilm);
            filmovi = (List<Film>) q.list();
            for (Film f : filmovi) {
                System.out.println("Film naziv: " + f.getNaziv());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            logger.info("usao u catch");
            e.printStackTrace();
        }

        return filmovi.get(0);
    }

    public Film getFilmByNaziv(String naziv) {
        List<Film> filmovi = new ArrayList<Film>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Film WHERE Naziv='" + naziv + "'");
            filmovi = (List<Film>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filmovi.get(0);
    }

    public boolean updateFilm(Film film) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(film);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Film> getFilmovi() {
        List<Film> filmovi = new ArrayList<Film>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Film");
            filmovi = (List<Film>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filmovi;
    }

    public boolean insertFilm(Film noviFilm) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(noviFilm);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
