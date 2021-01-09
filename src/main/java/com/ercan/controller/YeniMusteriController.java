package com.ercan.controller;

import com.ercan.Naming;
import com.ercan.entity.UserType;
import com.ercan.util.AlertUtil;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class YeniMusteriController implements Initializable {

    private SceneController sceneController;

    public TextField yeni_musteri_name;
    public TextField yeni_musteri_tel;
    public TextArea yeni_musteri_adres;

    public YeniMusteriController() {}

    public YeniMusteriController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onMouseClickedKaydet(MouseEvent mouseEvent) throws IOException {
        if(isReadyToSubmit()) {
            if(sceneController.repo.newUser(yeni_musteri_name.getText(), yeni_musteri_tel.getText(), yeni_musteri_adres.getText(), UserType.CUSTOMER)) {
                // good we added
                AlertUtil.informationMsgOnlyOk(yeni_musteri_name.getText()+" başarılı şekilde eklendi.",
                        result -> {
                            if (result.isPresent() && result.get() == ButtonType.OK){
                                sceneController.closeTab(Naming.NEW_MUSTERI_TAB);
                                sceneController.addNewTab("/profile_musteri.fxml", Naming.MUSTERI_TAB,"profile_musteri",
                                        new ProfileMusteriController(
                                                sceneController,
                                                sceneController.repo.findUser(yeni_musteri_name.getText(), UserType.CUSTOMER)
                                        )
                                );
                            }
                        });
            } else {
                AlertUtil.warningMsg("\""+yeni_musteri_name.getText()+"\"" + "  bu isimde müşteri var");
            }
        } else {
            AlertUtil.fieldsEmptyWarning();
        }
    }

    private boolean isReadyToSubmit() {
        return yeni_musteri_name.getText()!=null && !yeni_musteri_name.getText().isEmpty() &&
                yeni_musteri_tel.getText()!=null && !yeni_musteri_tel.getText().isEmpty() &&
                yeni_musteri_adres.getText()!=null && !yeni_musteri_adres.getText().isEmpty();
    }
}
