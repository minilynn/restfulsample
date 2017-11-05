/**
 * 
 */
package com.gul.sample.restful.server;

import java.net.InetAddress;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gul.sample.restful.server.provider.SampleRequestFilter2;

/**
 *
 * @author Lynn
 *
 */
public class MyServer {
	private final static Logger log = LoggerFactory.getLogger(MyServer.class);
	public static final String BASE_URI = "http://{ip}:8080/restfulsample/";
	private static HttpServer server;

	public static void startServer() throws Exception {
		if (server != null && server.isStarted()) {
			return;
		}

		// 获取当前服务器IP，绑定HTTP服务
		String ip = InetAddress.getLocalHost().getHostAddress();
		log.debug("当前服务器IP地址：" + ip);

		// create a resource config that scans for JAX-RS resources and providers
		// in the specified package
		final ResourceConfig rc = new ResourceConfig().packages("com.gul.sample.restful.resource",
				"com.gul.sample.restful.resource.method", "com.gul.sample.restful.server.provider");
		rc.register(SampleRequestFilter2.class);
		rc.register(MyFeature.class);

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI.replace("{ip}", ip)), rc);

		// 启动HTTP服务
		server.start();
	}

	public static void stopServer() {
		if (server != null) {
			server.shutdown();
		}
	}

	public static void main(String[] args) {
		try {
			startServer();
		} catch (Exception e) {
			log.error("启动Web服务异常", e);
		}
	}
}
