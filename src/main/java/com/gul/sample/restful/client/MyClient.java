package com.gul.sample.restful.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly.connector.GrizzlyConnectorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gul.sample.restful.client.provider.SampleRequestFilter;
import com.gul.sample.restful.resource.domain.UserInfo;
import com.gul.sample.restful.server.MyServer;

public class MyClient {
	private final static Logger log = LoggerFactory.getLogger(MyClient.class);
	private static Client client;

	public static void getRequest(WebTarget target) {
		String rs = target.path("hello").request().get(String.class);
		System.out.println("Response Text: " + rs);
	}

	public static void readResponse(WebTarget target) throws Exception {
		Response resp = target.path("hello").request(MediaType.TEXT_PLAIN).get();

		// 将HTTP响应打印出来
		System.out.println("****** HTTP response ******");
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("HTTP/1.1 ");
		strBuilder.append(resp.getStatus() + " ");
		strBuilder.append(resp.getStatusInfo() + "[\\r\\n]");
		System.out.println(strBuilder.toString());
		MultivaluedMap<String, String> headers = resp.getStringHeaders();
		Iterator<String> iterator = headers.keySet().iterator();
		while (iterator.hasNext()) {
			String headName = iterator.next();
			System.out.println(headName + ":" + headers.get(headName) + "[\\r\\n]");
		}
		System.out.println("[\\r\\n]");
		InputStream is = (InputStream) resp.getEntity();
		String len = headers.getFirst("Content-Length");
		byte[] content = new byte[Integer.parseInt(len)];
		int contentLength = is.read(content);
		System.out.println("Content Length: " + contentLength + ", Content: " + new String(content) + "[\\r\\n]");
	}

	public static void postRequest(WebTarget target) {
		Form form = new Form();
		form.param("parm1", "aaaaa");
		form.param("parm2", "11111");

		String rs = target.path("book/postreq").request().post(Entity.form(form), String.class);
		System.out.println("Response Text: " + rs);
	}

	public static void useProvider(WebTarget target) {
		UserInfo rs = target.path("hello").path("0001").request(MediaType.APPLICATION_JSON).get(UserInfo.class);
		System.out.println("Response Text: " + JSON.toJSONString(rs));
	}

	public static void useDynamicFeature(WebTarget target) {
		UserInfo rs = target.path("hello").path("df").request(MediaType.APPLICATION_JSON).get(UserInfo.class);
		System.out.println("Response Text: " + JSON.toJSONString(rs));
	}

	/**
	 * 使用默认的HttpUrlConnector连接器
	 */
	private static void useDefaultConnector() {
		log.debug("使用默认的HttpUrlConnector连接器创建Client对象");
		client = ClientBuilder.newClient();
	}

	/**
	 * 使用Grizzly连接器
	 */
	private static void useGrizzlyConnector() {
		log.debug("使用Grizzly连接器创建Client对象");
		GrizzlyConnectorProvider connector = new GrizzlyConnectorProvider();
		ClientConfig config = new ClientConfig();
		config.connectorProvider(connector);
		client = ClientBuilder.newClient(config);
	}

	/**
	 * 使用Apache连接器
	 */
	private static void useApacheConnector() {
		log.debug("使用Apache连接器创建Client对象");
		ApacheConnectorProvider connector = new ApacheConnectorProvider();
		ClientConfig config = new ClientConfig();
		config.connectorProvider(connector);

		// 设置连接超时、网络超时和请求超时
		RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(2000).setSocketTimeout(2000)
				.setConnectionRequestTimeout(200).build();
		config.property(ApacheClientProperties.REQUEST_CONFIG, reqConfig);

		// 直接在ClientConfig中设置参数
		// config.property(ClientProperties.PROXY_URI, "http://128.196.1.11");
		// config.property(ClientProperties.PROXY_USERNAME, "myproxy");
		// config.property(ClientProperties.PROXY_PASSWORD, "password");
		// config.property(ClientProperties.CONNECT_TIMEOUT, 2000);
		// config.property(ClientProperties.READ_TIMEOUT, 2000);

		client = ClientBuilder.newClient(config);
	}

	/**
	 * 使用连接池
	 */
	private static void useConnectorPool() {
		log.debug("使用连接池创建Client对象");
		ClientConfig config = new ClientConfig();
		createHttpClient(config, 2000, 1000);
		client = ClientBuilder.newClient(config);
	}

	/**
	 * 创建HttpClient对象
	 */
	public static void createHttpClient(ClientConfig config, int maxTotal, int maxPerRoute) {
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", plainsf).register("https", sslsf).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
		// 将最大连接数增加
		cm.setMaxTotal(maxTotal);
		// 将每个路由基础的连接增加
		cm.setDefaultMaxPerRoute(maxPerRoute);

		// 请求重试处理
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 5) {// 如果已经重试了5次，就放弃
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					return false;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					return false;
				}
				if (exception instanceof SSLException) {// SSL握手异常
					return false;
				}

				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};

		config.property(ApacheClientProperties.CONNECTION_MANAGER, cm).property(ApacheClientProperties.RETRY_HANDLER,
				httpRequestRetryHandler);
	}

	private static WebTarget initEnv(int connType) throws Exception {
		// 首先启动服务器
		MyServer.startServer();
		switch (connType) {
		case 1:
			// 使用默认连接器创建Client对象
			useDefaultConnector();
			break;
		case 2:
			// 使用Grizzly连接器创建Client对象
			useGrizzlyConnector();
			break;
		case 3:
			// 使用Apache连接器创建Client对象
			useApacheConnector();
			break;
		case 4:
			// 使用连接池创建Client对象
			useConnectorPool();
			break;
		default:
			// 使用默认连接器创建Client对象
			useDefaultConnector();
		}

		String ip = InetAddress.getLocalHost().getHostAddress();
		// 注册到Client对象中
		// client.register(SampleRequestFilter.class);
		WebTarget target = client.target("http://" + ip + ":8080/restfulsample");
		// 注册到WebTarget对象中
		target.register(SampleRequestFilter.class);

		return target;
	}

	public static void main(String[] args) throws Exception {
		try {
			// 连接类型，1-默认HttpUrlConnection；2-Grizzly；3-Apache；4-Apache Connection Pool
			int connType = 4;
			WebTarget target = initEnv(connType);

			// GET请求
			// getRequest(target);

			// Get请求，Response返回对象
			// readResponse(target);

			// POST请求
			// postRequest(target);

			// NameBinding
			// useProvider(target);

			// DynamicFeature
			useDynamicFeature(target);
		} finally {
			// 停止服务器
			MyServer.stopServer();
			if (client != null) {
				client.close();
			}
		}
	}
}
