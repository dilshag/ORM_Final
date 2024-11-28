package lk.ijse.dao.custom.impl;

import lk.ijse.bo.BOFactory;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.SuperDAO;
import lk.ijse.dao.custom.ProgrammeDAO;
import lk.ijse.entity.Programme;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgrammDAOImpl implements ProgrammeDAO {


    public boolean save(Programme programme) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(programme);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Programme> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM Programme");
        nativeQuery.addEntity(Programme.class);
        List<Programme> programmes= nativeQuery.list();


        transaction.commit();
        session.close();

        return programmes;
    }

    @Override
    public String generateNewID() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT programmeId FROM Programme ORDER BY programmeId DESC");
        query.setMaxResults(1);
        List results = query.list();

        transaction.commit();
        session.close();

        return (results.size() == 0) ? null : (String) results.get(0);

    }

    @Override
    public boolean update(Programme programme) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(programme);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String programmeId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("delete from Programme where programmeId = ?1");
        query.setParameter(1, programmeId);
        query.executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Programme SearchCID(String cid) throws IOException {
        List<Programme> programmeList = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            programmeList = session.createQuery("FROM Programme WHERE programmeId = :cid", Programme.class)
                    .setParameter("cid", cid)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return (Programme) programmeList;
    }
}
