package com.ercan.controller;

import com.ercan.Naming;
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

public class YeniPurchaseController implements Initializable {

    private SceneController sceneController;
    private User user;

    public DatePicker yen_purchase_date;
    public TextField yen_purchase_expl;
    public ChoiceBox<String> yen_purchase_unit_choice;
    public TextField yen_purchase_quantity;
    public TextField yen_purchase_unit_price;

    public YeniPurchaseController() {}


    public YeniPurchaseController(SceneController sceneController, User user) {
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
        yen_purchase_unit_price.setTextFormatter(price);

        TextFormatter<String> quantity = new TextFormatter<>(filterDouble);
        yen_purchase_quantity.setTextFormatter(quantity);
    }

    public void onMouseClickedKaydet(MouseEvent mouseEvent) {
        if(isReadyToSubmit()) {

            sceneController.repo.newPurchase(
                    user,
                    yen_purchase_date.getEditor().getText(),
                    yen_purchase_expl.getText(),
                    Unit.findUnit(yen_purchase_unit_choice.getValue()),
                    Double.parseDouble(yen_purchase_quantity.getText()),
                    Double.parseDouble(yen_purchase_unit_price.getText()));

            AlertUtil.informationMsgOnlyOk(" yeni satış başarılı şekilde eklendi.",
                    result -> {
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            if(user.userType == UserType.CUSTOMER) {
                                sceneController.closeTab(Naming.MUSTERI_TAB);
                                sceneController.closeTab(Naming.MUSTERI_NEW_PURCHASE_TAB);
                                sceneController.addNewTab("/profile_musteri.fxml", Naming.MUSTERI_TAB,"profile_musteri",
                                        new ProfileMusteriController(
                                                sceneController,
                                                sceneController.repo.findUser(user.name, user.userType)
                                        )
                                );
                            } else {
                                sceneController.closeTab(Naming.FIRMA_TAB);
                                sceneController.closeTab(Naming.FIRMA_NEW_PURCHASE_TAB);
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
        return  yen_purchase_expl.getText() != null && !yen_purchase_expl.getText().isEmpty() &&
                yen_purchase_unit_choice.getValue() != null && !yen_purchase_unit_choice.getValue().isEmpty() &&
                yen_purchase_quantity.getText() != null && !yen_purchase_quantity.getText().isEmpty() &&
                yen_purchase_unit_price.getText() != null && !yen_purchase_unit_price.getText().isEmpty();
    }
}
