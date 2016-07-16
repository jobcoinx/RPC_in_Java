package cn.cx.Services;

public interface ProvideService {
	// RPC
	// 客户端端调用服务端的方法，服务端方法执行之后返回结果给客户端，该返回的结果应该实现Serializable接口
	// 如：makeProduct返回的Product接口应该继承自Serializable接口。

	public int addInt(int a, int b);

	public String sayHello();

	public Product makeProduct();

}
