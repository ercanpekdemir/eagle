package com.ercan.controller;

import com.ercan.Naming;
import com.ercan.entity.User;
import com.ercan.entity.UserType;
import com.ercan.tabledata.PurchaseData;
import com.ercan.util.AlertUtil;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class EditPurchaseController implements Initializable {


    private SceneController sceneController;
    private User user;
    private PurchaseData purchaseData;

    public DatePicker purchase_date;
    public TextField purchase_expl;
    public TextField purchase_quantity;
    public TextField purchase_unit_price;
    public ChoiceBox<String> purchase_unit_chose;

    public Label purchase_title;

    public EditPurchaseController() {}

    public EditPurchaseController(Initializable sceneController, User user, PurchaseData purchaseData) {
        this.sceneController = (SceneController) sceneController;
        this.user = user;
        this.purchaseData = purchaseData;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        purchase_title.setText("Satışı düzenle, "+user.name);

        purchase_date.setPromptText(purchaseData.date);
        purchase_expl.setText(purchaseData.productExplanation);
        purchase_quantity.setText(String.valueOf(purchaseData.quantity));
        purchase_unit_price.setText(purchaseData.unitPrice);
        purchase_unit_chose.getSelectionModel().select(purchaseData.unit);

        UnaryOperator<TextFormatter.Change> filterDouble = change -> {
            Pattern validEditingState = Pattern.compile("\\d*|\\d+\\.\\d*");
            return validEditingState.matcher(change.getControlNewText()).matches() ? change : null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filterDouble);
        TextFormatter<String> textFormatter1 = new TextFormatter<>(filterDouble);
        purchase_quantity.setTextFormatter(textFormatter);
        purchase_unit_price.setTextFormatter(textFormatter1);
    }

    public void onMouseClickedKaydet(MouseEvent mouseEvent) {
        if(isReadyToSubmit()) {

            if(purchase_date.getEditor().getText() != null
                    && !purchase_date.getEditor().getText().isEmpty()) {
                purchaseData.setDate(purchase_date.getEditor().getText());
            }
            purchaseData.setProductExplanation(purchase_expl.getText());
            purchaseData.setUnit(purchase_unit_chose.getSelectionModel().getSelectedItem());
            purchaseData.setQuantity(Double.parseDouble(purchase_quantity.getText()));
            purchaseData.setUnitPrice(purchase_unit_price.getText());

            sceneController.repo.updatePurchase(
                user, purchaseData
            );

            AlertUtil.informationMsgOnlyOk("Satış başarılı şekilde güncellendi.",
                    result -> {
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            if(user.userType == UserType.CUSTOMER) {
                                sceneController.closeTab(Naming.MUSTERI_TAB);
                                sceneController.closeTab(Naming.MUSTERI_PURCHASE_EDIT_TAB);
                                sceneController.addNewTab("/profile_musteri.fxml", Naming.MUSTERI_TAB,"profile_musteri",
                                        new ProfileMusteriController(
                                                sceneController,
                                                sceneController.repo.findUser(user.name, user.userType)
                                        )
                                );
                            } else {
                                sceneController.closeTab(Naming.FIRMA_TAB);
                                sceneController.closeTab(Naming.FIRMA_PURCHASE_EDIT_TAB);
                                sceneController.addNewTab("/profile_firma.fxml", Naming.FIRMA_TAB,"profile_firma",
                                        new ProfileFirmaController(
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
        AlertUtil.confirmMsg("Satışı silmek istediğinize emin misiniz?", result -> {
            if (result.isPresent() && result.get() == ButtonType.OK){
                boolean ret = sceneController.repo.removePurchase(user, purchaseData);
                if(ret) {
                    AlertUtil.informationMsgOnlyOk("Satış başarılı bir şekilde silindi.", result1 -> {
                        if (result1.isPresent() && result1.get() == ButtonType.OK) {
                            if(user.userType == UserType.CUSTOMER) {
                                sceneController.closeTab(Naming.MUSTERI_TAB);
                                sceneController.closeTab(Naming.MUSTERI_PURCHASE_EDIT_TAB);
                                sceneController.addNewTab("/profile_musteri.fxml", Naming.MUSTERI_TAB,"profile_musteri",
                                        new ProfileMusteriController(
                                                sceneController,
                                                sceneController.repo.findUser(user.name, user.userType)
                                        )
                                );
                            } else {
                                sceneController.closeTab(Naming.FIRMA_TAB);
                                sceneController.closeTab(Naming.FIRMA_PURCHASE_EDIT_TAB);
                                sceneController.addNewTab("/profile_firma.fxml", Naming.FIRMA_TAB,"profile_firma",
                                        new ProfileFirmaController(
                                                sceneController,
                                                sceneController.repo.findUser(user.name, user.userType)
                                        )
                                );
                            }
                        }
                    });
                } else {
                    AlertUtil.errorMsg("Satış silinemedi!");
                }
            }
        });
    }

    private boolean isReadyToSubmit() {
        return  purchase_expl.getText() != null && !purchase_expl.getText().isEmpty() &&
                purchase_unit_chose.getValue() != null && !purchase_unit_chose.getValue().isEmpty() &&
                purchase_quantity.getText() != null && !purchase_quantity.getText().isEmpty() &&
                purchase_unit_price.getText() != null && !purchase_unit_price.getText().isEmpty();
    }

}
