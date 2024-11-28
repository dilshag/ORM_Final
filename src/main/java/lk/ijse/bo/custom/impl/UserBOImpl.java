package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO <UserDTO> {

   // StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STUDENT);
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveUser(UserDTO entity) throws SQLException, IOException, ClassNotFoundException {
        User user = new User();
        user.setUserId(entity.getUserId());
        user.setUserName(entity.getUserName());
        user.setPassword(entity.getPassword());
        user.setRole(entity.getRole());

        return userDAO.save(user);
    }

    @Override
    public String generateNewUserID() throws SQLException, IOException {
        return userDAO.generateNewID();
    }

    @Override
    public boolean deleteUser(String Id) throws SQLException, IOException, ClassNotFoundException {
        return userDAO.delete(Id);
    }

    @Override
    public List<UserDTO> getAllusers() throws IOException {
        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users){

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUserName(user.getUserName());
            userDTO.setRole(user.getRole());
            userDTO.setPassword(user.getPassword());

            userDTOS.add(userDTO);
        }

        return userDTOS;

    }

    @Override
    public boolean updateUser(UserDTO entity) throws IOException, SQLException, ClassNotFoundException {
        User user = new User();

        user.setUserId(entity.getUserId());
        user.setUserName(entity.getUserName());
        user.setPassword(entity.getPassword());
        user.setRole(entity.getRole());

        return userDAO.update(user);
    }
}
