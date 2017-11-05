/**
 * 
 */
package com.gul.sample.restful.client.provider;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lynn
 *
 */
@Provider
public class SampleRequestFilter implements ClientRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(SampleRequestFilter.class);

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		if (!requestContext.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
			log.debug("在Header中没有\"Authorization\"值");
		} else {
			log.debug("在Header中有\"Authorization\"值");
		}

		log.debug("当前请求URI：" + requestContext.getUri().toString());
	}
}
