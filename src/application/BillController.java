package application;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BillController implements Initializable {
	public ObservableList<Bill> bills;
	public List<Product> products;
	public TableView<Bill> billTable;
	public TableColumn<Bill, String> idColumn;
	public TableColumn<Bill, String> quantityColumn;
	public TableColumn<Bill, String> dateColumn;
	public Label totalItems;
	public Label totalBills;
	public ListView<String> productList;
	public ListView<Integer> quantityList;
	public Label productName;
	public Label categoryLabel;
	public Label quantityLabel;
	public Label costLabel;
	public Label sellingLabel;
	public ImageView barcode;

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

	public void getRow(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			Bill b = billTable.getSelectionModel().getSelectedItem();
			List<String> productNames = new ArrayList<>();
			List<Integer> productQuantities = new ArrayList<>();
			for (Order o : b.getProductsPurchased()) {
				productNames.add(o.getProductName());
				productQuantities.add(o.getQuantitySold());
			}
			productList.getItems().setAll(productNames);
			quantityList.getItems().setAll(productQuantities);

		}
	}

	public void productSelected(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			quantityList.getSelectionModel().select(productList.getSelectionModel().getSelectedIndex());
			for(Product p : products) {
				if(p.getName().equals(productList.getSelectionModel().getSelectedItem())) {
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
		}
	}

	public void quantitySelected(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			productList.getSelectionModel().select(quantityList.getSelectionModel().getSelectedIndex());
			productSelected(event);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		try {
			bills = FXCollections.observableArrayList(new Bill("1", (Date) format1.parse("12/08/2022 9:12:22 AM"), 80),
					new Bill("2", (Date) format1.parse("10/08/2022 9:12:22 AM"), 800),
					new Bill("3", (Date) format1.parse("06/07/2022 4:32:02 PM"), 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Order o = new Order("1", "1", "Coke", 2);
		Vector<Order> orders = new Vector<>();
		for (int i = 0; i < 5; i++) {
			orders.add(o);
		}

		for (Bill b : bills) {
			b.setProductsPurchased(orders);
			b.setTotalQuantity(b.getProductsPurchased().size());
		}

		SimpleDateFormat format2 = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a");
		idColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("ID"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("TotalQuantity"));
		dateColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(format2.format(cellData.getValue().getDate())));
		billTable.setItems(bills);

		products = new ArrayList<>();
		products.add(new Product("1", "Coke", new Category("1", "Beverage"), 20d, 30d, new Vendor("1", "Coca-Cola"), 18,
				300, "204458274890"));
		products.add(new Product("2", "Apple", new Category("2", "Fruits"), 8d, 15d, new Vendor("2", "Fruitella"), 40,
				30, "480467434460"));

		int itemCounter = 0;

		for (Bill b : bills) {
			for (Order ord : b.getProductsPurchased()) {
				itemCounter++;
			}
		}

		if (itemCounter == 1) {
			totalItems.setText("1 item");
		} else {
			totalItems.setText(itemCounter + " items");
		}
		if (bills.size() == 1) {
			totalBills.setText("1 bills");
		} else {
			totalBills.setText(bills.size() + " bills");
		}

	}

}
