/**
 * 
 */
package com.gul.sample.restful.server;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

import com.gul.sample.restful.server.method.BookResourceImpl;

/**
 *
 * @author Lynn
 *
 */
public class APIApplication extends ResourceConfig {
	public APIApplication() {
		// 加载Resource
		register(MyResource.class);
		register(BookResourceImpl.class);

		// 注册数据转换器
		register(JacksonJsonProvider.class);

		// Logging.
		register(LoggingFilter.class);
	}
}
