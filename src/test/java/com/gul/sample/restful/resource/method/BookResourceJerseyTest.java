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

	@Override
	protected Application configure() {
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
