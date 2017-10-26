/**
 * 
 */
package com.gul.sample.restful.resource.method;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author Lynn
 *
 */
public interface BookResource {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getAuthor();
}
