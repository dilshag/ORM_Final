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
import lk.ijse.bo.custom.ProgrammeBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.ProgrammeDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.User;
import lk.ijse.tdm.ProgrammeTM;
import lk.ijse.tdm.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class StudentFormController implements Initializable {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtStudentName;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnClear;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TableView<StudentTM> tblStudent;

    @FXML
    private TableColumn<?, ?> colStudentID;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colPhoneNumber;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colCoordinatorId;


    @FXML
    private ComboBox<User> cmbCodinatorID;

    @FXML
    private TextField txtSID;

    @FXML
    private TextField txtEmail;


    @FXML
    private Label lblUserId;

      StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);
  //  ProgrammeBO programmeBO = (ProgrammeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROGRAMME);
      ObservableList<StudentTM> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            setStudentID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setCellValueFactory();
        setComboUser();
    }

    private void setComboUser() {
        List<User> users = studentBO.getUserIds(); // Adjust this method to return List<User>
        cmbCodinatorID.getItems().addAll(users);
    }

    private void setCellValueFactory() {
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCoordinatorId.setCellValueFactory(new PropertyValueFactory<>("useId"));


    }

    private void getAll() throws Exception {
        List<StudentDTO> allStudent = studentBO.getAllStudent();

        if (!(allStudent.isEmpty())) {
            for (StudentDTO studentDTO : allStudent) {
                obList.add(new StudentTM(
                        studentDTO.getStudentID(),
                        studentDTO.getStudentName(),
                        studentDTO.getAddress(),
                        studentDTO.getPhoneNumber(),
                        studentDTO.getEmail(),
                        studentDTO.getUserId() ));

                tblStudent.setItems(obList);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Empty Programmes :( !!!").show();
        }
    }

    private void clearFields() {
        txtSID.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtPhoneNumber.clear();
        txtEmail.clear();
    }


    private void setStudentID() throws Exception {
        String studentID = studentBO.generateNewStudentID();
       // String programmeId = programmeBO.generateNewProgrammeID();

        if (studentID == null) {
            txtSID.setText("S0001");
        } else {
            String[] split = studentID.split("[S]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("S%04d", lastDigits);
            txtSID.setText(newID);
        }
    }


    public void cmbCodinatorOnAction(ActionEvent actionEvent) {
        cmbCodinatorID.getValue();
        cmbCodinatorID.requestFocus();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
    clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String studentID = txtSID.getText();

        boolean isDeleted = studentBO.deleteStudent(studentID);
        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Student deleted!").show();
            clearFields();
            // initialize();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        User selectedCordinator = (User) cmbCodinatorID.getSelectionModel().getSelectedItem();

        boolean isSaved = studentBO.saveStudent(new StudentDTO(
                txtSID.getText(),
                txtStudentName.getText(),
                txtAddress.getText(),
                txtPhoneNumber.getText(),
                txtEmail.getText(),
                selectedCordinator));
        if(isSaved){
            setComboUser();
            new Alert(Alert.AlertType.CONFIRMATION,"Student save successfully....!!! :)").show();
            getAll();
            clearFields();

       /* String studentID  = txtSID.getText();
        String studentName  = txtStudentName.getText();
        String address = txtAddress.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String email = txtEmail.getText();

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentID(studentID);
        studentDTO.setStudentName(studentName);
        studentDTO.setAddress(address);
        studentDTO.setPhoneNumber(phoneNumber);
        studentDTO.setEmail(email);

        boolean isAdded = studentBO.saveStudent(studentDTO);

        if (isAdded) {
            new Alert(Alert.AlertType.CONFIRMATION, "Student Added Successful :)!!!").show();
          //  getAll();*/
        }else {
            new Alert(Alert.AlertType.ERROR, "Student not Added :( !!!").show();

        }
    }



    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        User selectedCoordinator = (User) this.cmbCodinatorID.getSelectionModel().getSelectedItem();

        boolean isUpdate = studentBO.updateStudent(new StudentDTO(
                txtSID.getText(),
                txtStudentName.getText(),
                txtAddress.getText(),
                txtPhoneNumber.getText(),
                txtEmail.getText(),
                selectedCoordinator));
        if(isUpdate){
            getAll();
            setCellValueFactory();
           // tblStudent.refresh();
             clearFields();
            setComboUser();
            new Alert(Alert.AlertType.CONFIRMATION,"Student update successfully....!!! :)").show();

        }else {
            new Alert(Alert.AlertType.ERROR,"Student update unsuccessfully....!!! :(").show();
        }
       /* String studentID  = txtSID.getText();
        String studentName  = txtStudentName.getText();
        String address = txtAddress.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String email = txtEmail.getText();


        // Check if any of the fields are empty
        if (!studentID.isEmpty() || !studentName.isEmpty() || !address.isEmpty() || !phoneNumber.isEmpty() || !email.isEmpty()) {

           // ProgrammeDTO programmeDTO = new ProgrammeDTO(programmeId, programmeName, duration, fee);
            StudentDTO studentDTO = new StudentDTO(studentID,studentName,address,phoneNumber,email);
            try {
                boolean isSaved = studentBO.updateStudent(studentDTO);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                    clearFields();
                    //initialize();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
        }*/
    }


    @FXML
    void tblStudentOnMouseClicked(MouseEvent event) {
        StudentTM selectItem = tblStudent.getSelectionModel().getSelectedItem();

        if (selectItem != null){
            txtSID.setText(selectItem.getStudentID());
            txtStudentName.setText(selectItem.getStudentName());
            txtAddress.setText(selectItem.getAddress());
            txtPhoneNumber.setText(selectItem.getPhoneNumber());
            txtEmail.setText(selectItem.getEmail());
            cmbCodinatorID.setValue(selectItem.getUseId());

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnSave.setDisable(true);
        }else {
            btnUpdate.setDisable(true);
        }
    }


}
