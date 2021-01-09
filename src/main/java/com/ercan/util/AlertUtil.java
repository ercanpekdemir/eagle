package com.ercan.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

public class AlertUtil {
    public static void fieldsEmptyWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText("Bir ya da daha fazla alan boş, lütfen tüm alanları doldurunuz." +
                "Boş bırakmak istediğiniz alana '*' ya da '-' girebilirsiniz.");
        alert.showAndWait();
    }

    public static void warningMsg(String msg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void errorMsg(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hata");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void confirmMsg(String msg,OptionalFunction fn) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Onay");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        Optional<ButtonType> result = alert.showAndWait();
        try {
            fn.run(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void informationMsgOnlyOk(String msg,OptionalFunction fn) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bilgi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.getButtonTypes().setAll(ButtonType.OK);
        Optional<ButtonType> result = alert.showAndWait();
        try {
            fn.run(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
