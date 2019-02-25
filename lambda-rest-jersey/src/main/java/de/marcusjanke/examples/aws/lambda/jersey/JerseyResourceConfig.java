package de.marcusjanke.examples.aws.lambda.jersey;

import javax.annotation.PostConstruct;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.marcusjanke.examples.aws.lambda.jersey.resources.DefaultResource;

/**
 * Jersey resource config, wire JAX-RS-resources here
 *
 * @author marcus
 */
@Component
public class JerseyResourceConfig extends ResourceConfig {

    @Autowired
    private DefaultResource defaultResource;

    @PostConstruct
    public void init() {
        register(defaultResource);
    }
}
