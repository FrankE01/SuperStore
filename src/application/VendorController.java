package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VendorController implements Initializable {

	public ObservableList<Vendor> names;
	public ListView<String> vendorList;
	public ListView<String> productsList;
	public TextField searchBox;
	public Label totalVendors;

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
		addStage.setTitle("Add Category");
		addStage.resizableProperty().setValue(Boolean.FALSE);
		addStage.setMinWidth(600);
		addStage.setMaxWidth(600);
		addStage.setMinHeight(400);
		addStage.setMaxHeight(400);
		
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("AddVendor.fxml"));
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			addStage.setScene(scene);
			addStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void edit() {
		if(vendorList.getSelectionModel().getSelectedItems().size() > 0) {
				
			Stage addStage = new Stage();
			addStage.initModality(Modality.APPLICATION_MODAL);
			addStage.setTitle("Edit Category");
			addStage.resizableProperty().setValue(Boolean.FALSE);
			addStage.setMinWidth(600);
			addStage.setMaxWidth(600);
			addStage.setMinHeight(400);
			addStage.setMaxHeight(400);
			
			try {
				VBox root = (VBox) FXMLLoader.load(getClass().getResource("EditVendor.fxml"));
				Scene scene = new Scene(root, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				addStage.setScene(scene);
				addStage.showAndWait();
	
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	}
	
	public void getVendor(MouseEvent event) {
		if(event.isPrimaryButtonDown()) {
			String vendorName = vendorList.getSelectionModel().getSelectedItem();
			List<String> products = new LinkedList<>();
			
			names.forEach(ve -> {
				if(ve.getName().equals(vendorName)) {
					ve.getProductsOffered().forEach(p->products.add(p.getName()));
				}
			});
			
			productsList.getItems().setAll(products);
		}
	}
	
	public void findButtonClick() {
		names.forEach(ve -> {
			if(ve.getName().toLowerCase().contains(searchBox.getText().toLowerCase())) {
				vendorList.getSelectionModel().select(ve.getName());
			}
		});
	}
	
	public void handleSearchEnter(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			this.findButtonClick();
		}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		names = FXCollections.observableArrayList(new Vendor("1", "BelAqua"),
				new Vendor("2", "Coca-Cola"), new Vendor("3", "HP"));

		Product p = new Product("1", "Coke", new Category("1", "Beverage"), 20d, 30d, new Vendor("1", "Coca-Cola"), 18,
				300, "204458274890");
		Vector<Product> list = new Vector<>();
		for (int i = 0; i < 5; i++) {
			list.add(p);
		}

		List<String> nameList = new LinkedList<>();
		names.forEach(v -> {
			v.setProductsOffered(list);
			nameList.add(v.getName());
		});
		vendorList.getItems().setAll(nameList);
		if(names.size()==1) {
			totalVendors.setText("1 vendor");
		} else {			
			totalVendors.setText(names.size()+" vendors");
		}
	}
}
