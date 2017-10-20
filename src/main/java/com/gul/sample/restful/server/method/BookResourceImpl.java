/**
 * 
 */
package com.gul.sample.restful.server.method;

import javax.ws.rs.Path;

/**
 * 
 * @author Lynn
 *
 */
@Path("/book")
public class BookResourceImpl implements BookResource {

	/* (non-Javadoc)
	 * @see com.gul.sample.restful.server.method.BookResource#getWeight()
	 */
	@Override
	public String getAuthor() {
		return "Lynn";
	}

}
