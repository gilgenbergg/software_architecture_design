package controller;

import facade.Facade;
import facade.Starter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.ClientRequest;
import model.Landscaper;
import model.Plant;

import java.sql.SQLException;
import java.util.List;

public class ClientReqEditorController {

    public Label plantTypeLabel;
    private Facade facade = Starter.facade;

    public TextField cReqIDField;
    public Label viewLabel;
    public Label typeLabel;
    public RadioButton isPlannedRadio;
    public RadioButton isFirstRadio;
    public Label plantIDLabel;
    public ComboBox plantIDField;
    public Label landscaperIDLabel;
    public ComboBox landscaperIDField;
    public Label statusLabel;
    public RadioButton isNewOneRadio;
    public RadioButton isInPurchaseRadio;
    public RadioButton isInProgressRadio;
    public RadioButton isGardeningRadio;
    public RadioButton isDoneRadio;
    public Button finishEditionButton;
    public Label errorMsg;
    public Button backButton;
    public Label clientIDLabel;
    public Label clientIDField;

    private String chosenStatus = "";
    private String chosenType = "";
    private Integer cReqIDSetup;
    private Integer adminID;
    private Integer chosenPlantID;
    private Integer chosenLandscaperID;

    public ClientReqEditorController() throws SQLException, ClassNotFoundException {
    }

    public void finishEditionClicked(MouseEvent mouseEvent) throws SQLException {
        if ((landscaperIDField.getValue() == null) || (plantIDField.getValue() == null)) {
            errorMsg.setText("Please fill empty fields.");
            return;
        }
        try {
            facade.updatePlant(Integer.parseInt(plantIDField.getValue().toString()), facade.findCReqByID(cReqIDSetup));
            facade.updateCReqLandscaperID(cReqIDSetup, Integer.parseInt(landscaperIDField.getValue().toString()));
            facade.updateCReqStatus(cReqIDSetup, facade.parseCReqStatusFromDB(chosenStatus));
            facade.updateCReqType(cReqIDSetup, facade.parseCReqTypeFromDB(chosenType));
            Integer adminID = (facade.findCReqByID(cReqIDSetup)).getAdminID();
            Starter.showAdminView(facade.getAdminByUserID(adminID));
        } catch (Exception e) {
            errorMsg.setText(e.getMessage());
        }
    }

    public void setData(Integer cReqID, Integer adminID) throws SQLException, ClassNotFoundException {
        cReqIDSetup = cReqID;
        cReqIDField.setText(cReqID.toString());
        this.adminID = adminID;

        ClientRequest cReq = facade.findCReqByID(cReqID);
        clientIDField.setText(cReq.getClientID().toString());
        landscaperIDField.setValue(cReq.getLandscaperID().toString());

        ObservableList<Integer> plantsIDS = FXCollections.observableArrayList();
        List<Plant> allPlants= facade.filterPlantsByUserID(cReq.getClientID());
        for (Plant item:
                allPlants) {
            plantsIDS.add(item.getPlantID());
        }
        plantIDField.setItems(plantsIDS.sorted());

        ObservableList<Integer> landscapersIDS = FXCollections.observableArrayList();
        List<Landscaper> allLandscapers= facade.allLandscapers();
        for (Landscaper item:
                allLandscapers) {
            landscapersIDS.add(item.getUID());
        }
        landscaperIDField.setItems(landscapersIDS.sorted());

        if (cReq.getType() == ClientRequest.Type.firstOne) {
            isFirstRadio.setSelected(true);
            this.chosenType = isFirstRadio.getText();
        }
        if (cReq.getType() == ClientRequest.Type.planned) {
            isPlannedRadio.setSelected(true);
            this.chosenType = isPlannedRadio.getText();
        }
        if (cReq.getLandscaperID() != null) {
            landscaperIDField.setValue(cReq.getLandscaperID().toString());
        }
        if (cReq.getPlantID() != null) {
            plantIDField.setValue(cReq.getPlantID().toString());
        }
        if (cReq.getStatus() == ClientRequest.Status.newOne) {
            isNewOneRadio.setSelected(true);
            this.chosenStatus = isNewOneRadio.getText();
        }
        if (cReq.getStatus() == ClientRequest.Status.inProgress) {
            isInProgressRadio.setSelected(true);
            this.chosenStatus = isInProgressRadio.getText();
        }
        if (cReq.getStatus() == ClientRequest.Status.inPurchase) {
            isInPurchaseRadio.setSelected(true);
            this.chosenStatus = isInPurchaseRadio.getText();
        }
        if (cReq.getStatus() == ClientRequest.Status.gardening) {
            isGardeningRadio.setSelected(true);
            this.chosenStatus = isGardeningRadio.getText();
        }
        if (cReq.getStatus() == ClientRequest.Status.done) {
            isDoneRadio.setSelected(true);
            this.chosenStatus = isDoneRadio.getText();
        }
    }

    public void isPlannedClicked(MouseEvent mouseEvent) {
        isPlannedRadio.setSelected(true);
        isFirstRadio.setSelected(false);
        this.chosenType = isPlannedRadio.getText();
    }

    public void isFirstClicked(MouseEvent mouseEvent) {
        isFirstRadio.setSelected(true);
        isPlannedRadio.setSelected(false);
        this.chosenType = isFirstRadio.getText();
    }

    public void isNewOneClicked(MouseEvent mouseEvent) {
        isNewOneRadio.setSelected(true);
        isInProgressRadio.setSelected(false);
        isInPurchaseRadio.setSelected(false);
        isGardeningRadio.setSelected(false);
        isDoneRadio.setSelected(false);
        this.chosenStatus = isNewOneRadio.getText();
    }

    public void isInPurchaseClicked(MouseEvent mouseEvent) {
        isInPurchaseRadio.setSelected(true);
        isInProgressRadio.setSelected(false);
        isNewOneRadio.setSelected(false);
        isGardeningRadio.setSelected(false);
        isDoneRadio.setSelected(false);
        this.chosenStatus = isInPurchaseRadio.getText();
    }

    public void isInProgressClicked(MouseEvent mouseEvent) {
        isInProgressRadio.setSelected(true);
        isNewOneRadio.setSelected(false);
        isInPurchaseRadio.setSelected(false);
        isGardeningRadio.setSelected(false);
        isDoneRadio.setSelected(false);
        this.chosenStatus = isInProgressRadio.getText();
    }

    public void isGardeningClicked(MouseEvent mouseEvent) {
        isGardeningRadio.setSelected(true);
        isInPurchaseRadio.setSelected(false);
        isInProgressRadio.setSelected(false);
        isNewOneRadio.setSelected(false);
        isDoneRadio.setSelected(false);
        this.chosenStatus = isGardeningRadio.getText();
    }

    public void isDoneClicked(MouseEvent mouseEvent) {
        isDoneRadio.setSelected(true);
        isNewOneRadio.setSelected(false);
        isInProgressRadio.setSelected(false);
        isInPurchaseRadio.setSelected(false);
        isGardeningRadio.setSelected(false);
        this.chosenStatus = isDoneRadio.getText();
    }

    public void backButtonOnCLicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Starter.showAdminView(facade.getAdminByUserID(adminID));
    }

    public void plantIDChosen(ActionEvent actionEvent) throws SQLException {
        this.chosenPlantID = Integer.parseInt(plantIDField.getValue().toString());
        plantTypeLabel.setText(facade.findItemByPlantID(chosenPlantID).getType());
    }

    public void landscaperIDChosen(ActionEvent actionEvent) {
        this.chosenLandscaperID = Integer.parseInt(landscaperIDField.getValue().toString());
    }
}
