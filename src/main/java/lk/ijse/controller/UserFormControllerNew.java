package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.tdm.UserTm;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserFormControllerNew implements Initializable{


    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private TableView<UserTm> UserTable;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableColumn<?, ?> colUserRole;

    @FXML
    private TableColumn<?, ?> colUserPassword;

    @FXML
    private TextField txtId;

    UserBO userBO  = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);
    ObservableList<UserTm> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setUserID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setUserTypes();
        try {
            getAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUserPassword.setCellValueFactory(new PropertyValueFactory<>("password"));


    }

    private void getAll() throws IOException {
        List<UserDTO> allUsers = userBO.getAllusers();
        if (!(allUsers.isEmpty())){
            for (UserDTO userDTO : allUsers){
                obList.add(new UserTm(userDTO.getUserId(), userDTO.getUserName(),
                       userDTO.getRole(),userDTO.getPassword()));

                UserTable.setItems(obList);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Empty Users :( !!!").show();
        }

    }


    private void setUserTypes() {
        ObservableList<String> role = FXCollections.observableArrayList();
        //cmbType.setValue("Admin");

        role.add("Admin");
        role.add("Coordinator");

        cmbType.setItems(role);
    }



  /*  private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtPassword.clear();

    }*/

    private void setUserID() throws SQLException, IOException {

        String userID = userBO.generateNewUserID();

        if (userID == null) {
            txtId.setText("US000001");
        } else {
            String[] split = userID.split("[U][S]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("US%06d", lastDigits);
            txtId.setText(newID);
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
       // clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        UserTm selectItem = UserTable.getSelectionModel().getSelectedItem();

        if (selectItem!= null){
            userBO.deleteUser(selectItem.getUserId());
            new Alert(Alert.AlertType.INFORMATION, "User Deleted (:").show();
           // refreshTable();
            clearAll();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please select a user to delete ):").show();
        }
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        String userId = txtId.getText();
        String userName = txtName.getText();
        String userPassword = txtPassword.getText();
        String role = (String) cmbType.getValue();

        // Hash the password with bcrypt
        String hashedPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setUserName(userName);
        userDTO.setPassword(hashedPassword);
        userDTO.setRole(role);

        boolean isSaved = userBO.saveUser(userDTO);

        if (isSaved){

            clearAll();
            new Alert(Alert.AlertType.CONFIRMATION, "User Added successful :) !!!").show();
           // getAll();
          //  refreshTable();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION, "User  Added Unsuccessful :( !!!").show();

        }

    }

    private void clearAll() {
        txtId.clear();
        txtName.clear();
        txtPassword.clear();
        cmbType.setValue(null);

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {

        String id = txtId.getText();
        String userName = txtName.getText();
        String userPassword = txtPassword.getText();
        String role = (String) cmbType.getValue();

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(id);
        userDTO.setUserName(userName);
        userDTO.setPassword(userPassword);
        userDTO.setRole(role);

        boolean isUpdated = userBO.updateUser(userDTO);

        if (isUpdated){
            //refreshTable();
           // getAll();
            clearAll();
            new Alert(Alert.AlertType.CONFIRMATION, "User Updated successful :) !!!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "User Updated Unsuccessful :( !!!").show();
        }

    }

    @FXML
    void tblUserOnMouseClicked(MouseEvent event) {
        UserTm selectItem = UserTable.getSelectionModel().getSelectedItem();

        if (selectItem != null){
            txtId.setText(selectItem.getUserId());
            txtName.setText(selectItem.getUserName());
            txtPassword.setText(selectItem.getPassword());
            cmbType.setValue(selectItem.getRole());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        }else {
            btnUpdate.setDisable(true);
        }
    }


}
