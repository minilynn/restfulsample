/**
 * 
 */
package com.gul.sample.restful.server.provider;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lynn
 *
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SampleRequestFilter implements ContainerRequestFilter {
	private final static Logger log = LoggerFactory.getLogger(SampleRequestFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String method = requestContext.getMethod();
		log.debug("当前请求方法为：" + method);
	}
}
