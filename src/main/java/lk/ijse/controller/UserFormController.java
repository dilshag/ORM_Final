package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.tdm.UserTm;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class UserFormController {


    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXButton btnUserSave;

    @FXML
    private JFXButton btnUserUpdate;

    @FXML
    private JFXButton btnUserDelete;

    @FXML
    private TextField txtUserID;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private   Label lblUserID;

    @FXML
    private ComboBox<String> cmbUserRole;

    @FXML
    private TableView<UserTm> tblUser;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableColumn<?, ?> colUserRole;

   // StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);

    UserBO userBO  = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);


    private void initialize() throws SQLException, IOException {
        generateNewUserID();
        setValuesForRoleCmb();
        setCellValueFactory();
        getAllUsers();
        // showSelectedUserDetails();
        //  buttonsDisableAndEnable();
        //  clickEnterButtonMoveCursor();
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        String userID = txtUserID.getText();

        boolean isDeleted = userBO.deleteUser(userID);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Student deleted!").show();
            clearFields();
            // initialize();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String userID = txtUserID.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = cmbUserRole.getValue();

        // Hash the password with bcrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Create a UserDTO with the hashed password
        UserDTO userDTO = new UserDTO(userID, userName, hashedPassword, role);

        if (userName.isEmpty() || password.isEmpty() || hashedPassword.isEmpty() || cmbUserRole.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
        } else {
            try {
                boolean isSaved = userBO.saveUser(userDTO);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                    clearFields();
                    initialize();
                }
            } catch (SQLException | ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void setValuesForRoleCmb() {
    }

    private void getAllUsers() {

    }

    private void setCellValueFactory() {
    }

    private void clearFields() {

    }

    private void generateNewUserID() throws SQLException, IOException {
       /* String userID = userBO.generateNewUserID();

        if (userID == null) {

           txtUserID.setText("US000001");
        } else {
            try {
                String[] split = userID.split("[U][S]");
                int lastDigits = Integer.parseInt(split[1]);
                lastDigits++;
                String newID = String.format("US%06d", lastDigits);
                txtUserID.setText(newID);
            } catch (Exception e) {
                // Handle unexpected cases, such as invalid userID format
                System.err.println("Error generating new user ID: " + e.getMessage());
                txtUserID.setText("US000001");
            }*/
        String userID = userBO.generateNewUserID();

        if (userID == null) {
            txtUserID.setText("US000001");
        } else {
            String[] split = userID.split("[U][S]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("US%06d", lastDigits);
            txtUserID.setText(newID);
        }
        }
       /* try {
            String nextUserId = userBO.generateNewUserID();

            lblUserID.setText(String.valueOf(nextUserId));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }*/


    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void tblUserOnMouseClicked(MouseEvent event) {

    }

}
