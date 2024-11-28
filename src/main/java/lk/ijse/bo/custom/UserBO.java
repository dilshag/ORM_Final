package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.UserDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserBO <T> extends SuperBO {
  public boolean saveUser(T entity) throws SQLException, IOException, ClassNotFoundException;

  public String generateNewUserID() throws SQLException, IOException;

    boolean deleteUser(String userID) throws SQLException, IOException, ClassNotFoundException;

  public List<T> getAllusers() throws IOException;

  public boolean updateUser(T entity) throws IOException, SQLException, ClassNotFoundException;
}
