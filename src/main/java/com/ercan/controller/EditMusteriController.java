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

public class EditMusteriController implements Initializable {


    private SceneController sceneController;
    private User user;
    private UserData userData;

    public TextField musteri_name;
    public TextField musteri_tel;
    public TextArea musteri_adres;

    public EditMusteriController() {}

    public EditMusteriController(Initializable sceneController, User user, UserData userData) {
        this.sceneController = (SceneController) sceneController;
        this.user = user;
        this.userData = userData;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        musteri_name.setText(userData.name);
        musteri_tel.setText(userData.tel);
        musteri_adres.setText(userData.address);
    }

    public void onMouseClickedKaydet(MouseEvent mouseEvent) {
        if(isReadyToSubmit()) {
            userData.setName(musteri_name.getText());
            userData.setTel(musteri_tel.getText());
            userData.setAddress(musteri_adres.getText());
            sceneController.repo.updateUser(user, userData);
            AlertUtil.informationMsgOnlyOk("Müşteri bilgileri başarılı bir şekilde güncellendi.",
                    result -> {
                        if (result.isPresent() && result.get() == ButtonType.OK){
                            sceneController.closeTab(Naming.MUSTERI_TAB);
                            sceneController.closeTab(Naming.MUSTERI_EDIT_TAB);
                            sceneController.addNewTab("/profile_musteri.fxml", Naming.MUSTERI_TAB,"profile_musteri",
                                    new ProfileMusteriController(
                                            sceneController,
                                            sceneController.repo.findUser(musteri_name.getText(), UserType.CUSTOMER)
                                    )
                            );
                        }
                    });
        } else {
            AlertUtil.fieldsEmptyWarning();
        }
    }

    public void onMouseClickedSil(MouseEvent mouseEvent) {
        AlertUtil.confirmMsg(userData.name+" müşterisini silmek istediğinize emin misiniz?", result -> {
            if (result.isPresent() && result.get() == ButtonType.OK){
                boolean ret = sceneController.repo.removeUser(user);
                if(ret) {
                    AlertUtil.informationMsgOnlyOk(userData.name+" müşterisi başarılı bir şekilde silindi.", result1 -> {
                        if (result1.isPresent() && result1.get() == ButtonType.OK) {
                            sceneController.closeTab(Naming.MUSTERI_TAB);
                            sceneController.closeTab(Naming.MUSTERI_EDIT_TAB);
                        }
                    });
                } else {
                    AlertUtil.errorMsg(userData.name+" müşterisi silinemedi!");
                }
            }
        });
    }

    private boolean isReadyToSubmit() {
        return musteri_name.getText() != null && !musteri_name.getText().isEmpty() &&
                musteri_tel.getText() != null && !musteri_tel.getText().isEmpty() &&
                musteri_adres.getText() != null && !musteri_adres.getText().isEmpty();
    }
}
