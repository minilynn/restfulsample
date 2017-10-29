package com.gul.sample.restful.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;

import com.gul.sample.restful.server.MyServer;

public class MyClient {
	public static void getRequest(WebTarget target) {
		String rs = target.path("hello").request().get(String.class);
		System.out.println("Response Text: " + rs);

		// String str =
		// resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).get(String.class);
		// System.out.println("String:" + str);
		//
		// URI uri =
		// UriBuilder.fromUri("http://127.0.0.1/service/sean").port(10000).queryParam("desc",
		// "description")
		// .build();
		// resource = client.resource(uri);
		//
		// // header方法可用来添加HTTP头
		// ClientResponse response = resource.header("auth",
		// "123456").accept(MediaType.TEXT_PLAIN)
		// .type(MediaType.TEXT_PLAIN).get(ClientResponse.class);
		// // 将HTTP响应打印出来
		// System.out.println("****** HTTP response ******");
		// StringBuilder strBuilder = new StringBuilder();
		// strBuilder.append("HTTP/1.1 ");
		// strBuilder.append(response.getStatus() + " ");
		// strBuilder.append(response.getStatusInfo() + "[\\r\\n]");
		// System.out.println(strBuilder.toString());
		// MultivaluedMap<String, String> headers = response.getHeaders();
		// Iterator<String> iterator = headers.keySet().iterator();
		// while (iterator.hasNext()) {
		// String headName = iterator.next();
		// System.out.println(headName + ":" + headers.get(headName) + "[\\r\\n]");
		// }
		// System.out.println("[\\r\\n]");
		// System.out.println(response.getEntity(String.class) + "[\\r\\n]");
	}

	public static void postRequest(WebTarget target) {
		Form form = new Form();
		form.param("parm1", "aaaaa");
		form.param("parm2", "11111");

		String rs = target.path("book/postreq").request().post(Entity.form(form), String.class);
		System.out.println("Response Text: " + rs);
	}

	public static void main(String[] args) throws Exception {
		Client client = null;
		try {
			// 首先启动服务器
			MyServer.startServer();
			client = ClientBuilder.newClient();
			WebTarget target = client.target("http://127.0.0.1:8080/restfulsample");

			// GET请求
			// getRequest(target);

			// POST请求
			postRequest(target);
		} finally {
			// 停止服务器
			MyServer.stopServer();
			if (client != null) {
				client.close();
			}
		}
	}
}
