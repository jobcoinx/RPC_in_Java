package cn.cx.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class ClientInvocationHandler implements InvocationHandler {
	private Socket socket;

	public ClientInvocationHandler(Socket sockettmp) {
		this.socket = sockettmp;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream objInput = new ObjectInputStream(socket.getInputStream());
		String methodName = method.getName();
		Class<?>[] paramTypes = method.getParameterTypes();
		objOutput.writeUTF(methodName);
		objOutput.writeObject(paramTypes);
		objOutput.writeObject(args);
		objOutput.flush();

		Object rObject = objInput.readObject();
		System.out.println("Client invoke()");
		System.out.println("Proxy: " + proxy.getClass().getName());
		System.out.println("Method: " + methodName);
		System.out.println("Result: " + rObject.toString());
		System.out.println();
		return rObject;
	}

}
