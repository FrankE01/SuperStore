package application;

public class Order {
	private String ID;
	private String ProductId;
	private String ProductName;
	private int QuantitySold;
	private double TotalPrice;
	private Vendor Vendor;
	private String ProductBarcode;
	
	public Order(String iD, String productID, String productName, int quantitySold) {
		this.ID = iD;
		this.ProductId = productID;
		this.ProductName = productName;
		this.QuantitySold = quantitySold;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public int getQuantitySold() {
		return QuantitySold;
	}
	public void setQuantitySold(int quantitySold) {
		QuantitySold = quantitySold;
	}
	public double getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		TotalPrice = totalPrice;
	}
	public Vendor getVendor() {
		return Vendor;
	}
	public void setVendor(Vendor vendor) {
		Vendor = vendor;
	}

	public String getProductBarcode() {
		return ProductBarcode;
	}

	public void setProductBarcode(String productBarcode) {
		ProductBarcode = productBarcode;
	}

}
