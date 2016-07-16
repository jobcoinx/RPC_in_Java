package cn.cx.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class ClientHandlerRunnable implements Runnable {

	private Socket sock;
	private Object service;
	private boolean STOPCLIENTHANDLER = false;

	public ClientHandlerRunnable(Socket socket, Object object) {
		this.sock = socket;
		this.service = object;
	}

	@Override
	public void run() {
		while (!STOPCLIENTHANDLER) {
			try {
				ObjectInputStream objinput = new ObjectInputStream(sock.getInputStream());
				ObjectOutputStream objOutput = new ObjectOutputStream(sock.getOutputStream());
				try {
					System.out.println("Handling client request");
					String methodName = objinput.readUTF();
					Class<?>[] paramTypes = (Class<?>[]) objinput.readObject();
					Object[] args = (Object[]) objinput.readObject();
					Method method = service.getClass().getMethod(methodName, paramTypes);
					Object result = method.invoke(service, args);
					objOutput.writeObject(result);
					objOutput.flush();
					System.out.println("Object is: " + service.getClass().getName());
					System.out.println("Method is: " + methodName);
					System.out.println("The result is: " + result.toString());
					System.out.println();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.err.println("ERROR: Stream closed, one of client connection OUT!");
				try {
					sock.close();
					System.err.println("Socket which is client connected was closed");
					this.STOPCLIENTHANDLER = true;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
