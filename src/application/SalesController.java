package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SalesController implements Initializable {
	public ObservableList<Order> sales;
	public List<Product> products;
	public TableView<Order> saleTable;
	public TableColumn<Order, String> barcodeColumn;
	public TableColumn<Order, String> productColumn;
	public TableColumn<Order, String> quantityColumn;
	public TableColumn<Order, String> priceColumn;
	public Label subtotalLabel;
	public Label taxLabel;
	public Label totalLabel;
	public TextField searchBox;

	public void toProducts(MouseEvent event) {

		Stage productStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Application.fxml"));
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			productStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void toVendors(MouseEvent event) {

		Stage vendorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Vendors.fxml"));
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			vendorStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void toBills(MouseEvent event) {
		Stage billStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Bills.fxml"));
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			billStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addButtonClick() {
		products.forEach(p -> {
			if (!searchBox.getText().isBlank()
					&& p.getBarcode().toLowerCase().contains(searchBox.getText().toLowerCase())) {
				Stage addStage = new Stage();
				addStage.initModality(Modality.APPLICATION_MODAL);
				addStage.setTitle("Add To Cart");
				addStage.resizableProperty().setValue(Boolean.FALSE);
				addStage.setMinWidth(600);
				addStage.setMaxWidth(600);
				addStage.setMinHeight(400);
				addStage.setMaxHeight(400);

				class UserData {
					public Product selectedProduct;
					public List<Product> allProducts;
				}

				UserData data = new UserData();
				data.selectedProduct = p;
				data.allProducts = products;
				addStage.setUserData(data);

				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("AddToCart.fxml"));
					VBox root = (VBox) loader.load();

					AddToCartController atcc = loader.getController();
					atcc.barcodeLabel.setText(p.getBarcode());
					atcc.nameLabel.setText(p.getName());
					atcc.categoryLabel.setText(p.getCategory().getName());
					atcc.priceLabel.setText(Double.toString(p.getSellingPrice()));
					atcc.vendorLabel.setText(p.getVendor().getName());
					atcc.quantityLabel.setText(Integer.toString(p.getQuantity()));

					Scene scene = new Scene(root, 600, 400);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					addStage.setScene(scene);
					addStage.showAndWait();

				} catch (Exception e) {
					e.printStackTrace();
				}
				calculateTotal();
			}
		});
	}

	public void handleSearchEnter(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			this.addButtonClick();
		}
	}

	public void removeButtonClick() {
		if (saleTable.getSelectionModel().getSelectedItems().size() > 0) {
			sales.remove(saleTable.getSelectionModel().getSelectedItem());
		}
		calculateTotal();
	}

	public void makeOrderButtonClick() {

	}

	public void calculateTotal() {
		double priceCounter = 0;

		for (Order o : sales) {
			priceCounter += o.getTotalPrice();
		}

		subtotalLabel.setText(Double.toString(priceCounter));
		totalLabel.setText(Double.toString(priceCounter + 10));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		products = new ArrayList<>();
		products.add(new Product("1", "Coke", new Category("1", "Beverage"), 20d, 30d, new Vendor("1", "Coca-Cola"), 18,
				300, "204458274890"));
		products.add(new Product("2", "Apple", new Category("2", "Fruits"), 8d, 15d, new Vendor("2", "Fruitella"), 40,
				30, "480467434460"));

		sales = FXCollections.observableArrayList(
				new Order("1", products.get(0).getID(), products.get(0).getName(), 10),
				new Order("2", products.get(1).getID(), products.get(1).getName(), 10));

		for (Order o : sales) {
			for (Product p : products) {
				if (p.getID().equals(o.getProductId())) {
					o.setProductBarcode(p.getBarcode());
					o.setTotalPrice(o.getQuantitySold() * p.getSellingPrice());
					o.setVendor(p.getVendor());
				}
			}
		}

		barcodeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("ProductBarcode"));
		productColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("ProductName"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("QuantitySold"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("TotalPrice"));
		saleTable.setItems(sales);
		taxLabel.setText("10");
		calculateTotal();

	}
}
