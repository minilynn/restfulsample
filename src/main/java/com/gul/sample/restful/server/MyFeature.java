/**
 * 
 */
package com.gul.sample.restful.server;

import javax.ws.rs.GET;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import com.gul.sample.restful.resource.MyResource;
import com.gul.sample.restful.server.provider.SampleRequestFilter3;

/**
 *
 * @author Lynn
 *
 */
public class MyFeature implements DynamicFeature {
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		boolean classMatched = MyResource.class.isAssignableFrom(resourceInfo.getResourceClass());
		boolean methodMatched = resourceInfo.getResourceMethod().getName().equals("getUserInfo2");
		boolean methodTypeMatched = resourceInfo.getResourceMethod().isAnnotationPresent(GET.class);
		if (classMatched && methodMatched && methodTypeMatched) {
			context.register(SampleRequestFilter3.class);
		}
	}
}
