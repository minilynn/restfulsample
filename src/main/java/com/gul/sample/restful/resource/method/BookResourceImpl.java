/**
 * 
 */
package com.gul.sample.restful.resource.method;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.dom.DOMSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.alibaba.fastjson.JSON;
import com.gul.sample.restful.resource.method.domain.BookInfo;

/**
 * 
 * @author Lynn
 *
 */
@Path("/book")
public class BookResourceImpl implements BookResource {
	private final static Logger log = LoggerFactory.getLogger(BookResourceImpl.class);

	private BookInfo bi;

	public BookResourceImpl() {
		bi = new BookInfo();
		bi.setTitle("XML详解");
		bi.setAuthor("Big Guy");
		bi.setType("Java");
	}

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
		return bi;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BookInfo getBookInfo2() {
		return bi;
	}

	@GET
	@Path("quality")
	@Produces({ "application/xml;qs=0.1", "applicaton/json;qs=0.2" })
	public BookInfo getBookInfo3() {
		return bi;
	}

	@POST
	@Path("/jaxb1")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public BookInfo getBookInfo3(JAXBElement<BookInfo> book) {
		log.debug("输入的Book信息：" + JSON.toJSONString(book.getValue()));
		return bi;
	}

	@POST
	@Path("/jaxb2")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public BookInfo getBookInfo4(BookInfo book) {
		log.debug("输入的Book信息：" + JSON.toJSONString(book));
		return bi;
	}

	@POST
	@Path("/doc")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Document getBookInfo5(Document book) {
		log.debug("输入的Book信息：" + book.toString());
		return book;
	}

	@POST
	@Path("/dom")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public DOMSource getBookInfo6(DOMSource book) {
		log.debug("输入的Book信息：" + book.toString());
		return book;
	}

	@POST
	@Path("/postreq")
	@Produces(MediaType.TEXT_PLAIN)
	public String getBookInfo7(@FormParam("parm1") final String parm1, @FormParam("parm2") final Integer parm2) {
		log.debug("输入的Form信息：" + parm1 + ", " + parm2);
		return "成功处理Form表单请求";
	}
}
