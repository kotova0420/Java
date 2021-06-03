package sample.controllers;


import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.models.Clients;
import sample.Main;


public class ClientsController {
    //1 переменные
    @FXML
    private TableView<Clients> clientsTableView;

    @FXML
    private TableColumn<Clients, String> clientColumn;

    @FXML
    private TableColumn<Clients, String> surnameColumn;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label birthLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    private Main mainApp;


    //2 заполняю TableView значениями, которые получаю через getClients описанный в main
    //используется один раз в рут контроллере

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        clientsTableView.setItems(mainApp.getClients());
    }


    //3 кнока изменить
    @FXML
    public void handleEditAction() {
        int selectedIndex = clientsTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Clients selectedClient = clientsTableView.getItems().get(selectedIndex);
            boolean isOkClicked = mainApp.showClientsEditDialog(selectedClient);
            if (isOkClicked) {
                mainApp.getClients().set(selectedIndex, selectedClient);
                showClientDetails(selectedClient);
                try {
                    mainApp.update("clients", selectedClient, selectedClient.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            getAlert().showAndWait();
        }
    }


    //4 кнопка удалить
    @FXML
    public void handleDeleteAction() {
        int selectedIndex = clientsTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                mainApp.delete("clients", clientsTableView.getItems().get(selectedIndex).getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            clientsTableView.getItems().remove(selectedIndex);
        } else {
            getAlert().showAndWait();
        }
    }


    //5 при ошибке
    private Alert getAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Ошибка");
        alert.setHeaderText("Клиент не выбран");
        alert.setContentText("Выберите его из левого списка");
        return alert;
    }


    // 6 инициализация
    @FXML
    private void initialize() {
        clientColumn.setCellValueFactory(
                c -> new SimpleStringProperty(
                        c.getValue().getName()
                )
        );

        surnameColumn.setCellValueFactory(
                c -> new SimpleStringProperty(
                        c.getValue().getSurname()
                )
        );


        showClientDetails(null);
        clientsTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> showClientDetails(newValue))

        );
    }

    // 7 метод показывает детали клиента если клиент не равен нулю
    private void showClientDetails(Clients client) {
        if (client != null) {
            nameLabel.setText(client.getName());
            surnameLabel.setText(client.getSurname());
            birthLabel.setText(client.getBirthday());
            phoneLabel.setText(client.getPhone());
            emailLabel.setText(client.getEmail());


        }
    }

}