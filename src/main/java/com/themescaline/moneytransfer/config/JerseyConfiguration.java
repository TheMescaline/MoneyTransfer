package com.themescaline.moneytransfer.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        packages("com.themescaline.moneytransfer");
        register(new JacksonFeature());
    }
}
