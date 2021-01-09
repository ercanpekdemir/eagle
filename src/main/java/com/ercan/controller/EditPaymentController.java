package com.ercan.controller;

import com.ercan.Naming;
import com.ercan.entity.User;
import com.ercan.entity.UserType;
import com.ercan.tabledata.PaymentTransactionData;
import com.ercan.util.AlertUtil;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class EditPaymentController implements Initializable {


    private SceneController sceneController;
    private User user;
    private PaymentTransactionData paymentData;

    public DatePicker payment_date;
    public TextField payment_expl;
    public ChoiceBox<String> payment_type;
    public TextField payment_amount;
    public Label payment_title;

    public EditPaymentController() {}

    public EditPaymentController(Initializable sceneController, User user, PaymentTransactionData paymentData) {
        this.sceneController = (SceneController) sceneController;
        this.user = user;
        this.paymentData = paymentData;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        payment_title.setText("Tahsilatı düzenle, " + user.name);

        payment_date.setPromptText(paymentData.date);
        payment_expl.setText(paymentData.paymentExplanation);
        payment_type.getSelectionModel().select(paymentData.paymentType);
        payment_amount.setText(paymentData.amount);

        UnaryOperator<TextFormatter.Change> filterDouble = change -> {
            Pattern validEditingState = Pattern.compile("\\d*|\\d+\\.\\d*");
            return validEditingState.matcher(change.getControlNewText()).matches() ? change : null;
        };
        TextFormatter<String> price = new TextFormatter<>(filterDouble);
        payment_amount.setTextFormatter(price);
    }

    public void onMouseClickedKaydet(MouseEvent mouseEvent) {
        if(isReadyToSubmit()) {

            paymentData.setDate(payment_date.getPromptText());
            paymentData.setPaymentExplanation(payment_expl.getText());
            paymentData.setPaymentType(payment_type.getSelectionModel().getSelectedItem());
            paymentData.setAmount(payment_amount.getText());

            sceneController.repo.updatePayment(
                    user, paymentData
            );

            AlertUtil.informationMsgOnlyOk("Tahsilat başarılı şekilde güncellendi.",
                    result -> {
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            if(user.userType == UserType.CUSTOMER) {
                                sceneController.closeTab(Naming.MUSTERI_TAB);
                                sceneController.closeTab(Naming.MUSTERI_PAYMENT_EDIT_TAB);
                                sceneController.addNewTab("/profile_musteri.fxml", Naming.MUSTERI_TAB,"profile_musteri",
                                        new ProfileMusteriController(
                                                sceneController,
                                                sceneController.repo.findUser(user.name, user.userType)
                                        )
                                );
                            } else {
                                sceneController.closeTab(Naming.FIRMA_TAB);
                                sceneController.closeTab(Naming.FIRMA_PAYMENT_EDIT_TAB);
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

    public void onMouseClickedSil(MouseEvent mouseEvent) {
        AlertUtil.confirmMsg("Tahsilatı silmek istediğinize emin misiniz?", result -> {
            if (result.isPresent() && result.get() == ButtonType.OK){
                boolean ret = sceneController.repo.removePayment(user, paymentData);
                if(ret) {
                    AlertUtil.informationMsgOnlyOk("Tahsilat başarılı bir şekilde silindi.", result1 -> {
                        if (result1.isPresent() && result1.get() == ButtonType.OK) {
                            if(user.userType == UserType.CUSTOMER) {
                                sceneController.closeTab(Naming.MUSTERI_TAB);
                                sceneController.closeTab(Naming.MUSTERI_PAYMENT_EDIT_TAB);
                                sceneController.addNewTab("/profile_musteri.fxml", Naming.MUSTERI_TAB,"profile_musteri",
                                        new ProfileMusteriController(
                                                sceneController,
                                                sceneController.repo.findUser(user.name, user.userType)
                                        )
                                );
                            } else {
                                sceneController.closeTab(Naming.FIRMA_TAB);
                                sceneController.closeTab(Naming.FIRMA_PAYMENT_EDIT_TAB);
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
                    AlertUtil.errorMsg("Tahsilat silinemedi!");
                }
            }
        });
    }

    private boolean isReadyToSubmit() {
        return  payment_expl.getText() != null && !payment_expl.getText().isEmpty() &&
                payment_type.getValue() != null && !payment_type.getValue().isEmpty() &&
                payment_amount.getText() != null && !payment_amount.getText().isEmpty();
    }

}
