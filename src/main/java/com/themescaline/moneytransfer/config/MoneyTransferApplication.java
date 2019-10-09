package com.themescaline.moneytransfer.config;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

@Slf4j
@ApplicationPath("/api")
public class MoneyTransferApplication extends ResourceConfig {

    public MoneyTransferApplication() {
        packages("com.themescaline.moneytransfer.web");
    }
}
