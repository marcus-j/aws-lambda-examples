package de.marcusjanke.examples.aws.lambda.jersey.resources;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import de.marcusjanke.examples.aws.lambda.jersey.JerseyResourceConfig;
import de.marcusjanke.examples.aws.lambda.jersey.SpringConfig;

/**
 * test resources
 * 
 * @author marcus
 *
 */
public class DefaultResourceTest extends JerseyTest {

	private static JerseyResourceConfig resourceConfig;

	/**
	 * set up jersey resources
	 */
	@BeforeClass
	public static void setUpJerseyResourceConfig() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringConfig.class);
		context.refresh();
		resourceConfig = context.getBean(JerseyResourceConfig.class);
	}

	/**
	 * wire jersey config
	 */
	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		return resourceConfig;
	}

	/**
	 * test resource response
	 */
	@Test
	public void estCall() {
		String response = target("/default").request().get(String.class);
		assertThat(response).isNotNull().isEqualTo("Default response");
	}
}
