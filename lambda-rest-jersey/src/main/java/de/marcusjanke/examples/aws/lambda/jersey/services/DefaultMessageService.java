package de.marcusjanke.examples.aws.lambda.jersey.services;

/**
 * default message service
 * 
 * @author marcus
 *
 */
public class DefaultMessageService implements MessageService {

	@Override
	public String getMessage() {
		return "Default response";
	}
}
