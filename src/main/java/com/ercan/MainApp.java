package com.ercan;

import com.ercan.controller.SceneController;
import com.ercan.entity.*;
import com.ercan.entity.MyObjectBox;
import com.ercan.repo.DataSource;
import com.ercan.repo.Repository;
import io.objectbox.BoxStore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainApp extends Application {

    static Repository repo;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/scene.fxml"
                )
        );

        BoxStore store = MyObjectBox.builder().name("objectbox-ercan-db").build();


        repo = new Repository(new DataSource(store));

//        createDummyData();
//        test();

        loader.setController(new SceneController(repo, stage));



        loader.load();


        Scene scene = new Scene(loader.getRoot());
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // to open fullscreen
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        stage.setTitle("Pekdemir Emlak İnşaat Müşteri Takip Uygulaması");
        stage.setScene(scene);
        stage.show();




        //todo truncate db: delete this
//        store.close();
//        BoxStore.deleteAllFiles(new File("objectbox-ercan-db"));
    }

    public static void main(String[] args) {
        launch(args);

        // todo import .mdb backup

//        MyObjectBox.builder().initialDbFile(new File("./data.mdb")).build();



    }

    void createDummyData(){

        repo.newUser("ercan pekdemir", "123456","maltepe, aydınevler", UserType.CUSTOMER);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","bdwgwedsv", PaymentType.CASH, 35.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","çimento", Unit.ADET, 10, 40.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","wgwg", PaymentType.CASH, 55.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","demir", Unit.ADET, 10, 30.0);
        repo.newPayment(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tujty", PaymentType.CC, 155.0);
        repo.newPurchase(repo.findUser("ercan pekdemir", UserType.CUSTOMER), "12.12.12","tuğla", Unit.ADET, 10, 20.0);
    }

    void test(){
        User user = repo.findUser("ercan pekdemir", UserType.CUSTOMER);
        System.out.println("balance:"+repo.findBalanceByUser(user).toString());
        System.out.println("payment:"+repo.findPaymentsByUser(user).toString());
        System.out.println("purchase:"+repo.findPurchasesByUser(user).toString());
        repo.removeUser(user);
        if(repo.findBalanceByUser(user) != null ) System.out.println("balance:"+repo.findBalanceByUser(user).toString());
        else System.out.println("balance null");
        if(repo.findPaymentsByUser(user) != null ) System.out.println("payment:"+repo.findPaymentsByUser(user));
        else System.out.println("payment null");
        if(repo.findPurchasesByUser(user) != null ) System.out.println("purchase:"+repo.findPurchasesByUser(user));
        else System.out.println("purchase null");
    }

    void loadFromExternal(){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        String[] pathnames;
        File f = new File(s+"/src/main/asset/");
        pathnames = f.list();
        for (String pathname : pathnames) {
            System.out.println(pathname);
        }
        if(pathnames.length>0){
            BoxStore store1 = MyObjectBox.builder().initialDbFile(new File(s+"/src/main/asset/"+pathnames[0])).build();
        }
    }

}
