package de.marcusjanke.examples.aws.lambda.jersey.services;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

/**
 * Test default message service
 * 
 * @author marcus
 *
 */
public class DefaultMessageServiceTest {

	/**
	 * test getMessage value
	 */
	@Test
	public void testDefaultMessage() {
		MessageService service = new DefaultMessageService();
		assertThat(service.getMessage()).isNotNull().isEqualTo("Default response");
	}
	
}
