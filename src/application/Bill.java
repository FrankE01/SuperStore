package application;

import java.util.Date;
import java.util.Vector;

public class Bill {

	private String ID;
	private Vector<Order> ProductsPurchased;
	private Date Date;
	private int TotalCost;
	private int TotalQuantity;

	public Bill(String ID, Date Date, int TotalCost) {
		this.ID = ID;
		this.ProductsPurchased = new Vector<>();
		this.Date = Date;
		this.TotalCost = TotalCost;

	}
	
	public Bill(String ID, Vector<Order> ProductsPurchased, Date Date, int TotalCost) {
		this.ID = ID;
		this.ProductsPurchased = ProductsPurchased;
		this.Date = Date;
		this.TotalCost = TotalCost;

	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Vector<Order> getProductsPurchased() {
		return ProductsPurchased;
	}

	public void setProductsPurchased(Vector<Order> productsPurchased) {
		ProductsPurchased = productsPurchased;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public int getTotalCost() {
		return TotalCost;
	}

	public void setTotalCost(int totalCost) {
		TotalCost = totalCost;
	}

	public int getTotalQuantity() {
		return TotalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		TotalQuantity = totalQuantity;
	}

}
