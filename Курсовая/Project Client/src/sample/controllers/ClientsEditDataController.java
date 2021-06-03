package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Clients;
import sample.Main;


public class ClientsEditDataController {
    //1 переменные из fxml
    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField birthdayField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    //2 создание окна-подмосток
    private Stage dialogStage;

    //3 создание клиента
    private Clients client;

    //4 для закрытия окна флаг
    private boolean okClicked = false;

    // 5 метод для открытия окна нового
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    //6 то что вытавляется при открытии окна
    public void setClient(Clients client) {
        this.client = client;

        //гет запрос на сервер
        nameField.setText(client.getName());
        surnameField.setText(client.getSurname());
        birthdayField.setText(client.getBirthday());
        emailField.setText(client.getEmail());
        phoneField.setText(client.getPhone());
    }

    //7 переменная флаг для клика
    public boolean isOkClicked() {
        return okClicked;
    }

    //8 кнопка ОК
    // проверка, все ли данные верно введены
    @FXML
    private void handleOkAction() {
        boolean isCorrect = true;

        //обработка ошибки если человек не ввел имя и фамилию
        if (nameField.getText() == null | surnameField.getText() == null) {
            Main.showAlert("Вы не указали обязательные данные", "Имя и фамилия должны быть заполнены.");
            isCorrect = false;

        } else {
            client.setName(nameField.getText());
            client.setSurname(surnameField.getText());
        }


        client.setBirthday(birthdayField.getText());
        client.setEmail(emailField.getText());
        client.setPhone(phoneField.getText());

        if (isCorrect) {
            okClicked = true;
            dialogStage.close();
        }
    }

    //9 кнопка Cancel
    // закрывает окно
    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }
}
