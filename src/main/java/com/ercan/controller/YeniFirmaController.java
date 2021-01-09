package com.ercan.controller;

import com.ercan.Naming;
import com.ercan.entity.UserType;
import com.ercan.util.AlertUtil;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class YeniFirmaController implements Initializable {

    private SceneController sceneController;

    public TextField yeni_firma_name;
    public TextField yeni_firma_tel;
    public TextArea yeni_firma_adres;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public YeniFirmaController() {}

    public YeniFirmaController(Initializable sceneController) {
        this.sceneController = (SceneController) sceneController;
    }

    public void onMouseClickedKaydet(MouseEvent mouseEvent) throws IOException {
        if(isReadyToSubmit()) {
            if(sceneController.repo.newUser(yeni_firma_name.getText(), yeni_firma_tel.getText(), yeni_firma_adres.getText(), UserType.FIRM)) {
                // good we added
                AlertUtil.informationMsgOnlyOk(yeni_firma_name.getText()+" başarılı şekilde eklendi.",
                        result -> {
                            if (result.isPresent() && result.get() == ButtonType.OK){
                                sceneController.closeTab(Naming.NEW_FIRMA_TAB);
                                sceneController.addNewTab(
                                        "/profile_firma.fxml",
                                        Naming.FIRMA_TAB,
                                        "profile_firma",
                                        new ProfileFirmaController(
                                                sceneController,
                                                sceneController.repo.findUser(yeni_firma_name.getText(), UserType.FIRM)
                                        )
                                );
                            }
                        });
            } else {
                AlertUtil.warningMsg("\""+yeni_firma_name.getText()+"\"" + "  bu isimde firma var");
            }
        } else {
            AlertUtil.fieldsEmptyWarning();
        }
    }

    private boolean isReadyToSubmit() {
        return  yeni_firma_name.getText()!=null && !yeni_firma_name.getText().isEmpty() &&
                yeni_firma_tel.getText()!=null && !yeni_firma_tel.getText().isEmpty() &&
                yeni_firma_adres.getText()!=null && !yeni_firma_adres.getText().isEmpty();
    }
}
