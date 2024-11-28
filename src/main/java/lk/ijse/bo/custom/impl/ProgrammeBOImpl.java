package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ProgrammeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProgrammeDAO;
import lk.ijse.dto.ProgrammeDTO;
import lk.ijse.entity.Programme;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgrammeBOImpl implements ProgrammeBO <ProgrammeDTO>{

    ProgrammeDAO programmeDAO = (ProgrammeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PROGRAMME);

    @Override
    public boolean saveProgram(ProgrammeDTO dto) throws Exception {
        return programmeDAO.save(new Programme(dto.getProgrammeId(), dto.getProgrammeName(), dto.getDuration(), dto.getFee()));

    }

    @Override
    public List<ProgrammeDTO> getAllPrograms() throws Exception {
        List<Programme> programmes = programmeDAO.getAll();
        List<ProgrammeDTO> programmeDTOS = new ArrayList<>();

        for (Programme programme: programmes){
            ProgrammeDTO programmeDTO = new ProgrammeDTO();

            programmeDTO.setProgrammeId(programme.getProgrammeId());
            programmeDTO.setProgrammeName(programme.getProgrammeName());
            programmeDTO.setDuration(programme.getDuration());
            programmeDTO.setFee(programme.getFee());

            programmeDTOS.add(programmeDTO);
        }
        return programmeDTOS;
    }
    @Override
    public String generateNewProgrammeID() throws Exception {
        return programmeDAO.generateNewID();
    }

    @Override
    public boolean updateProgram(ProgrammeDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return programmeDAO.update(new Programme(dto.getProgrammeId(), dto.getProgrammeName(), dto.getDuration(), dto.getFee()));

    }

    @Override
    public boolean deleteProgram(String programmeId) throws SQLException, ClassNotFoundException, IOException {
        return programmeDAO.delete(programmeId);
    }
}

