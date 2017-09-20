package de.marcusjanke.examples.aws.lambda.hello;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 * 
 * [created with AWS Toolkit for Eclipse]
 */
public class HelloLambdaFunctionHandlerTest {

    private static String input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = "world";
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("HelloLambda");
        return ctx;
    }

    /**
     * test hello world lambda function handler call
     */
    @Test
    public void testHelloLambdaFunctionHandler() {
        HelloLambdaFunctionHandler handler = new HelloLambdaFunctionHandler();
        Context ctx = createContext();
        String output = handler.handleRequest(input, ctx);
        Assert.assertEquals("Hello world!", output);
    }
}
