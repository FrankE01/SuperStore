package application;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditProductController implements Initializable {
	public TextField nameBox;
	public ComboBox<String> categoryBox;
	public TextField costPriceBox;
	public TextField sellingPriceBox;
	public ComboBox<String> vendorBox;
	public TextField quantityBox;
	public TextField barcodeBox;
	public List<Category> categories;
	public List<Vendor> vendors;
	public Product selectedProduct;

	public void edit() {
		selectedProduct = (Product) nameBox.getUserData();

		try {
			Statement statement = DB_Connection.connection.createStatement();

			Category selectedCategory = null;
			Vendor selectedVendor = null;

			for (Category c : categories) {
				if (c.getName().equals(categoryBox.getSelectionModel().getSelectedItem())) {
					selectedCategory = c;
				}
			}
			for (Vendor v : vendors) {
				if (v.getName().equals(vendorBox.getSelectionModel().getSelectedItem())) {
					selectedVendor = v;
				}
			}

//				String query = String.format(
//						"INSERT INTO product(name, category, cost_price, selling_price, supplier, quantity, barcode) VALUES ('%s',%d,%.1f,%.1f,%d,%d,'%s')",
//						nameBox.getText(), selectedCategory.getID(), Double.parseDouble(costPriceBox.getText()),
//						Double.parseDouble(sellingPriceBox.getText()), selectedVendor.getID(),
//						Integer.parseInt(quantityBox.getText()), barcodeBox.getText());

			String query = String.format(
					"UPDATE product SET name = '%s', category = %d, cost_price = %.1f, selling_price = %.1f, supplier = %d, quantity = %d, barcode = '%s' WHERE (id = %d);",
					nameBox.getText(), selectedCategory.getID(), Double.parseDouble(costPriceBox.getText()),
					Double.parseDouble(sellingPriceBox.getText()), selectedVendor.getID(),
					Integer.parseInt(quantityBox.getText()), barcodeBox.getText(), selectedProduct.getID());
			statement.executeUpdate(query);
			Stage editStage = (Stage) nameBox.getScene().getWindow();
			editStage.close();

		} catch (SQLIntegrityConstraintViolationException e) {
			ErrorMessage.display("Duplicate Product", "A product with the specified name already exists");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			ErrorMessage.display("Invalid Fields", "Please make sure all your fields are of the correct type");
		}
	}
	
	public void delete() {
		selectedProduct = (Product) nameBox.getUserData();
		
		try {
			Statement statement = DB_Connection.connection.createStatement();
			String query = String.format("DELETE FROM product WHERE (id = %d);", selectedProduct.getID());
			statement.executeUpdate(query);
			Stage editStage = (Stage) nameBox.getScene().getWindow();
			editStage.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

//		Statement statement;
//		try {
//			statement = DB_Connection.connection.createStatement();
//			ResultSet result = statement.executeQuery("SELECT * FROM category");
//
//			categories = new ArrayList<>();
//			while (result.next()) {
//				categories.add(new Category(result.getInt("id"), result.getString("name")));
//			}
//
//			result = statement.executeQuery("SELECT * FROM supplier");
//			vendors = new ArrayList<>();
//			while (result.next()) {
//				vendors.add(new Vendor(result.getInt("id"), result.getString("name"), result.getString("phone"),
//						result.getString("email")));
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
}
