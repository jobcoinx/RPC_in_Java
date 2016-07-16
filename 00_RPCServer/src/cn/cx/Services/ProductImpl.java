package cn.cx.Services;

public class ProductImpl implements Product {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6232035628083996804L;
	private String ProductID;

	public ProductImpl(String product) {
		this.ProductID = product + " " + serialVersionUID;
	}

	@Override
	public String getProductID() {
		return this.ProductID;
	}

	public void setProductID(String productID) {
		ProductID = productID;
	}

}
