package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.kordamp.ikonli.javafx.FontIcon;

import application.dataStructures.ArrayStack;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductsController implements Initializable {
	public Circle productsCircle;
	public FontIcon productsIcon;
	public Circle vendorsCircle;
	public FontIcon vendorssIcon;
	public Circle billsCircle;
	public FontIcon billsIcon;
	public Circle ordersCircle;
	public FontIcon orderssIcon;
	public TableView<Product> productTable;
	public TableColumn<Product, String> idColumn;
	public TableColumn<Product, String> productColumn;
	public TableColumn<Product, String> categoryColumn;
	public TableColumn<Product, String> priceColumn;
	public TableColumn<Product, String> quantityColumn;
	public Label totalProducts;
	public Label totalItems;
	public Label productName;
	public Label categoryLabel;
	public Label quantityLabel;
	public Label costLabel;
	public Label sellingLabel;
	public ImageView barcode;
	public TextField searchBox;
	public ObservableList<Product> data;
	public ArrayStack<Product> products;

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

	public void toOrders(MouseEvent event) {
		Stage saleStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Sales.fxml"));
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			saleStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void add() {
		Stage addStage = new Stage();
		addStage.initModality(Modality.APPLICATION_MODAL);
		addStage.setTitle("Add Product");
		addStage.resizableProperty().setValue(Boolean.FALSE);
		addStage.setMinWidth(600);
		addStage.setMaxWidth(600);
		addStage.setMinHeight(400);
		addStage.setMaxHeight(400);

		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			addStage.setScene(scene);
			addStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit() {
		if (productTable.getSelectionModel().getSelectedItems().size() > 0) {

			Stage addStage = new Stage();
			addStage.initModality(Modality.APPLICATION_MODAL);
			addStage.setTitle("Edit Product");
			addStage.resizableProperty().setValue(Boolean.FALSE);
			addStage.setMinWidth(600);
			addStage.setMaxWidth(600);
			addStage.setMinHeight(400);
			addStage.setMaxHeight(400);

			try {
				VBox root = (VBox) FXMLLoader.load(getClass().getResource("EditProduct.fxml"));
				Scene scene = new Scene(root, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				addStage.setScene(scene);
				addStage.showAndWait();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void getRow(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			Product p = productTable.getSelectionModel().getSelectedItem();
			System.out.println(productTable.getSelectionModel().getSelectedItem().getName());
			productName.setText(p.getName());
			categoryLabel.setText(p.getCategory().getName());
			quantityLabel.setText(Integer.toString(p.getQuantity()));
			costLabel.setText(Double.toString(p.getBuyingPrice()));
			sellingLabel.setText(Double.toString(p.getSellingPrice()));
			try {
				barcode.setImage(BarcodeGenerator.generate(p.getBarcode()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void findButtonClick() {
		data.forEach(p -> {
			if (p.getName().toLowerCase().contains(searchBox.getText().toLowerCase())) {
				productTable.getSelectionModel().select(p);
			}
		});
	}

	public void handleSearchEnter(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			this.findButtonClick();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			Statement statement = DB_Connection.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM category");
			List<Category> categories = new ArrayList<>();

			while (result.next()) {
				categories.add(new Category(result.getString("id"), result.getString("name")));
			}

			result = statement.executeQuery("SELECT * FROM supplier");
			List<Vendor> vendors = new ArrayList<>();

			while (result.next()) {
				vendors.add(new Vendor(result.getString("id"), result.getString("name"), result.getString("phone"),
						result.getString("email")));
			}

			result = statement.executeQuery("SELECT * FROM product");
			while (result.next()) {
				if (result.getInt("category") == 1 || result.getInt("category") == 2 || result.getInt("category") == 3
						|| result.getInt("category") == 4) {

					String vendorID = result.getString("supplier");
					for (Vendor v : vendors) {
						if (v.getID().equals(vendorID)) {
							products.push(new Product(result.getString("id"), result.getString("name"),
									categories.get(result.getInt("category")), (double) result.getInt("cost_price"),
									(double) result.getInt("selling_price"), v, result.getInt("quantity"),
									result.getString("barcode")));
						}
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		data = FXCollections.observableArrayList(
				new Product("1", "Coke", new Category("1", "Beverage"), 20d, 30d, new Vendor("1", "Coca-Cola"), 18,
						"204458274890"),
				new Product("2", "Apple", new Category("2", "Fruits"), 8d, 15d, new Vendor("2", "Fruitella"), 40,
						"480467434460"));

		idColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ID"));
		productColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
		categoryColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getName()));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("SellingPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("Quantity"));
		productTable.setItems(data);

		int totalProductsCounter = 0;
		int totalItemsCounter = 0;
		for (Product p : data) {
			totalProductsCounter++;
			totalItemsCounter += p.getQuantity();
		}
		if (totalProductsCounter == 1) {
			totalProducts.setText("1 product");
		} else {
			totalProducts.setText(totalProductsCounter + " produts");
		}
		if (totalItemsCounter == 1) {
			totalItems.setText("1 item");
		} else {
			totalItems.setText(totalItemsCounter + " items");
		}

	}

}
