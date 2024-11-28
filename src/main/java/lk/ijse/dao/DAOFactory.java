package lk.ijse.dao;


import lk.ijse.dao.custom.impl.ProgrammDAOImpl;
import lk.ijse.dao.custom.impl.RegistrationDAOImpl;
import lk.ijse.dao.custom.impl.StudentDAOImpl;
import lk.ijse.dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        STUDENT, PROGRAMME, USER ,REGISTRATION
    }

    public SuperDAO getDAO(DAOTypes daoTypes) {

        switch (daoTypes) {

            case PROGRAMME:
                return new ProgrammDAOImpl();

            case STUDENT:
                return new StudentDAOImpl();
            case USER:
                return new UserDAOImpl();
            case REGISTRATION:
                return new RegistrationDAOImpl();
            default:
                return null;


        }
    }
}
