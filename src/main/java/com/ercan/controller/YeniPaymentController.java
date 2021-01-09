package com.ercan.controller;

import com.ercan.Naming;
import com.ercan.entity.PaymentType;
import com.ercan.entity.Unit;
import com.ercan.entity.User;
import com.ercan.entity.UserType;
import com.ercan.util.AlertUtil;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class YeniPaymentController implements Initializable {

    private SceneController sceneController;
    private User user;

    public DatePicker yeni_payment_date;
    public TextField yeni_payment_expl;
    public ChoiceBox<String> yeni_payment_type;
    public TextField yeni_payment_tutar;

    public YeniPaymentController(SceneController sceneController, User user) {
        this.sceneController = sceneController;
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UnaryOperator<TextFormatter.Change> filterDouble = change -> {
            Pattern validEditingState = Pattern.compile("\\d*|\\d+\\.\\d*");
            return validEditingState.matcher(change.getControlNewText()).matches() ? change : null;
        };
        TextFormatter<String> price = new TextFormatter<>(filterDouble);
        yeni_payment_tutar.setTextFormatter(price);
    }

    public void onMouseClickedKaydet(MouseEvent mouseEvent) {
        if(isReadyToSubmit()) {

            sceneController.repo.newPayment(
                    user,
                    yeni_payment_date.getPromptText(),
                    yeni_payment_expl.getText(),
                    PaymentType.findType(yeni_payment_type.getValue()),
                    Double.parseDouble(yeni_payment_tutar.getText()));

            AlertUtil.informationMsgOnlyOk(" yeni tahsilat başarılı şekilde eklendi.",
                    result -> {
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            if(user.userType == UserType.CUSTOMER) {
                                sceneController.closeTab(Naming.MUSTERI_TAB);
                                sceneController.closeTab(Naming.MUSTERI_NEW_PAYMENT_TAB);
                                sceneController.addNewTab("/profile_musteri.fxml", Naming.MUSTERI_TAB,"profile_musteri",
                                        new ProfileMusteriController(
                                                sceneController,
                                                sceneController.repo.findUser(user.name, user.userType)
                                        )
                                );
                            } else {
                                sceneController.closeTab(Naming.FIRMA_TAB);
                                sceneController.closeTab(Naming.FIRMA_NEW_PAYMENT_TAB);
                                sceneController.addNewTab("/profile_firma.fxml", Naming.FIRMA_TAB,"profile_firma",
                                        new ProfileMusteriController(
                                                sceneController,
                                                sceneController.repo.findUser(user.name, user.userType)
                                        )
                                );
                            }

                        }
                    });

        } else {
            AlertUtil.fieldsEmptyWarning();
        }
    }

    private boolean isReadyToSubmit() {
        return  yeni_payment_expl.getText() != null && !yeni_payment_expl.getText().isEmpty() &&
                yeni_payment_type.getValue() != null && !yeni_payment_type.getValue().isEmpty() &&
                yeni_payment_tutar.getText() != null && !yeni_payment_tutar.getText().isEmpty();
    }
}
