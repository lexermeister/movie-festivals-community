/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Ulaznica;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Aleksa
 */
public class UlaznicaHelper {
    private Logger logger = Logger.getLogger("logger");
    private Session session = null;

    public UlaznicaHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public boolean insertUlaznica(Ulaznica ulaznica) {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(ulaznica);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
