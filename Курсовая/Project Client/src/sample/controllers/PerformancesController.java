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
import sample.models.Performances;
import sample.Main;

import java.io.IOException;

public class PerformancesController {
    //1
    @FXML
    private TableView<Performances> performancesTableView;

    @FXML
    private TableColumn<Performances, String> performancesTableColumn;

    @FXML
    private Label nameLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label priceLabel;



    private Main mainApp;


    //2 загружаем данные в TableView
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        performancesTableView.setItems(mainApp.getPerformances());
    }


    //3
    @FXML
    private void initialize() {
        performancesTableColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

        showPerformancesDetails(null);
        performancesTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> showPerformancesDetails(newValue))

        );
    }


    //4
    @FXML
    public void handleAddAction() {
        Performances performance = new Performances();

        boolean isOkClicked = showPerformanceEditData(performance);
        if (isOkClicked) {
            try {
                Performances newPerformance = mainApp.postPerformance(performance);
                mainApp.getPerformances().add(newPerformance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    //5
    public boolean showPerformanceEditData(Performances performance) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/PerformancesEditData.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Параметры записи");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            PerformancesEditDataController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.setDialogStage(dialogStage);
            controller.setPerformance(performance);
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
        int selectedIndex = performancesTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Performances selectedPerformance = performancesTableView.getItems().get(selectedIndex);

            boolean isOkClicked = showPerformanceEditData(selectedPerformance);
            if (isOkClicked) {
                mainApp.getPerformances().set(selectedIndex, selectedPerformance);
                showPerformancesDetails(selectedPerformance);
                try {
                    mainApp.update("performances", selectedPerformance, selectedPerformance.getId());
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
        int selectedIndex = performancesTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                mainApp.delete("performances", performancesTableView.getItems().get(selectedIndex).getId());
                performancesTableView.getItems().remove(selectedIndex);
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
        alert.setHeaderText("Спектакль не выбран");
        alert.setContentText("Выберите его из левого списка");
        return alert;
    }


    //9
    public void showPerformancesDetails(Performances performance) {
        if (performance != null) {
            nameLabel.setText(performance.getName());
            dateLabel.setText(performance.getDate());
            timeLabel.setText(performance.getTime());
            priceLabel.setText(performance.getPrice() + " руб.");

        }
    }

}
