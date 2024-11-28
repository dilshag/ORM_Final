package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ProgrammeDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProgrammeBO<T> extends SuperBO {
   public boolean saveProgram(T entity) throws Exception;

  public List<T> getAllPrograms()throws Exception; ;

  public String generateNewProgrammeID() throws Exception;

    public boolean updateProgram(ProgrammeDTO dto) throws SQLException, ClassNotFoundException, IOException;

    public boolean deleteProgram(String programmeId) throws SQLException, ClassNotFoundException, IOException;
}
