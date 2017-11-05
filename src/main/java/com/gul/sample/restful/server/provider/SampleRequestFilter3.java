/**
 * 
 */
package com.gul.sample.restful.server.provider;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lynn
 *
 */
@Priority(Priorities.AUTHENTICATION)
public class SampleRequestFilter3 implements ContainerRequestFilter {
	private final static Logger log = LoggerFactory.getLogger(SampleRequestFilter3.class);

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String method = requestContext.getMethod();
		log.debug("DynamicFeature注册，当前请求方法为：" + method);
	}
}
