package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ProgrammeDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StudentBO <T> extends SuperBO {
    public boolean saveStudent(T entity) throws Exception;
    public List<T> getAllStudent()throws Exception;
    public String generateNewStudentID() throws Exception;
    public boolean updateStudent(StudentDTO dto) throws SQLException, ClassNotFoundException, IOException;

    boolean deleteStudent(String studentID) throws SQLException, IOException, ClassNotFoundException;

    List<User> getUserIds();


    ;
}
