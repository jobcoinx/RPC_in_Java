package cn.cx.rpc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.cx.Services.ProvideService;
import cn.cx.Services.ProvideServiceImpl;

public class Server {
	private ServerSocket serverSocket;
	private boolean SERVERSTOP;
	private ExecutorService clientExecutors = Executors.newFixedThreadPool(23);

	public static void main(String[] args) {
		String IP = "223.3.82.179";
		int port = 3333;
		Server server = new Server();
		ProvideService provideService = new ProvideServiceImpl();
		server.publishService(provideService, IP, port);
	}

	public void publishService(Object object, String iP, int port) {
		if (iP == null || iP.length() == 0) {
			throw (new IllegalArgumentException("RPC Server ip is incorrect!"));
		}
		if (port <= 0 || port > 65535) {
			throw (new IllegalArgumentException("RPC Server port is incorrect!"));
		}
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(InetAddress.getByName(iP), port));
			System.out.println(object.getClass().getName() + " is published on the site: " + iP + ":" + port);
			System.out.println("RPC Server is up.");
			while (!SERVERSTOP) {
				Socket socket = serverSocket.accept();
				System.out.println("Got a connection from " + socket.getInetAddress() + ":" + socket.getPort());
				clientExecutors.submit(new ClientHandlerRunnable(socket, object));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setSERVERSTOP(boolean sERVERSTOP) {
		SERVERSTOP = sERVERSTOP;
	}
}
