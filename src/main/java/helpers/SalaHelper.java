/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Festival;
import beans.Sala;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Aleksa
 */
public class SalaHelper {
    private Logger logger = Logger.getLogger("logger");
    private Session session = null;

    public SalaHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public List<Sala> getSaleByMesto(int idMesto){
        List<Sala> sale = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Sala WHERE IdMesto=" + idMesto);
            sale = (List<Sala>) q.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sale;
    }

    public void insertSala(Sala sala) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(sala);
            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
