package de.marcusjanke.examples.aws.lambda.jersey.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.marcusjanke.examples.aws.lambda.jersey.services.MessageService;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * Sample resources
 * 
 * @author u253760
 *
 */
@Path("/default")
@Service
public class DefaultResource {
	
	@Autowired
	private MessageService messageService;

    @GET
    @Produces(TEXT_PLAIN)
    public Response getDefaultResponse() {
        return Response.ok(messageService.getMessage()).build();
    }
}
