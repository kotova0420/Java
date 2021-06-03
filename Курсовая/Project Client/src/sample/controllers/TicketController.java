package sample.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.models.Tickets;
import sample.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TicketController {

    @FXML
    private TableView<Tickets> ticketsTableView;

    @FXML
    private TableColumn<Tickets, String> performanceColumn;


    @FXML
    private Label performanceLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label discountLabel;

    @FXML
    private Label finalPriceLabel;


    //2
    private Main mainApp;
    private SimpleDateFormat simpleDateFormatEn;
//    private SimpleDateFormat simpleDateFormatRu;
    private SimpleDateFormat simpleDateFormat;


    //3
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ticketsTableView.setItems(mainApp.getTickets());

        simpleDateFormatEn = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
 //       simpleDateFormatRu = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        simpleDateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
    }



    //4
    @FXML
    private void initialize() {
        performanceColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPerformance().getName()));
//        dateColumn.setCellValueFactory(data -> {
//            String dateRu;
//            try {
//                Date dateEn = simpleDateFormatEn.parse(data.getValue().getPerformance().getDate());
//                dateRu = simpleDateFormat.format(dateEn);
//                return new SimpleStringProperty(dateRu);
//
//            } catch (ParseException e) {
//                System.out.println("Ошибка при конвертировании даты.");
//                return null;
//            }
//        });

        showTicketsDetails(null);
        ticketsTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldValue, newValue) -> showTicketsDetails(newValue))

        );
    }



    //7
    private Alert getAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Ошибка");
        alert.setHeaderText("Билет не выбран");
        alert.setContentText("Выберите его из левого списка");
        return alert;
    }


    //8
    public void showTicketsDetails(Tickets ticket) {
        if (ticket != null) {
            nameLabel.setText(ticket.getClient().getName());
            surnameLabel.setText(ticket.getClient().getSurname());
            phoneLabel.setText(ticket.getClient().getPhone());
            emailLabel.setText(ticket.getClient().getEmail());
            performanceLabel.setText(ticket.getPerformance().getName());
            priceLabel.setText(String.valueOf(ticket.getPerformance().getPrice()));
            discountLabel.setText(ticket.getShare().getName());


            //цена
            float newPrice = ticket.getPerformance().getPrice() * (1-((100-ticket.getShare().getCoefficient()*100)/100));
            finalPriceLabel.setText(String.valueOf(newPrice));

            //дата
            dateLabel.setText(ticket.getPerformance().getDate() );
            timeLabel.setText(ticket.getPerformance().getTime());


//            //разбираемся с ценой и скидкой
//            if (ticket.getPerformance().getShare() != null) {
//                discountLabel.setText(ticket.getPerformance().getShare().getName());
//                float newPrice = ticket.getPerformance().getPrice() * ticket.getPerformance().getShare().getCoefficient();
//                finalPriceLabel.setText(String.valueOf(newPrice));
//
//            } else {
//                discountLabel.setText("отсутствует");
//                finalPriceLabel.setText(String.valueOf(ticket.getPerformance().getPrice()));
//            }
//        }
//
//
//            //разбираемся с датой
//            String dateRu;
//            try {
//                Date dateEn = simpleDateFormatEn.parse(ticket.getPerformance().getDate());
//                dateRu = simpleDateFormatRu.format(dateEn);
//                dateLabel.setText(dateRu);
//                timeLabel.setText(ticket.getPerformance().getTime().substring(0, 5));
//
//            } catch (ParseException e) {
//                System.out.println("Ошибка при конвертировании даты.");
//                dateLabel.setText(ticket.getPerformance().getDate() );
//                timeLabel.setText(ticket.getPerformance().getTime());
        }

    }


    @FXML
    public void handleDeleteAction() {
        int selectedIndex = ticketsTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                mainApp.delete("tickets", ticketsTableView.getItems().get(selectedIndex).getId());
                ticketsTableView.getItems().remove(selectedIndex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getAlert().showAndWait();
        }
    }

}
