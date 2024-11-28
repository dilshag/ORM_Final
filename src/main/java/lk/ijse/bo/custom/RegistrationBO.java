package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Programme;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface RegistrationBO extends SuperBO {
    String generateNewRegistrationID() throws SQLException, IOException;

    List<Registration> getAllRegistrationDetails() throws IOException;

    List<Student> getAllStudent() throws IOException;

    List<Programme> getAllCourse() throws IOException;

    Programme serachbyCIDs(String cid) throws IOException;

    Student serachbyIDS(String sid) throws IOException;

    boolean saveRegistration(Registration registration) throws SQLException, IOException, ClassNotFoundException;
}
