package com.ercan.controller;

import com.ercan.Naming;
import com.ercan.component.MyButton;
import com.ercan.entity.*;
import com.ercan.repo.CalculatorUtil;
import com.ercan.tabledata.PaymentTransactionData;
import com.ercan.tabledata.PurchaseData;
import com.ercan.tabledata.UserData;
import com.ercan.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toList;

public class ProfileMusteriController  implements Initializable {
    public TableView payment_table;
    public TableView purchase_table;

    public TableColumn<PaymentTransactionData, String> tc_payment_date;
    public TableColumn<PaymentTransactionData, String> tc_payment_explanation;
    public TableColumn<PaymentTransactionData, String> tc_payment_type;
    public TableColumn<PaymentTransactionData, Double> tc_payment_amount;
    public TableColumn<PaymentTransactionData, Void> tc_payment_edit;

    public TableColumn<PurchaseData, String> tc_purchase_date;
    public TableColumn<PurchaseData, String> tc_purchase_explanation;
    public TableColumn<PurchaseData, String> tc_purchase_unit;
    public TableColumn<PurchaseData, Integer> tc_purchase_quantity;
    public TableColumn<PurchaseData, Double> tc_purchase_unit_price;
    public TableColumn<PurchaseData, Double> tc_purchase_price;
    public TableColumn<PurchaseData, Void> tc_purchase_edit;
    public Pagination purchase_pager;
    public Pagination payment_pager;

    public Label musteri_name;
    public Label musteri_tel;
    public TextArea musteri_adres;
    public Label musteri_totalPurchase;
    public Label musteri_totalCashPayment;
    public Label musteri_totalCCPayment;
    public Label musteri_totalCEKPayment;
    public Label musteri_totalReturnPayment;
    public Label musteri_total_tahsilat;
    public Label musteri_balance;

    public AnchorPane profile_musteri;

    private User user;

    List<PurchaseData> purchaseList;
    List<PaymentTransactionData> paymentList;
    private static int rowsPerPage = 50;

    private int purchaseTableIndex = 0;
    private int paymentTableIndex = 0;

    private SceneController sceneController;

    public ProfileMusteriController() {}

    public ProfileMusteriController(Initializable sceneController, User user) {
        this.sceneController = (SceneController) sceneController;
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initColumns();
//        dummyData();

        List<Purchase> purchases = sceneController.repo.findPurchasesByUser(user);
        purchaseList = purchases
                .stream()
                .map(it -> new PurchaseData(it.id, it.date, it.productExplanation, it.unit.getName(), it.quantity, String.valueOf(it.unitPrice), String.valueOf(it.price)))
                .collect(toList());
        purchase_pager.setMaxPageIndicatorCount(10);
        int remainder = purchaseList.size() % rowsPerPage != 0 ? 1 : 0;
        purchase_pager.setPageCount(purchaseList.size() / rowsPerPage + remainder);
        purchase_pager.setPageFactory(this::createPurchasePage);

        List<PaymentTransaction> payments = sceneController.repo.findPaymentsByUser(user);
        paymentList = payments
                .stream()
                .map(it -> new PaymentTransactionData(it.id, it.date, it.paymentExplanation, it.paymentType.getType(), String.valueOf(it.amount)))
                .collect(toList());
        payment_pager.setMaxPageIndicatorCount(10);
        remainder = paymentList.size() % rowsPerPage != 0 ? 1 : 0;
        payment_pager.setPageCount(paymentList.size() / rowsPerPage + remainder);
        payment_pager.setPageFactory(this::createPaymentPage);

        addButtonToTable();

        Balance balance = sceneController.repo.findBalanceByUser(user);
        musteri_name.setText(user.name);
        musteri_tel.setText(user.tel);
        musteri_adres.setText(user.address);
        musteri_totalPurchase.setText(CalculatorUtil.amountFormatter(balance.totalPurchase));
        musteri_totalCashPayment.setText(CalculatorUtil.amountFormatter(balance.totalCashPayment));
        musteri_totalCCPayment.setText(CalculatorUtil.amountFormatter(balance.totalCCPayment));
        musteri_totalCEKPayment.setText(CalculatorUtil.amountFormatter(balance.totalCEKPayment));
        musteri_totalReturnPayment.setText(CalculatorUtil.amountFormatter(balance.totalReturnPayment));
        musteri_total_tahsilat.setText(CalculatorUtil.amountFormatter(balance.totalTahsilat));
        musteri_balance.setText(CalculatorUtil.amountFormatter(balance.balance));
    }


    private void dummyData(){
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));


        for (int i = 0; i < 50; i++) {
            PaymentTransactionData pt = new PaymentTransactionData(i, date, "bla bla"+i, PaymentType.CASH.getType(), "i");
            PurchaseData pd = new PurchaseData(i, date, "af"+i, "afsg", 4, "5.0", "i");
            purchaseList.add(pd);
            paymentList.add(pt);
        }
    }

    private TableView<PurchaseData> createPurchaseTable() {
        TableView<PurchaseData> table = new TableView<>();
        TableColumn<PurchaseData, String> tc_purchase_date = new TableColumn<>("Tarih");
        TableColumn<PurchaseData, String> tc_purchase_explanation = new TableColumn<>("Açıklama");
        TableColumn<PurchaseData, String> tc_purchase_unit = new TableColumn<>("Birim");
        TableColumn<PurchaseData, Integer> tc_purchase_quantity = new TableColumn<>("Miktar");
        TableColumn<PurchaseData, Double> tc_purchase_unit_price = new TableColumn<>("Birim Fiyatı");
        TableColumn<PurchaseData, Double> tc_purchase_price = new TableColumn<>("Tutar");
        tc_purchase_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tc_purchase_explanation.setCellValueFactory(new PropertyValueFactory<>("productExplanation"));
        tc_purchase_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        tc_purchase_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tc_purchase_unit_price.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tc_purchase_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tc_purchase_date.setPrefWidth(50);
        tc_purchase_explanation.setPrefWidth(50);
        tc_purchase_unit.setPrefWidth(50);
        tc_purchase_quantity.setPrefWidth(50);
        tc_purchase_unit_price.setPrefWidth(50);
        tc_purchase_price.setPrefWidth(50);

        table.getColumns().addAll(tc_purchase_date, tc_purchase_explanation, tc_purchase_unit, tc_purchase_quantity, tc_purchase_unit_price, tc_purchase_price);
        table.setPrefHeight(500);
        table.setPrefWidth(500);
        return table;
    }

    public Node createPurchasePage(int pageIndex) {
        purchaseTableIndex = pageIndex * rowsPerPage;
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, purchaseList.size());
        purchase_table.setItems(FXCollections.observableArrayList(purchaseList.subList(fromIndex, toIndex).stream().map(PurchaseData::copy).collect(toList())));
        return purchase_table;
    }

    public Node createPaymentPage(int pageIndex) {
        paymentTableIndex = pageIndex * rowsPerPage;
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, paymentList.size());
        payment_table.setItems(FXCollections.observableArrayList(paymentList.subList(fromIndex, toIndex).stream().map(PaymentTransactionData::copy).collect(toList())));
        return payment_table;
    }

    private void addButtonToTable() {
        addButtonToPaymentTable();
        addButtonToPurchaseTable();
    }

    private void addButtonToPaymentTable() {
        Callback<TableColumn<PaymentTransactionData, Void>, TableCell<PaymentTransactionData, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<PaymentTransactionData, Void> call(TableColumn<PaymentTransactionData, Void> param) {

                return new TableCell<>() {

                    private final Button btn = new Button("Düzenle");
                    {

                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                int rowIndex = getTableRow().getIndex();
                                PaymentTransactionData paymentData = paymentList.get(rowIndex + paymentTableIndex);
                                sceneController.addNewTab("/edit_payment.fxml", Naming.MUSTERI_PAYMENT_EDIT_TAB,"edit_payment", new EditPaymentController(sceneController, user, paymentData));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        btn.setStyle("-fx-background-color: #006cb4");
                        btn.setTextFill(Color.WHITE);
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        tc_payment_edit.setCellFactory(cellFactory);
        tc_payment_edit.setPrefWidth(100);
     }

    private void addButtonToPurchaseTable() {
        Callback<TableColumn<PurchaseData, Void>, TableCell<PurchaseData, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<PurchaseData, Void> call(TableColumn<PurchaseData, Void> param) {

                return new TableCell<>() {
                    private final MyButton btn = new MyButton("Düzenle");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                int rowIndex = getTableRow().getIndex();
                                PurchaseData purchaseData = purchaseList.get(rowIndex + purchaseTableIndex);
                                sceneController.addNewTab("/edit_purchase.fxml", Naming.MUSTERI_PURCHASE_EDIT_TAB,"edit_purchase", new EditPurchaseController(sceneController, user, purchaseData));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        btn.setStyle("-fx-background-color: #006cb4");
                        btn.setTextFill(Color.WHITE);
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        tc_purchase_edit.setCellFactory(cellFactory);
        tc_purchase_edit.setPrefWidth(20);
    }

     private void initializePaymentColumns() {
         tc_payment_date.setCellValueFactory(new PropertyValueFactory<>("date"));
         tc_payment_explanation.setCellValueFactory(new PropertyValueFactory<>("paymentExplanation"));
         tc_payment_type.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
         tc_payment_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
     }

    private void initializePurchaseColumns() {
        tc_purchase_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tc_purchase_explanation.setCellValueFactory(new PropertyValueFactory<>("productExplanation"));
        tc_purchase_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        tc_purchase_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tc_purchase_unit_price.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tc_purchase_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void initColumns() {
        initializePaymentColumns();
        initializePurchaseColumns();
    }

    public void onMouseClickedMusteriDuzenle(MouseEvent mouseEvent) throws IOException {
        UserData userData = new UserData(
                user.name,
                user.tel,
                user.address,
                user.userType.getType(),
                purchaseList,
                paymentList
        );
        sceneController.addNewTab("/edit_musteri.fxml", Naming.MUSTERI_EDIT_TAB,"edit_musteri", new EditMusteriController(sceneController, user, userData));
    }

    public void onClickMusteriYazdir(MouseEvent mouseEvent) {
        try {
            printNode(profile_musteri);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void onClickMusteriYeniPayment(MouseEvent mouseEvent) throws IOException {
        sceneController.addNewTab("/yeni_payment.fxml", Naming.MUSTERI_NEW_PAYMENT_TAB,"yeni_payment", new YeniPaymentController(sceneController, user));
    }

    public void onClickMusteriYeniPurchase(MouseEvent mouseEvent) throws IOException {
        sceneController.addNewTab("/yeni_purchase.fxml", Naming.MUSTERI_NEW_PURCHASE_TAB,"yeni_purchase", new YeniPurchaseController(sceneController, user));
    }

    public void printNode(final Node node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Printer printer = Printer.getDefaultPrinter();
        if(printer == null) {
            AlertUtil.errorMsg("Default bir printer bulunamadı!");
            return;
        }
        PageLayout pageLayout
                = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        PrinterAttributes attr = printer.getPrinterAttributes();
        PrinterJob job = PrinterJob.createPrinterJob();
        double scaleX
                = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY
                = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        Scale scale = new Scale(scaleX, scaleY);
        node.getTransforms().add(scale);

        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, node);
            if (success) {
                job.endJob();
            }
        }
        node.getTransforms().remove(scale);
    }
}
