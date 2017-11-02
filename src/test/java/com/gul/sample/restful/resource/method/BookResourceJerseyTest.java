/**
 * 
 */
package com.gul.sample.restful.resource.method;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gul.sample.restful.resource.method.domain.BookInfo;

/**
 * 
 * @author Lynn
 *
 */
public class BookResourceJerseyTest extends JerseyTest {
	private final static String BASE_URI = "book/";
	private final static Logger log = LoggerFactory.getLogger(BookResourceJerseyTest.class);

	private static final String CONTAINER_GRIZZLY = "org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory";
	private static final String CONTAINER_INMEMORY = "org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory";
	private static final String CONTAINER_JDKHTTP = "org.glassfish.jersey.test.jdkhttp.JdkHttpServerTestContainerFactory";
	private static final String CONTAINER_SIMPLE = "org.glassfish.jersey.test.simple.SimpleTestContainerFactory";

	@Override
	protected Application configure() {
		// 设置测试用容器
		set(TestProperties.CONTAINER_FACTORY, CONTAINER_SIMPLE);
		return new ResourceConfig(BookResourceImpl.class);
	}

	/**
	 * 测试相同资源的内容协商
	 */
	@Test
	public void testConCogWithPriority() {
		WebTarget target = target(BASE_URI + "id001");

		// 通过资源类型协商
		Builder builder = target.request(MediaType.APPLICATION_JSON);
		BookInfo book = builder.get(BookInfo.class);
		log.debug("BookInfo对象内容：" + JSON.toJSONString(book));
	}
}
