/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Komentar;
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
public class KomentarHelper {
    Logger logger = Logger.getLogger("logger");
    Session session = null;

    public KomentarHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public boolean insertKomentar(Komentar komentar) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(komentar);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Komentar> getKomentarByFilm(int idFilm){
        List<Komentar> komentari = new ArrayList<Komentar>();
        try{
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Komentar WHERE IdFilm=" + idFilm);
            komentari = (List<Komentar>)q.list();
            tx.commit();
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return komentari;
    }
    
}
