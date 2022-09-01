package application;

import java.util.Vector;

public class Vendor {

	private String ID;
	private String Name;
	private Vector<Product> ProductsOffered;
	
	public Vendor() {
		
	}

	public Vendor(String ID, String Name) {
		this.ID = ID;
		this.Name = Name;
		this.ProductsOffered = new Vector<Product>();
	}
	
	public Vendor(String ID, String Name, Vector<Product> ProductsOffered) {
		this.ID = ID;
		this.Name = Name;
		this.ProductsOffered = ProductsOffered;
	}

	public Vector<Product> getProductsOffered() {
		return ProductsOffered;
	}

	public void setProductsOffered(Vector<Product> productsOffered) {
		ProductsOffered = productsOffered;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

}
