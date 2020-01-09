package com.dxc.moneytransfer.app.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import com.dxc.moneytransfer.app.dao.impl.AccountDAOImpl;
import com.dxc.moneytransfer.app.exception.AccountTransactionException;
import com.dxc.moneytransfer.app.model.Account;
import com.dxc.moneytransfer.app.model.UserTransaction;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AmountTransferService {

	private static Logger log = Logger.getLogger(AmountTransferService.class);
	
	/**
	 * Transfer funds between two accounts.
	 * @param UserTransaction
	 * @return Response
	 * @throws AccountTransactionException
	 */
	@POST
	@Path("/transferFund") 
	public Response transferFund(UserTransaction transaction) throws AccountTransactionException {
		log.info("   transferFund  ****");
		boolean result = new AccountDAOImpl().transferFund(transaction);
		if (result) {
			return Response.status(Response.Status.OK).build();
		} else {
			// transaction failed
			throw new WebApplicationException("Transaction failed", Response.Status.BAD_REQUEST);
		}
	}
	
	/**
     * Get Account details by accId
     * @param accountId
     * @return Account
     * @throws CustomException
     */
    @GET
    @Path("/{accountId}")
    public Account getAccount(@PathParam("accountId") long accountId) throws AccountTransactionException {
        return new AccountDAOImpl().getAccountById(accountId);
    }
    
}
