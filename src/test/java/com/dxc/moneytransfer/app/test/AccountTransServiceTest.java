package com.dxc.moneytransfer.app.test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.junit.Test;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;
import com.dxc.moneytransfer.app.dao.impl.DBConnection;
import com.dxc.moneytransfer.app.model.UserTransaction;
import com.dxc.moneytransfer.app.rest.service.AmountTransferService;

public class AccountTransServiceTest extends JerseyTest{

	
	@Override
	 public Application configure() {
	  enable(TestProperties.LOG_TRAFFIC);
	  enable(TestProperties.DUMP_ENTITY);
	  DBConnection.loadTestData();//
	  return new ResourceConfig(AmountTransferService.class);
	 }
	
	/*
	 * 
	 */
	@Test
	 public void getAccountDetailsById() {
	  Response output = target("/account/100011").request().get();
	  assertEquals("Should return status 200", 200, output.getStatus());
	  assertNotNull("Should return Account as json", output.getEntity());
	  System.out.println(output.getStatus());
	  System.out.println(output.readEntity(String.class));
	 }
	
	@Test
	 public void testTransferfunds() {
		 UserTransaction userTrans = new UserTransaction(new BigDecimal(1000), 100011L, 100012L);
	  Response output = target("/account/transferFund").request().post(Entity.entity(userTrans, MediaType.APPLICATION_JSON));
	  System.out.println(output.getStatus());
	  //assertEquals("Should return status 201", 201, output.getStatus());
	 }
}
