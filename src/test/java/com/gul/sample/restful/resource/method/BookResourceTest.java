/**
 * 
 */
package com.gul.sample.restful.resource.method;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gul.sample.restful.resource.method.domain.BookInfo;
import com.gul.sample.restful.server.MyServer;

/**
 *
 * @author Lynn
 *
 */
public class BookResourceTest {
	private final static Logger log = LoggerFactory.getLogger(BookResourceTest.class);

	@BeforeClass
	public static void startServer() {
		try {
			MyServer.startServer();
		} catch (Exception e) {
			log.error("启动服务器异常", e);
		}
	}

	@AfterClass
	public static void stopServer() {
		MyServer.stopServer();
	}

	/**
	 * 测试相同资源的内容协商
	 */
	@Test
	public void testConCogWithPriority() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://127.0.0.1:8080/restfulsample");

		// 通过资源类型协商
		Builder builder = target.path("book").path("id001").request(MediaType.APPLICATION_JSON);
		Response resp = builder.get();
		BookInfo bi = target.path("book").path("id001").request().get(BookInfo.class);
		log.debug("获取相应的内容类型：" + JSON.toJSONString(resp));
		log.debug("BookInfo对象内容：" + JSON.toJSONString(bi));
		String entity = (String) resp.getEntity();
		// resp.close();
		// log.debug("获取相应的XML内容：" + entity);

		// 通过优先级进行资源协商
		resp = target.path("book").path("id001").request()
				.header("Accept", "application/xml;q=0.1,applicaton/json;q=0.2").get();
		String mediaType = resp.getMediaType().getType();
		log.debug("获取相应的内容类型：" + mediaType);
		entity = (String) resp.getEntity();
		resp.close();
		System.out.println("获取相应的JSon内容：" + entity);
	}
}
