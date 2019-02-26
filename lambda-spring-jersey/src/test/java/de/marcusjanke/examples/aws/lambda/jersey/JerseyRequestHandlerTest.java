package de.marcusjanke.examples.aws.lambda.jersey;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * JerseyRequestHandler tests
 * 
 * @author marcus
 *
 */
public class JerseyRequestHandlerTest {

	private static AwsProxyRequest request;

	@BeforeClass
	public static void createInput() {
		request = new AwsProxyRequestBuilder("/default", "GET").build();
	}

	/**
	 * create Lambda context
	 * 
	 * @return Lambda context
	 */
	private Context createContext() {
		TestContext ctx = new TestContext();
		ctx.setFunctionName("JerseyLambda");
		return ctx;
	}

	/**
	 * test service call
	 */
	@Test
	public void testJerseyLambdaFunctionHandler() {
		JerseyRequestHandler handler = new JerseyRequestHandler();
		Context ctx = createContext();
		AwsProxyResponse output = handler.handleRequest(request, ctx);
		assertThat(output).isNotNull();
		assertThat(output.getBody()).isEqualTo("Default response");
		assertThat(output.getStatusCode()).isEqualTo(200);
	}
}
