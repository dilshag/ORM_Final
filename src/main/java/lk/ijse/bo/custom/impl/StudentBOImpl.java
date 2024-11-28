package lk.ijse.bo.custom.impl;

import lk.ijse.bo.SuperBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProgrammeDAO;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dto.ProgrammeDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.Programme;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO<StudentDTO> {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STUDENT);
    @Override
    public boolean saveStudent(StudentDTO entity) throws Exception {
        // return studentDAO.save(new Student(dto.getStudentID(), dto.getStudentName(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(),dto.getUserId()));
        Student student = new Student();
        student.setStudentID(entity.getStudentID());
        student.setStudentName(entity.getStudentName());
        student.setAddress(entity.getAddress());
        student.setPhoneNumber(entity.getPhoneNumber());
        student.setEmail(entity.getEmail());
        student.setUser(entity.getUserId());


        return studentDAO.save(student);

    }

    @Override
    public List<StudentDTO> getAllStudent() throws Exception {
        List<Student> students = studentDAO.getAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student: students){
            StudentDTO studentDTO = new StudentDTO();

            studentDTO.setStudentID(student.getStudentID());
            studentDTO.setStudentName(student.getStudentName());
            studentDTO.setAddress(student.getAddress());
            studentDTO.setPhoneNumber(student.getPhoneNumber());
            studentDTO.setEmail(student.getEmail());
            studentDTO.setUserId(student.getUser());

           studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

    @Override
    public String generateNewStudentID() throws Exception {
        return studentDAO.generateNewID();
    }

    @Override
    public boolean updateStudent(StudentDTO entity) throws SQLException, ClassNotFoundException, IOException {
       // return studentDAO.update(new Student(dto.getStudentID(), dto.getStudentName(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(),dto.getUserId()));
        Student student = new Student();
        student.setStudentID(entity.getStudentID());
        student.setStudentName(entity.getStudentName());
        student.setAddress(entity.getAddress());
        student.setPhoneNumber(entity.getPhoneNumber());
        student.setEmail(entity.getEmail());
        student.setUser(entity.getUserId());


        return studentDAO.update(student);
    }

    @Override
    public boolean deleteStudent(String studentID) throws SQLException, IOException, ClassNotFoundException {
        return studentDAO.delete(studentID);
    }

    @Override
    public List<User> getUserIds() {
        return studentDAO.getid();
    }
}
