package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.io.IOException;
import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {

    List<User> getid();

    Student searchById(String sid) throws IOException;
}
