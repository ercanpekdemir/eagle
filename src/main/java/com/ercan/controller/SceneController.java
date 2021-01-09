package com.ercan.controller;

import com.ercan.Naming;
import com.ercan.entity.User;
import com.ercan.entity.UserType;
import com.ercan.repo.Repository;
import com.ercan.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.stream.Collectors.toList;

public class SceneController implements Initializable {
    Repository repo;
    Stage stage;
    public SceneController(){}
    public SceneController(Repository repository, Stage stage) {
        repo = repository;
        this.stage = stage;
    }

    public TextField searc_musteri_input;
    public TextField searc_firma_input;
    public TabPane tab_pane;
    public Button yeni_musteri;
    public Button yeni_firma;
    public ListView<String> searc_firma_lv;
    public ListView<String> searc_musteri_lv;
    public List<User> searc_firma_list;
    public List<User> searc_musteri_list;

    public ImageView logo;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searc_firma_lv.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                User user = searc_firma_list.get(searc_firma_lv.getSelectionModel().getSelectedIndex());
                if(repo.isUserExist(user)) {
                    try {
                        if(!isTabOpen(Naming.NEW_FIRMA_TAB)) {
                            addNewTab("/profile_firma.fxml", Naming.FIRMA_TAB, "profile_firma", new ProfileFirmaController(SceneController.this, user));
                        } else {
                            AlertUtil.warningMsg("'Yeni Firma' sayfası açıkken firma profil sayfası açamazsınız." +
                                    " Lütfen 'Yeni Firma' sayfasını kapatınız.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertUtil.warningMsg(user.name + " firması silinmiş!");
                }
            }
        });
        searc_musteri_lv.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                User user = searc_musteri_list.get(searc_musteri_lv.getSelectionModel().getSelectedIndex());
                if(repo.isUserExist(user)) {
                    try {
                        if(!isTabOpen(Naming.NEW_MUSTERI_TAB)) {
                            addNewTab("/profile_musteri.fxml", Naming.MUSTERI_TAB,"profile_musteri", new ProfileMusteriController(SceneController.this, user));
                        } else {
                            AlertUtil.warningMsg("'Yeni Müşteri' sayfası açıkken müşteri profil sayfası açamazsınız." +
                                    " Lütfen 'Yeni Müşteri' sayfasını kapatınız.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertUtil.warningMsg(user.name + " müşterisi silinmiş!");
                }
            }
        });
    }

    public void onKeyPressedMusteri(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            String input = searc_musteri_input.getText();
            if(isInputValidToSearch(input)) {
                searc_musteri_list = repo.searchUser(input, UserType.CUSTOMER);
                List<String> userList = searc_musteri_list.stream().map(user -> user.name).collect(toList());
                searc_musteri_lv.setItems(FXCollections.observableList(userList));
            } else {
                //todo show input is not valid to search
            }
        }
    }

    public void onKeyPressedFirma(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            String input = searc_firma_input.getText();
            if(isInputValidToSearch(input)) {
                searc_firma_list = repo.searchUser(input, UserType.FIRM);
                List<String> userlist = searc_firma_list.stream().map(user -> user.name).collect(toList());
                searc_firma_lv.setItems(FXCollections.observableList(userlist));

            } else {
                //todo show input is not valid to search
            }
        }
    }

    public void yeniMusteriOnClick(MouseEvent mouseEvent) throws IOException {
        if(!isTabOpen(Naming.MUSTERI_TAB)) {
            addNewTab("/yeni_musteri.fxml", Naming.NEW_MUSTERI_TAB,"yeni_musteri", new YeniMusteriController(this));
        } else {
            AlertUtil.warningMsg("Musteri profil sayfası açıkken yeni müşteri oluşturamazsınız! " +
                    " Lütfen açık olan profil sayfasını kapatınız.");
        }
    }

    public void yeniFirmaOnClick(MouseEvent mouseEvent) throws IOException {
        if(!isTabOpen(Naming.FIRMA_TAB)) {
            addNewTab("/yeni_firma.fxml", Naming.NEW_FIRMA_TAB, "yeni_firma", new YeniFirmaController(this));
        } else {
            AlertUtil.warningMsg("Firma profil sayfası açıkken yeni müşteri oluşturamazsınız! " +
                    " Lütfen açık olan profil sayfasını kapatınız.");
        }
    }

    public void yeniMusteriOnMausePressed(MouseEvent mouseEvent) {
        yeni_musteri.setStyle("-fx-background-color: #4AA7B4");
    }
    public void yeniMusteriOnMauseReleased(MouseEvent mouseEvent) {
        yeni_musteri.setStyle("-fx-background-color: #006CB4");
    }

    public void yeniFirmaOnMausePressed(MouseEvent mouseEvent) {
        yeni_firma.setStyle("-fx-background-color: #4AA7B4");
    }
    public void yeniFirmaOnMauseReleased(MouseEvent mouseEvent) {
        yeni_firma.setStyle("-fx-background-color: #006CB4");
    }

    public void addNewTab(String resourceName, String tabName, String rootName, Initializable controller) throws IOException {

        if(isTabOpen(tabName)) {
            AlertUtil.warningMsg(tabName + " sayfası zaten açık!");
            return;
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        resourceName
                )
        );
        loader.setController(controller);
        loader.load();
        AnchorPane anchorPane = (AnchorPane)loader.getNamespace().get(rootName);

        Tab tab = new Tab();
        tab.setText(tabName);
        tab.setId(tabName);
        tab.setContent(anchorPane);
        tab_pane.getTabs().addAll(tab);
        SingleSelectionModel<Tab> selectionModel = tab_pane.getSelectionModel();
        selectionModel.select(tab);

        tab.setOnCloseRequest(event -> {
            if(isMusteriProfileTabSelected() && !isMusteriProfileTabAllowedToClosed()) {
                event.consume();
                AlertUtil.warningMsg("Musteriye ait düzenleme ya da ekleme sayfaları açık." +
                        "\nÖncelikle bu sayfalarda ki düzenleme ya da ekleme işlemlerini tamamlayınız ya da kapatınız!");
            } else if(isFirmaProfileTabSelected() && !isFirmaProfileTabAllowedToClosed()) {
                event.consume();
                AlertUtil.warningMsg("Firmaya ait düzenleme ya da ekleme sayfaları açık." +
                        "\nÖncelikle bu sayfalarda ki düzenleme ya da ekleme işlemlerini tamamlayınız ya da kapatınız!");
            } else if(!isMusteriProfileTabSelected() && !isFirmaProfileTabSelected()){
                AlertUtil.confirmMsg("Kaydetmeden çıkmak istediğinize emin misiniz?", result -> {
                    if (result.isPresent() && result.get() == ButtonType.OK){
                        // do nothing, close the tab
                    } else {
                        event.consume();
                    }
                });
            }
        });
    }

    public void closeTab(String tabName) {
        for (Tab tab : tab_pane.getTabs()) {
            if(tab.getId() != null && tab.getId().contains(tabName)) {
                tab_pane.getTabs().remove(tab);
                break;
            }
        }
    }

    private boolean isInputValidToSearch(String input) {
        return input != null && !input.isEmpty() && input.length() > 2;
    }

    private boolean isMusteriProfileTabSelected() {
        String id = tab_pane.getSelectionModel().getSelectedItem().getId();
        return id.equals(Naming.MUSTERI_TAB);
    }

    private boolean isFirmaProfileTabSelected() {
        String id = tab_pane.getSelectionModel().getSelectedItem().getId();
        return id.equals(Naming.FIRMA_TAB);
    }

    private boolean isMusteriProfileTabAllowedToClosed() {
        return !(isTabOpen(Naming.MUSTERI_EDIT_TAB) ||
                isTabOpen(Naming.MUSTERI_PURCHASE_EDIT_TAB) ||
                isTabOpen(Naming.MUSTERI_PAYMENT_EDIT_TAB) ||
                isTabOpen(Naming.MUSTERI_NEW_PAYMENT_TAB) ||
                isTabOpen(Naming.MUSTERI_NEW_PURCHASE_TAB));
    }

    private boolean isFirmaProfileTabAllowedToClosed() {
        return !(isTabOpen(Naming.FIRMA_EDIT_TAB) ||
                isTabOpen(Naming.FIRMA_PAYMENT_EDIT_TAB) ||
                isTabOpen(Naming.FIRMA_PURCHASE_EDIT_TAB) ||
                isTabOpen(Naming.FIRMA_NEW_PAYMENT_TAB) ||
                isTabOpen(Naming.FIRMA_NEW_PURCHASE_TAB));
    }

    private boolean isTabOpen(String tabName) {
        for (Tab tab : tab_pane.getTabs()) {
            if(tab.getId() != null && tab.getId().contains(tabName)) return true;
        }
        return false;
    }

    public void yukleOnClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File fileTobeLoad = fileChooser.showOpenDialog(stage);

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File dest = new File(s+Naming.DB_FILE);

        if (fileTobeLoad != null) {
            AlertUtil.confirmMsg("Bu işlem mevcut verileri silip seçmiş olduğunuz dosyayı yükler. Emin misiniz?", result -> {
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        Files.copy(fileTobeLoad.toPath(), dest.toPath(), REPLACE_EXISTING);
                        AlertUtil.informationMsgOnlyOk("Yükleme başarılı, uygulamayı kapatmak için 'OK'a tıklayınız. Daha sonra uygulamayı tekrar açınız.", result1 -> {
                            if(result1.isPresent() && result1.get() == ButtonType.OK) {
                                stage.close();
                            }
                        });
                    } catch (IOException ex) {
                        AlertUtil.errorMsg("hata:"+ex.getMessage());
                    }
                }
            });
        }
    }
    public void yedekleOnClick(MouseEvent mouseEvent) {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File f = new File(s+Naming.DB_FILE);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veri Yedekle");
        fileChooser.setInitialFileName(f.getName());

        File dest = fileChooser.showSaveDialog(stage);
        if (dest != null) {
            try {
                Files.copy(f.toPath(), dest.toPath(), REPLACE_EXISTING);
            } catch (IOException ex) {
                AlertUtil.errorMsg("hata:"+ex.getMessage());
            }
        }
    }
}
