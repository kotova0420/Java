package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import sample.models.Tickets;
import sample.models.Clients;
import sample.models.Performances;
import sample.models.Shares;
import sample.Main;

public class TicketNewOneController {

    @FXML
    private ChoiceBox<String> clientBox;

    @FXML
    private ChoiceBox<String> performanceBox;

    @FXML
    private ChoiceBox<String> shareBox;


    //2
    private Stage dialogStage;
    private Tickets ticket;
    private boolean okClicked = false;
    private Main mainApp;


    //4
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        ObservableList<String> clients = FXCollections.observableArrayList();
        ObservableList<String> performances = FXCollections.observableArrayList();
        ObservableList<String> shares = FXCollections.observableArrayList();

        for (Clients client : mainApp.getClients()) {
            clients.add(client.getSurname());
        }

        for (Performances performance : mainApp.getPerformances()) {
            performances.add(performance.getName());
        }

        for (Shares share : mainApp.getShares()) {
            shares.add(share.getName());
        }

        clientBox.setItems(clients);
        performanceBox.setItems(performances);
        shareBox.setItems(shares);

    }


    //5
    public void setTicket(Tickets ticket) {
        this.ticket = ticket;
    }


    //6
    @FXML
    private void handleOkAction() {
        boolean isCorrect = true;

        if (clientBox.getValue() == null | performanceBox.getValue() == null) {
            Main.showAlert("Вы не выбрли обязательные данные", "Выберите клиента и спектакль");
            isCorrect = false;

        } else {
            ticket.setClient(mainApp.getClients().get(clientBox.getSelectionModel().getSelectedIndex()));
            ticket.setPerformance(mainApp.getPerformances().get(performanceBox.getSelectionModel().getSelectedIndex()));
        }

        if (shareBox.getValue() != null) {
            ticket.setShare(mainApp.getShares().get(shareBox.getSelectionModel().getSelectedIndex()));

        } else ticket.setShare(null);


        if (isCorrect) {
            okClicked = true;
            dialogStage.close();
        }
    }

    //7
    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }

    //8
    public boolean isOkClicked() {
        return okClicked;
    }

    //9
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

}
