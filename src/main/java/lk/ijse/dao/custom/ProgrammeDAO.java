package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Programme;

import java.io.IOException;

public interface ProgrammeDAO extends CrudDAO<Programme> {

    Programme SearchCID(String cid) throws IOException;
}
