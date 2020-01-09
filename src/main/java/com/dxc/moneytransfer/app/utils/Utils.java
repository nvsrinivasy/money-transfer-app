package com.dxc.moneytransfer.app.utils;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

public class Utils {

	static Logger log = Logger.getLogger(Utils.class);

	public static Properties properties = new Properties();

	// zero amount with scale 4 and financial rounding mode

	public static void loadConfigProps(String fileName) {

		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);) {
			// Properties prop = new Properties();
			properties.load(inputStream);
			// System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static BigDecimal getZeroPrice() {

		return new BigDecimal(0).setScale(4, RoundingMode.HALF_EVEN);
	}
	
	
	/*
	 * getContextPath() - This function helps to get Context path from props
	 * 
	 */
	public static String getContextPath() {
		return StringUtil.isNotBlank(properties.getProperty("server.contextPath"))
				? properties.getProperty("server.contextPath")
				: Constants.APP_CONTXT_PATH;
	}

	/*
	 * getServerPort()- This function helps to get Server port from props
	 * 
	 */
	public static int getServerPort() {

		return StringUtil.isNotBlank(properties.getProperty("server.port"))
				? Integer.parseInt(properties.getProperty("server.port").trim())
				: Integer.parseInt(Constants.DEFAULT_SERVER_PORT);
	}

	// initialise

	static {

		loadConfigProps(Constants.PROPERTIES_FILE);
	}

}
