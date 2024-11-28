package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProgrammeBO;
import lk.ijse.dto.ProgrammeDTO;
import lk.ijse.tdm.ProgrammeTM;
import lk.ijse.tdm.UserTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ProgramFormController implements Initializable {


    @FXML
    private JFXButton btnProgramSave;

    @FXML
    private JFXButton btnProgramUpdate;

    @FXML
    private JFXButton btnProgramDelete;

    @FXML
    private JFXButton btnClear;

    @FXML
    private TableView<ProgrammeTM> tblProgramme;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colProgrammeName;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TextField txtPId;

    @FXML
    private TextField txtProgrammeName;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtDuration;


    ProgrammeBO programmeBO = (ProgrammeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROGRAMME);
    ObservableList<ProgrammeTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            setProgrammeID();
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
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("programmeId"));
        colProgrammeName.setCellValueFactory(new PropertyValueFactory<>("programmeName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }


    private void getAll() throws Exception {
        List<ProgrammeDTO> allPrograms = programmeBO.getAllPrograms();

        if (!(allPrograms.isEmpty())) {
            for (ProgrammeDTO programmeDTO : allPrograms) {
                obList.add(new ProgrammeTM(programmeDTO.getProgrammeId(),
                        programmeDTO.getProgrammeName(), programmeDTO.getDuration(),
                        programmeDTO.getFee()));

                tblProgramme.setItems(obList);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Empty Programmes :( !!!").show();
        }
    }


    private void clearFields() {
        txtPId.clear();
        txtProgrammeName.clear();
        txtDuration.clear();
        txtFee.clear();

    }


    private void setProgrammeID() throws Exception {
        String programmeId = programmeBO.generateNewProgrammeID();

        if (programmeId == null) {
            txtPId.setText("PR000001");
        } else {
            String[] split = programmeId.split("[P][R]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("PR%06d", lastDigits);
            txtPId.setText(newID);
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        ProgrammeTM selectItem = tblProgramme.getSelectionModel().getSelectedItem();

        if (selectItem!= null){
            programmeBO.deleteProgram(selectItem.getProgrammeId());
            new Alert(Alert.AlertType.INFORMATION, "User Deleted (:").show();
            // refreshTable();
           // clearAll();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please select a user to delete ):").show();
        }
        btnProgramUpdate.setDisable(true);
        btnProgramSave.setDisable(false);
        btnProgramDelete.setDisable(true);
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        String programmeId  = txtPId.getText();
        String programmeName = txtProgrammeName.getText();
        String duration = txtDuration.getText();
        double fee = Double.parseDouble(txtFee.getText());

        ProgrammeDTO programmeDTO = new ProgrammeDTO();
        programmeDTO.setProgrammeId(programmeId);
        programmeDTO.setProgrammeName(programmeName);
        programmeDTO.setDuration(duration);
        programmeDTO.setFee(fee);

        boolean isAdded = programmeBO.saveProgram(programmeDTO);

        if (isAdded) {
            new Alert(Alert.AlertType.CONFIRMATION, "Programme Added Successful :)!!!").show();
            getAll();
        }else {
            new Alert(Alert.AlertType.ERROR, "programme not Added :( !!!").show();

        }
    }




    @FXML
    void btnUpdateOnAction(ActionEvent event) throws IOException {
        String programmeId = txtPId.getText();
        String programmeName = txtProgrammeName.getText();
        String duration = txtDuration.getText();
        String feeText = txtFee.getText(); // Get the fee as a String first

        // Check if any of the fields are empty
        if (!programmeId.isEmpty() || !programmeName.isEmpty() || !feeText.isEmpty() || !duration.isEmpty()) {
            double fee = Double.parseDouble(feeText); // Parse fee to double only after checking that it's not empty

            ProgrammeDTO programmeDTO = new ProgrammeDTO(programmeId, programmeName, duration, fee);

            try {
                    boolean isSaved = programmeBO.updateProgram(programmeDTO);
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
            }

    }

    @FXML
    void tblProgramOnMouseClicked(MouseEvent event) {
        ProgrammeTM selectItem = tblProgramme.getSelectionModel().getSelectedItem();

        if (selectItem != null){
            txtPId.setText(selectItem.getProgrammeId());
            txtProgrammeName.setText(selectItem.getProgrammeName());
            txtDuration.setText(selectItem.getDuration());
            txtFee.setText(String.valueOf(selectItem.getFee()));
             btnProgramDelete.setDisable(false);
            btnProgramUpdate.setDisable(false);
              btnProgramSave.setDisable(true);
        }else {
            btnProgramUpdate.setDisable(true);
        }
    }




}
