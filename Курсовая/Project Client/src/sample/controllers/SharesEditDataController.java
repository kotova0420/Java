package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Shares;
import sample.Main;

public class SharesEditDataController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField startField;

    @FXML
    private TextField endField;

    @FXML
    private TextField conditionsField;

    @FXML
    private TextField discountField;

    //2
    private Stage dialogStage;
    private Shares share;
    private boolean okClicked = false;

    //3
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    //4
    public void setShare(Shares share) {
        this.share = share;

        nameField.setText(share.getName());
        startField.setText(share.getStart_date());
        endField.setText(share.getStart_date());
        conditionsField.setText(share.getConditions());
        discountField.setText((100 - share.getCoefficient() * 100) + "%");
    }

    //5
    public boolean isOkClicked() {
        return okClicked;
    }

    //6
    @FXML
    private void handleOkAction() {
        boolean isCorrect = true;
        if (nameField.getText() == null | discountField.getText() == null) {
            Main.showAlert("Вы не ввели обязательные данные", "Название и скидка не могут быть пустыми.");
            isCorrect = false;

        } else {
            share.setName(nameField.getText());
            try {
                share.setCoefficient(1 - Float.parseFloat(discountField.getText().substring(0, discountField.getText().length() - 1)) / 100);
            } catch (Exception e) {
                Main.showAlert("Ошибка в форматировании скидки", "Скидка должны быть указана целым числом.");
                isCorrect = false;

            }
        }

        share.setStart_date(startField.getText());
        share.setEnd_date(endField.getText());
        share.setConditions(conditionsField.getText());

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
}
