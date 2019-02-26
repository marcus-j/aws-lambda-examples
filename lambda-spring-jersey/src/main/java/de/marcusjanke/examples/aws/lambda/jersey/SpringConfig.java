package de.marcusjanke.examples.aws.lambda.jersey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.marcusjanke.examples.aws.lambda.jersey.services.DefaultMessageService;
import de.marcusjanke.examples.aws.lambda.jersey.services.MessageService;

/**
 * Setup any Spring services/beans here
 * 
 * @author marcus
 *
 */
@Configuration
@ComponentScan("de.marcusjanke.examples.aws.lambda.jersey")
public class SpringConfig {

	@Bean
	public MessageService messageService() {
		return new DefaultMessageService();
	}
}
