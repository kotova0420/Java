package sample.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Performances;
import sample.Main;

public class PerformancesEditDataController {
    //1
    @FXML
    private TextField nameField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField priceField;



    //2 новое окно
    private Stage dialogStage;
    // переменная класса Performances
    private Performances performance;
    // флаг для нажатия кнопки
    private boolean okClicked = false;
    // переменная класса mainApp
    private Main mainApp;

    //3 используется один раз в контроллере performancesController
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    //4 пустая инициализация
    @FXML
    private void initialize() {

    }

    //5  используется один раз в контроллере performancesController
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    //6 выставляет данные при открытии окна, получает данные через гет запросы
    public void setPerformance(Performances performance) {
        this.performance = performance;

        nameField.setText(performance.getName());
        dateField.setText(performance.getDate());
        timeField.setText(performance.getTime());

    }


    //7 возвращает флаг
    public boolean isOkClicked() {
        return okClicked;
    }

    //8 кнопка ОК
    @FXML
    private void handleOkAction() {
        boolean isCorrect = true;
        //проверка заполненности трех полей, иначе ошибка
        if (nameField.getText() == null | dateField.getText() == null | timeField.getText() == null | priceField.getText() == null) {
            Main.showAlert("Вы не ввели обязательные данные", "Название, дата и время не могут быть пустыми.");
            isCorrect = false;

        } else {
            performance.setName(nameField.getText());//вставляем данные при правильности
            performance.setDate(dateField.getText());
            performance.setTime(timeField.getText());
            //вставляем цену при правильности
            try {
                performance.setPrice(Float.parseFloat(priceField.getText()));
            } catch (Exception e) {
                Main.showAlert("Ошибка в формате цены", "Вводите цену в формате числа с точкой. Например 189.4 или 90");
            }
        }



        if (isCorrect) {
            okClicked = true;
            dialogStage.close();
        }
    }



    //9 кнопка Cancel закрывает окно без изменений
    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }
}
