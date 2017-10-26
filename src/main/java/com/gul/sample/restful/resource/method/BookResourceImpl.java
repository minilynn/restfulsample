/**
 * 
 */
package com.gul.sample.restful.resource.method;

import javax.inject.Qualifier;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.gul.sample.restful.resource.method.domain.BookInfo;

/**
 * 
 * @author Lynn
 *
 */
@Path("/book")
public class BookResourceImpl implements BookResource {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gul.sample.restful.server.method.BookResource#getWeight()
	 */
	@Override
	public String getAuthor() {
		return "Lynn";
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public BookInfo getBookInfo1() {
		BookInfo bi = new BookInfo();
		bi.setTitle("XML详解");
		bi.setAuthor("Big Guy");
		bi.setType("Java");
		return bi;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BookInfo getBookInfo2() {
		BookInfo bi = new BookInfo();
		bi.setTitle("Java编程思想");
		bi.setAuthor("Big Guy");
		bi.setType("Java");
		return bi;
	}
}
