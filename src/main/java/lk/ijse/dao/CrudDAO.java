package lk.ijse.dao;

import lk.ijse.entity.Programme;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CrudDAO <T> extends SuperDAO {
    public boolean save(T entity) throws SQLException, ClassNotFoundException, IOException;
    public List<T> getAll() throws IOException;
    public String generateNewID() throws SQLException, IOException;
    public boolean update(T entity) throws SQLException, ClassNotFoundException, IOException;
    public boolean delete(String Id) throws SQLException, ClassNotFoundException, IOException;
}
