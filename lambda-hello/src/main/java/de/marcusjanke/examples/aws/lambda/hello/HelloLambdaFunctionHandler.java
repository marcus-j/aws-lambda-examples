package de.marcusjanke.examples.aws.lambda.hello;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Hello world Lambda function handler
 * 
 * @author marcus
 *
 */
public class HelloLambdaFunctionHandler implements RequestHandler<String, String> {
	
	/**
	 * handle request for input string (a name) and returns "Hello, <input>!" result
	 */
    @Override
    public String handleRequest(String input, Context context) {
    	String output = "Hello, " + input + "!";
    	return output;
    }
}