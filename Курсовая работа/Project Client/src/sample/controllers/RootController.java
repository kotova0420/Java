package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.models.Clients;
import sample.models.Tickets;
import sample.Main;

import java.io.IOException;

public class RootController {
    //1
    @FXML
    private MenuItem ticketsListItem;

    @FXML
    private MenuItem addTicketItem;

    @FXML
    private MenuItem clientsListItem;

    @FXML
    private MenuItem addClientItem;

    @FXML
    private MenuItem  performancesListItem;

    @FXML
    private MenuItem  sharesListItem;

    @FXML
    private MenuItem  avtorItem;


    //2
    private BorderPane rootFavor;

    private Main mainApp;

    //3
    public void setRootLayout(BorderPane rootLayout) {
        this.rootFavor = rootLayout;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    //4
    @FXML
    private void initialize() {
        addClientItem.setOnAction(actionEvent -> addNewClient());
        addTicketItem.setOnAction(actionEvent -> addNewTicket());
        clientsListItem.setOnAction(actionEvent -> launchClientsScreen());
        ticketsListItem.setOnAction(actionEvent -> launchTicketsScreen());
        performancesListItem.setOnAction(actionEvent -> launchPerformancesScreen());
        sharesListItem.setOnAction(actionEvent -> launchSharesScreen());
        avtorItem.setOnAction(actionEvent -> launchAvtorScreen());
    }

    //5
    @FXML
    private void addNewClient() {
        Clients client = new Clients();
        boolean isOkClicked = showAddClientDialog(client);
        if (isOkClicked) {
            try {
                Clients newClient = mainApp.postClient(client);
                mainApp.getClients().add(newClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //6
    public boolean showAddClientDialog(Clients client) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/ClientsEditData.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Параметры записи");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ClientsEditDataController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setClient(client);
            dialogStage.showAndWait();
            return controller.isOkClicked();


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //7
    @FXML
    private void addNewTicket() {
        Tickets ticket = new Tickets();
        boolean isOkClicked = showAddTicketDialog(ticket);
        if (isOkClicked) {
            try {
                Tickets newTicket = mainApp.postTicket(ticket);
                mainApp.getTickets().add(newTicket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    //8
    public boolean showAddTicketDialog(Tickets ticket) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/TicketNewOne.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Параметры записи");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TicketNewOneController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.setDialogStage(dialogStage);
            controller.setTicket(ticket);
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //9
    public void launchClientsScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/ClientsFavor.fxml"));
            AnchorPane clients = loader.load();

            rootFavor.setCenter(clients);

            ClientsController controller = loader.getController();
            controller.setMainApp(mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchPerformancesScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/PerformancesFavor.fxml"));
            AnchorPane performances = loader.load();

            rootFavor.setCenter(performances);

            PerformancesController controller = loader.getController();
            controller.setMainApp(mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchSharesScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/SharesFavor.fxml"));
            AnchorPane shares = loader.load();

            rootFavor.setCenter(shares);

            SharesController controller = loader.getController();
            controller.setMainApp(mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchTicketsScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/TicketFavor.fxml"));
            AnchorPane tickets = loader.load();

            rootFavor.setCenter(tickets);

            TicketController controller = loader.getController();
            controller.setMainApp(mainApp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void launchAvtorScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/Avtor.fxml"));
            AnchorPane avtors = loader.load();

            rootFavor.setCenter(avtors);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
