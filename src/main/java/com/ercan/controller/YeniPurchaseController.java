package com.ercan.controller;

import com.ercan.Naming;
import com.ercan.entity.Product;
import com.ercan.entity.Unit;
import com.ercan.entity.User;
import com.ercan.entity.UserType;
import com.ercan.util.AlertUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class YeniPurchaseController implements Initializable {

    private SceneController sceneController;
    private User user;

    public DatePicker yen_purchase_date;
    public TextField yen_purchase_expl;
    public ChoiceBox<String> yen_purchase_unit_choice;
    public ChoiceBox<String> yen_purchase_search_result;
    public TextField yen_purchase_quantity;
    public TextField yen_purchase_unit_price;

    public List<Product> matchedProductList;

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

        yen_purchase_expl.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 2) {
                matchedProductList = sceneController.repo.findProductByName(newValue);
                if(matchedProductList != null && matchedProductList.size()>1) {
                    yen_purchase_search_result.setItems(
                            FXCollections.observableArrayList(
                                    matchedProductList.stream().map(product -> product.productExplanation).collect(toList())
                            )
                    );
                    yen_purchase_search_result.show();
                } else if(matchedProductList != null && matchedProductList.size() == 1) {
                    yen_purchase_expl.setText(matchedProductList.get(0).productExplanation);
                }
            }
        });

        yen_purchase_search_result.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                System.out.println(yen_purchase_search_result.getItems().get((Integer) number2));
                yen_purchase_expl.setText(yen_purchase_search_result.getItems().get((Integer) number2));
            }
        });
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

    private boolean isReadyToSubmit() {
        return  yen_purchase_expl.getText() != null && !yen_purchase_expl.getText().isEmpty() &&
                yen_purchase_unit_choice.getValue() != null && !yen_purchase_unit_choice.getValue().isEmpty() &&
                yen_purchase_quantity.getText() != null && !yen_purchase_quantity.getText().isEmpty() &&
                yen_purchase_unit_price.getText() != null && !yen_purchase_unit_price.getText().isEmpty();
    }
}
