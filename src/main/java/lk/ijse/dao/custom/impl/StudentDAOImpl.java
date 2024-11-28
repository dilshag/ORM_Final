package lk.ijse.dao.custom.impl;


import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Programme;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public boolean save(Student student) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(student);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Student> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM Student");
        nativeQuery.addEntity(Student.class);
        List<Student> students= nativeQuery.list();


        transaction.commit();
        session.close();

        return students;
    }

    @Override
    public String generateNewID() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT studentID FROM Student ORDER BY studentID DESC");
        query.setMaxResults(1);
        List results = query.list();

        transaction.commit();
        session.close();

        return (results.size() == 0) ? null : (String) results.get(0);

    }

    @Override
    public boolean update(Student entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String studentID) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("delete from Student where  studentID = ?1");
        query.setParameter(1,studentID);
        query.executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<User> getid() {
        List<User> users = new ArrayList<>();
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            // Use HQL to get all User objects
            String hql = "FROM User"; // This will retrieve all User entities
            Query<User> query = session.createQuery(hql, User.class);
            users = query.list(); // This will now contain User objects
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger instead of printStackTrace
        }
        return users;
    }

    @Override
    public Student searchById(String sid) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        Student student = null;

        try {
            transaction = session.beginTransaction();
            // find student
            String hql = "FROM Student WHERE studentID = :id";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("id", sid);
            student = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return student;
    }
}
