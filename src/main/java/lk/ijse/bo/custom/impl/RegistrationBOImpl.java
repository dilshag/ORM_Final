package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProgrammeDAO;
import lk.ijse.dao.custom.RegistrationDAO;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Programme;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STUDENT);
    ProgrammeDAO programmeDAO = (ProgrammeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PROGRAMME);
    RegistrationDAO registrationDAO = (RegistrationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.REGISTRATION);
    @Override
    public String generateNewRegistrationID() throws SQLException, IOException {
        return registrationDAO.generateNewID();
    }

    @Override
    public List<Registration> getAllRegistrationDetails() throws IOException {
        List<Registration> alldetails = registrationDAO.getAll();

        return alldetails;
    }

    @Override
    public List<Student> getAllStudent() throws IOException {
        List<Student> allStudent = studentDAO.getAll();

        return allStudent;
    }

    @Override
    public List<Programme> getAllCourse() throws IOException {
        List<Programme> allPrograms = programmeDAO.getAll();
        return allPrograms;
    }

    @Override
    public Programme serachbyCIDs(String cid) throws IOException {
        return programmeDAO.SearchCID(cid);
    }

    @Override
    public Student serachbyIDS(String sid) throws IOException {
        return studentDAO.searchById(sid);
    }

    @Override
    public boolean saveRegistration(Registration entity) throws SQLException, IOException, ClassNotFoundException {
        return registrationDAO.save(new Registration(entity.getRegiId(),entity.getEnrollmentDate(),entity.getPayment(),entity.getDueAmount(),entity.getStudentName(),entity.getProgramName(),entity.getDuration(),entity.getStudent(),entity.getProgramme()));
    }
}
