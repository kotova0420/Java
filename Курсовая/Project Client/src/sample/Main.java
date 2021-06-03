package sample;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.ClientsEditDataController;
import sample.controllers.RootController;
import sample.models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main extends Application {


    //1 создаем подмостки
    private Stage primaryStage;

    // 2 создаем списки для всех сущностей
    private ObservableList<Tickets> tickets = FXCollections.observableArrayList();
    private ObservableList<Clients> clients = FXCollections.observableArrayList();
    private ObservableList<Performances> performances = FXCollections.observableArrayList();
    private ObservableList<Shares> shares = FXCollections.observableArrayList();

    //1_1 создаем переменную json
    private final Gson gson;


    //3 методы которые возвращают списки сущностей
    public ObservableList<Tickets> getTickets() {
        return tickets;
    }

    public ObservableList<Clients> getClients() {
        return clients;
    }

    public ObservableList<Performances> getPerformances() { return performances; }

    public ObservableList<Shares> getShares() {
        return shares;
    }



    // 4 метод показа ошибки
    public static void showAlert(String header, String content) {
        Main mainApp = new Main();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Ошибка");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // 5  возвращает переменную класса Stage
    public Stage getPrimaryStage() {
        return primaryStage;
    }


    // 6 создает json
    // и заполняет все списки сущностей полученными от сервера значениями
    public Main() {
        gson = new Gson();
        getTicketsFromServer();
        getClientsFromServer();
        getPerformancesFromServer();
        getSharesFromServer();
    }


    // 7 -инициализируется рут
    //    создается сцена, добавляется и показываеися
    //    используется  RootController
    private void initRootLayout() {
        try {
            //загружает страницу созданного файла fxml, находящуюся по указанному пути в проекте
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/RootFavor.fxml"));
            BorderPane rootFavor = loader.load();

            Scene scene = new Scene(rootFavor);
            primaryStage.setScene(scene);
            primaryStage.show();

            RootController rootController = loader.getController();
            //это функции из рут котроллера
            rootController.setMainApp(this);
            rootController.setRootLayout(rootFavor);
            rootController.launchTicketsScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //  8 метод start-с него начинаеися работа приложения, в него подается объект stage
    //  берет primaryStage
    //  делает титул
    //  вызывает initRootLayout
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Информационно-справочная система театра");
        initRootLayout();
    }



    public boolean showClientsEditDialog(Clients client) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/ClientsEditData.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Параметры записи");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ClientsEditDataController controller = loader.getController();
            //методы из ClientsEditDataController
            controller.setDialogStage(dialogStage);
            controller.setClient(client);
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
    }



    //11
    public static void main(String[] args) {
        launch(args);
    }




    //12 запрос к серверу на список сущности
    // в резултьтате в список добавляется ответ сервера
    public void getClientsFromServer() {
        try {
            URL myUrl = new URL("http://localhost:8080/clients");
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            Type clientsType = new TypeToken<List<Clients>>() {
            }.getType();
            List<Clients> newClients = gson.fromJson(response.toString(), clientsType);
            //заполняет переменную clients(список) полученными от сервера значениями
            clients.addAll(newClients);

        } catch (Exception e) {
            System.out.println(".");
        }
    }



    //13
    public void getTicketsFromServer() {
        try {
            URL myUrl = new URL("http://localhost:8080/tickets");
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            Type ticketsType = new TypeToken<List<Tickets>>() {
            }.getType();
            List<Tickets> newTickets = gson.fromJson(response.toString(), ticketsType);
            tickets.addAll(newTickets);

        } catch (Exception e) {
            System.out.println(".");
        }
    }

    //14
    public void getPerformancesFromServer() {
        try {
            URL myUrl = new URL("http://localhost:8080/performances");
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            Type performancesType = new TypeToken<List<Performances>>() {
            }.getType();
            List<Performances> newPerformances = gson.fromJson(response.toString(), performancesType);
            performances.addAll(newPerformances);

        } catch (Exception e) {
            System.out.println(".");
        }
    }

    //16
    public void getSharesFromServer() {
        try {
            URL myUrl = new URL("http://localhost:8080/shares");
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            Type sharesType = new TypeToken<List<Shares>>() {
            }.getType();
            List<Shares> newShares = gson.fromJson(response.toString(), sharesType);
            shares.addAll(newShares);

        } catch (Exception e) {
            System.out.println(".");
        }
    }



    //17
    public Performances postPerformance(Performances performance) throws Exception {
        URL myUrl = new URL("http://localhost:8080/performances");
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String json = gson.toJson(performance);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return gson.fromJson(response.toString(), Performances.class);
        }
    }


    //18
    public Tickets postTicket(Tickets ticket) throws Exception {
        URL myUrl = new URL("http://localhost:8080/tickets");
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String json = gson.toJson(ticket);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return gson.fromJson(response.toString(), Tickets.class);
        }
    }

    //20
    public Clients postClient(Clients client) throws Exception {
        URL myUrl = new URL("http://localhost:8080/clients");
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String json = gson.toJson(client);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return gson.fromJson(response.toString(), Clients.class);
        }
    }

    //21
    public Shares postShare(Shares share) throws Exception {
        URL myUrl = new URL("http://localhost:8080/shares");
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String json = gson.toJson(share);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return gson.fromJson(response.toString(), Shares.class);
        }
    }

    //22
    public void update(String path, Object o, String id) throws Exception {
        URL myUrl = new URL("http://localhost:8080/" + path + "/" + id);
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String json = gson.toJson(o);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }

    }

    //23
    // delete запрос
    public void delete(String path, String id) throws Exception {
        URL myUrl = new URL("http://localhost:8080/" + path + "/" + id);
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("DELETE");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }

    }
}

