/**
 * 
 */
package com.gul.sample.restful.server;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

/**
 *
 * @author Lynn
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@NameBinding
public @interface MyProvider {
}
