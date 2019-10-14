package com.themescaline.moneytransfer;

import com.themescaline.moneytransfer.config.HibernateSessionFactoryHelper;
import com.themescaline.moneytransfer.config.JerseyConfiguration;
import lombok.experimental.UtilityClass;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Application launcher class
 *
 * @author lex.korovin@gmail.com
 */
@UtilityClass
public class Launcher {
    private static final String JERSEY_SERVLET_NAME = "jersey-container-servlet";
    private static final String DEFAULT_PORT = "8080";
    private static final String PORT = "PORT";
    private static final String CONTEXT_PATH = "";
    private static final String APP_BASE = ".";

    public void main(String[] args) throws LifecycleException {
        String configFilePath = null;
        if (args.length > 0) {
            configFilePath = args[0];
        }
        HibernateSessionFactoryHelper.init(configFilePath);
        start();
    }

    private void start() throws LifecycleException {
        String port = System.getenv(PORT);
        if (port == null || port.isEmpty()) {
            port = DEFAULT_PORT;
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        tomcat.getHost().setAppBase(APP_BASE);

        Context context = tomcat.addContext(CONTEXT_PATH, APP_BASE);
        Tomcat.addServlet(context, JERSEY_SERVLET_NAME,
                new ServletContainer(new JerseyConfiguration()));
        context.addServletMappingDecoded("/api/*", JERSEY_SERVLET_NAME);

        tomcat.start();
        tomcat.getServer().await();
    }
}
