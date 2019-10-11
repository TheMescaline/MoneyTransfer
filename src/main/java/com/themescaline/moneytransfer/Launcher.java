package com.themescaline.moneytransfer;

import com.themescaline.moneytransfer.config.HibernateSessionFactory;
import com.themescaline.moneytransfer.config.JerseyConfiguration;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Application launcher class
 *
 * @author lex.korovin@gmail.com
 */
public class Launcher {
    private static final String JERSEY_SERVLET_NAME = "jersey-container-servlet";

    public static void main(String[] args) throws Exception {
        String configFilePath = null;
        if (args.length > 0) {
            configFilePath = args[0];
        }
        HibernateSessionFactory.init(configFilePath);
        new Launcher().start();
    }

    private void start() throws Exception {
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "8080";
        }

        String contextPath = "";
        String appBase = ".";

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        tomcat.getHost().setAppBase(appBase);

        Context context = tomcat.addContext(contextPath, appBase);
        Tomcat.addServlet(context, JERSEY_SERVLET_NAME,
                new ServletContainer(new JerseyConfiguration()));
        context.addServletMappingDecoded("/api/*", JERSEY_SERVLET_NAME);

        tomcat.start();
        tomcat.getServer().await();
    }
}
