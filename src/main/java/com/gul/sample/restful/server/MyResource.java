/**
 * 
 */
package com.gul.sample.restful.server;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lynn
 *
 */
@Path("/hello")
public class MyResource {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello Jersey 2";
	}
	
	@GET
	@Path("/user")
	public UserInfo getUserResource() {
		UserInfo user = new UserInfo();
		user.setId("000001");
		user.setName("Lynn");
		List<String> phones = new ArrayList<>();
		phones.add("010-11011011");
		phones.add("139999999");
		user.setTelephones(phones);

		return user;
	}
}
