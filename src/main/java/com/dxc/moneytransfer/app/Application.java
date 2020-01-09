package com.dxc.moneytransfer.app;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import com.dxc.moneytransfer.app.dao.impl.DBConnection;
import com.dxc.moneytransfer.app.rest.service.AmountTransferService;
import com.dxc.moneytransfer.app.utils.Utils;

public class Application {

	private static Logger log = Logger.getLogger(Application.class);
	
	public static void main(String[] args) throws Exception {		
		log.info("Load test data for testing .....");
		DBConnection.loadTestData();
		log.info("Load test data for testing done .....");
		// start Jetty
		startServer();
	}
	
	/*
	 * startServer() - function helps to start of Jetty with Jersey 
	 */
	public static void startServer() {
        Server server = null;
        try {
        	server = getServer();
            server.start();
            server.join();
        } catch (Exception e) {
            log.error("Server exception: " + e.getClass() + " " + e.getMessage());
            System.exit(1);
        } finally {
            server.destroy();
        }
    }


	/*
	 * getServer() - function responsible for initialization and start of Jetty with Jersey 
	 */
	private static Server getServer()  {
		
		Server server = new Server(Utils.getServerPort());
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath(Utils.getContextPath());
		server.setHandler(context);
		ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");		
		servletHolder.setInitParameter("jersey.config.server.provider.classnames",	
						 AmountTransferService.class.getCanonicalName());
		
		return server;
		
	}

}
