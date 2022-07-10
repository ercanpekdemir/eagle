package com.ercan.controller;

import com.ercan.entity.Product;
import com.ercan.tabledata.ProductData;
import com.ercan.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toList;

public class ProductEditController implements Initializable {
    private SceneController sceneController;
    public TableView product_table;
    public TableColumn<ProductData, String> tc_product_explanation;
    public TableColumn<ProductData, Void> tc_product_sil;
    public Pagination product_pager;
    private int productTableIndex = 0;
    private static int rowsPerPage = 100;

    public TextField yeni_product_name;

    List<ProductData> productList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeProductColumns();

        populateTable();
    }

    public ProductEditController() {}
    public ProductEditController(Initializable sceneController) {
        this.sceneController = (SceneController) sceneController;
    }

    public void onMouseClickedKaydet(MouseEvent mouseEvent) throws IOException {
        boolean res = sceneController.repo.newProduct(yeni_product_name.getText());
        if(res) {
            AlertUtil.informationMsgOnlyOk("\""+yeni_product_name.getText()+"\" "+"ürün listesine eklendi.", result -> {
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    populateTable();
                }
            });
        } else {
            AlertUtil.errorMsg("ürün ismi boş olamaz ya da aynı isimde eklenemez!");
        }
    }

    private void addButtonToProductTable() {
        Callback<TableColumn<ProductData, Void>, TableCell<ProductData, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ProductData, Void> call(TableColumn<ProductData, Void> param) {

                return new TableCell<>() {
                    private final Button btn = new Button("Sil");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int rowIndex = getTableRow().getIndex();
                            ProductData data = productList.get(rowIndex + productTableIndex);
                            boolean res = sceneController.repo.removeProduct(data.productExplanation);
                            if(res) {
                                AlertUtil.informationMsgOnlyOk("\""+data.productExplanation+"\" "+"ürünü silindi.", result -> {
                                    if (result.isPresent() && result.get() == ButtonType.OK) {
                                        populateTable();
                                    }
                                });
                            } else {
                                AlertUtil.errorMsg("\""+data.productExplanation+"\" "+" silinemedi!");
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

        tc_product_sil.setCellFactory(cellFactory);
        tc_product_sil.setPrefWidth(85);
    }

    public Node createProductTable(int pageIndex) {
        productTableIndex = pageIndex * rowsPerPage;
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, productList.size());
        product_table.setItems(FXCollections.observableArrayList(new ArrayList<>(productList.subList(fromIndex, toIndex))));
        return product_table;
    }

    public void populateTable() {
        List<Product> products = sceneController.repo.getProducts();
        productList = products
                .stream()
                .map(it -> new ProductData(it.id, it.productExplanation))
                .collect(toList());
        product_pager.setMaxPageIndicatorCount(10);
        int remainder = productList.size() % rowsPerPage != 0 ? 1 : 0;
        product_pager.setPageCount(productList.size() / rowsPerPage + remainder);
        product_pager.setPageFactory(this::createProductTable);

        addButtonToProductTable();
    }

    private void initializeProductColumns() {
        tc_product_explanation.setCellValueFactory(new PropertyValueFactory<>("productExplanation"));
    }
}
