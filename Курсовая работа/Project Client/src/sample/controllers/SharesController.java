package sample.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.models.Shares;
import sample.Main;

import java.io.IOException;

public class SharesController {

    //1
    @FXML
    private TableView<Shares> sharesTableView;

    @FXML
    private TableColumn<Shares, String> sharesTableColumn;

    @FXML
    private Label nameLabel;

    @FXML
    private Label startLabel;

    @FXML
    private Label endLabel;

    @FXML
    private Label conditionsLabel;

    @FXML
    private Label discountLabel;

    private Main mainApp;

    //2
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        sharesTableView.setItems(mainApp.getShares());
    }

    //3
    @FXML
    private void initialize() {
        sharesTableColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

        showSharesDetails(null);
        sharesTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> showSharesDetails(newValue))

        );
    }

    //4
    @FXML
    public void handleAddAction() {
        Shares share = new Shares();

        boolean isOkClicked = showSharesEditDialog(share);
        if (isOkClicked) {
            try {
                Shares newShare = mainApp.postShare(share);
                mainApp.getShares().add(newShare);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //5
    public boolean showSharesEditDialog(Shares share) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/SharesEditData.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Параметры записи");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            SharesEditDataController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setShare(share);
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //6
    @FXML
    public void handleEditAction() {
        int selectedIndex = sharesTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Shares selectedShare = sharesTableView.getItems().get(selectedIndex);

            boolean isOkClicked = showSharesEditDialog(selectedShare);
            if (isOkClicked) {
                mainApp.getShares().set(selectedIndex, selectedShare);
                showSharesDetails(selectedShare);
                try {
                    mainApp.update("shares", selectedShare, selectedShare.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            getAlert().showAndWait();
        }
    }

    //7
    @FXML
    public void handleDeleteAction() {
        int selectedIndex = sharesTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                mainApp.delete("shares", sharesTableView.getItems().get(selectedIndex).getId());
                sharesTableView.getItems().remove(selectedIndex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getAlert().showAndWait();
        }
    }

    //8
    private Alert getAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Ошибка");
        alert.setHeaderText("Акция не выбрана");
        alert.setContentText("Выберите ее из левого списка");
        return alert;
    }

    //9
    public void showSharesDetails(Shares share) {
        if (share != null) {
            nameLabel.setText(share.getName());
            startLabel.setText(share.getStart_date());
            endLabel.setText(share.getEnd_date());
            conditionsLabel.setText(share.getConditions());
            discountLabel.setText((100 - share.getCoefficient() * 100) + "%");
        }
    }

}
