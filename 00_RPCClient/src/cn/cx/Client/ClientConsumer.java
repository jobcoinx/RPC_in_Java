package cn.cx.Client;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.Socket;

import cn.cx.Services.Product;
import cn.cx.Services.ProvideService;

public class ClientConsumer {
	private Socket socket;

	public static void main(String[] args) {
		ClientConsumer consumer = new ClientConsumer();
		String ip = "223.3.82.179";
		int port = 3333;

		ProvideService service = consumer.getProxy(ProvideService.class, ip, port);
		System.out.println("Got service: " + service.getClass().getName());
		while (true) {
			System.out.println(service.addInt(1, 4));
			System.out.println(service.sayHello() + " Client received!");
			System.out.println("##");
			Product product = service.makeProduct();
			System.out.println(product.getProductID());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<?> service, String ip, int port) {
		try {
			socket = new Socket(ip, port);
			System.out.println("Connected");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
				new ClientInvocationHandler(socket));
	}

}
