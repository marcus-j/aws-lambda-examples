package de.marcusjanke.examples.aws.lambda.jersey;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Jersey Lambda function handler, based on https://www.sebastianhesse.de/2017/08/27/use-jersey-spring-aws-lambda/
 * 
 * @author marcus
 *
 */
public class JerseyRequestHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
	
	private JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

	/**
	 * create JerseyRequestHandler and set up Spring context and Jersey config
	 */
	public JerseyRequestHandler() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfig.class);
        context.refresh();
        JerseyResourceConfig resourceConfig = context.getBean(JerseyResourceConfig.class);
		handler = JerseyLambdaContainerHandler.getAwsProxyHandler(resourceConfig);
	}

	/**
	 * handle requests from API Gateway proxy
	 */
	@Override
	public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
		return handler.proxy(awsProxyRequest, context);
	}
}