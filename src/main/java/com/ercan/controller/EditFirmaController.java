package com.ercan.controller;

import com.ercan.Naming;
import com.ercan.entity.User;
import com.ercan.entity.UserType;
import com.ercan.tabledata.UserData;
import com.ercan.util.AlertUtil;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EditFirmaController implements Initializable {


    private SceneController sceneController;
    private User user;
    private UserData userData;

    public TextField firma_name;
    public TextField firma_tel;
    public TextArea firma_adres;

    public EditFirmaController() {}

    public EditFirmaController(Initializable sceneController, User user, UserData userData) {
        this.sceneController = (SceneController) sceneController;
        this.user = user;
        this.userData = userData;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firma_name.setText(userData.name);
        firma_tel.setText(userData.tel);
        firma_adres.setText(userData.address);
    }

    public void onMouseClickedKaydet(MouseEvent mouseEvent) {
        if(isReadyToSubmit()) {
            userData.setName(firma_name.getText());
            userData.setTel(firma_tel.getText());
            userData.setAddress(firma_adres.getText());
            sceneController.repo.updateUser(user, userData);
            AlertUtil.informationMsgOnlyOk("Firma bilgileri başarılı bir şekilde güncellendi.",
                    result -> {
                        if (result.isPresent() && result.get() == ButtonType.OK){
                            sceneController.closeTab(Naming.FIRMA_TAB);
                            sceneController.closeTab(Naming.FIRMA_EDIT_TAB);
                            sceneController.addNewTab("/profile_firma.fxml", Naming.FIRMA_TAB,"profile_firma",
                                    new ProfileFirmaController(
                                            sceneController,
                                            sceneController.repo.findUser(firma_name.getText(), UserType.FIRM)
                                    )
                            );
                        }
                    });
        } else {
            AlertUtil.fieldsEmptyWarning();
        }
    }

    public void onMouseClickedSil(MouseEvent mouseEvent) {
        AlertUtil.confirmMsg(userData.name+" firmasını silmek istediğinize emin misiniz?", result -> {
            if (result.isPresent() && result.get() == ButtonType.OK){
                boolean ret = sceneController.repo.removeUser(user);
                if(ret) {
                    AlertUtil.informationMsgOnlyOk(userData.name+" firması başarılı bir şekilde silindi.", result1 -> {
                        if (result1.isPresent() && result1.get() == ButtonType.OK) {
                            sceneController.closeTab(Naming.FIRMA_TAB);
                            sceneController.closeTab(Naming.FIRMA_EDIT_TAB);
                        }
                    });
                } else {
                    AlertUtil.errorMsg(userData.name+" firması silinemedi!");
                }
            }
        });
    }

    private boolean isReadyToSubmit() {
        return  firma_name.getText() != null && !firma_name.getText().isEmpty() &&
                firma_tel.getText() != null && !firma_tel.getText().isEmpty() &&
                firma_adres.getText() != null && !firma_adres.getText().isEmpty();
    }
}
