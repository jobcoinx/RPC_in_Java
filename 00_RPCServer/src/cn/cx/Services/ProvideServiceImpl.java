package cn.cx.Services;

public class ProvideServiceImpl implements ProvideService {

	@Override
	public int addInt(int a, int b) {
		return a + b;
	}

	@Override
	public String sayHello() {
		return "SERVER RESPONSE";
	}

	@Override
	public Product makeProduct() {
		String product = "SERVER PRODUCT";
		return new ProductImpl(product);
	}

}
