/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import HibernateUtil.HibernateUtil;
import beans.Programfestivala;
import beans.Projekcija;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Aleksa
 */
public class ProgramHelper {
    private Logger logger = Logger.getLogger("logger");
    private Session session = null;

    public ProgramHelper() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public boolean insertProgram(Programfestivala program){
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(program);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteProjekciju(int idProjekcije){
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM Programfestivala WHERE IdProjekcija=" + idProjekcije);
            Programfestivala prog = (Programfestivala)q.list().get(0);
            session.delete(prog);
            tx.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
