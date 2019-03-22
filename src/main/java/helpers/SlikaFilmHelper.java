/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Film;
import beans.Slikafilm;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Aleksa
 */
public class SlikaFilmHelper {
    private Logger logger = Logger.getLogger("logger");
    private Session session = null;

    public SlikaFilmHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public List<Slikafilm> getSlikeZaFilm(int idFilm){
        List<Slikafilm> slike = new ArrayList<Slikafilm>();
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Slikafilm WHERE IdFilm=" + idFilm);
            slike = (List<Slikafilm>) q.list();
            for (Slikafilm s : slike) {
                System.out.println("Film naziv: " + s.getSlika());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slike;
    } 

    public void insertSlika(Slikafilm slikaFilm) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(slikaFilm);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
