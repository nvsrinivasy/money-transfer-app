package com.dxc.moneytransfer.app.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;
import com.dxc.moneytransfer.app.utils.Utils;

public class DBConnection {

	private static Logger log = Logger.getLogger(DBConnection.class);
	
	private static final String dbDriver = Utils.properties.getProperty("db.driver");
	private static final String dbConnectionURL = Utils.properties.getProperty("db.connection_url");
	private static final String user = Utils.properties.getProperty("db.user");
	private static final String password = Utils.properties.getProperty("db.password");	
	
	//Driver load
	static{		
		DbUtils.loadDriver(dbDriver);
	}

	/*getConnection()- This function helps to provide Database connection
	 * 
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbConnectionURL, user, password);

	}
	
	/*loadTestData()- this function helps to load test user account data
	 * 
	 */
	public static void loadTestData() {
		log.info("Load test user account data ..... ");				
		try(Connection conn = getConnection()){			
			RunScript.execute(conn, new FileReader("/moneytransfer_test_script.sql"));
		} catch (SQLException e) {
			log.error("loadTestData(): Error test data loading: ", e);
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			log.error("loadTestData(): Error not finding test db script file ", e);
			throw new RuntimeException(e);
		}
	}
}
