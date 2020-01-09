package com.dxc.moneytransfer.app.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.dxc.moneytransfer.app.dao.IAccountDAO;
import com.dxc.moneytransfer.app.exception.AccountTransactionException;
import com.dxc.moneytransfer.app.model.Account;
import com.dxc.moneytransfer.app.model.UserTransaction;
import com.dxc.moneytransfer.app.utils.Utils;

public class AccountDAOImpl implements IAccountDAO {
	
	private static Logger log = Logger.getLogger(AccountDAOImpl.class);

	private final static String GET_ACC_BY_ID = "SELECT * FROM Account WHERE AccountId = ? ";
	private final static String GET_ACC_BY_ID_LOCK = "SELECT * FROM Account WHERE AccountId = ? FOR UPDATE";
	private final static String UPDATE_ACC_BALANCE = "UPDATE Account SET Balance = ? ,LastUpdatedDate = TO_DATE(CURRENT_DATE(), 'YYYY-MM-DD')   WHERE AccountId = ? ";	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dxc.moneytransfer.app.dao.AccountDAO#transferFund(com.moneytransfer.model.UserTransaction)
	 * transferFund() - This function helps to transfer amount other account
	 * @param
	 * @return 
	 */
	public boolean transferFund(UserTransaction userTransaction) throws AccountTransactionException {		
		boolean isTrasfer= false;
		Connection conn = null;		
		Account fromAccount = null;
		Account toAccount = null;
		log.info("transferFund(): " + userTransaction);	
		try {
			 conn = DBConnection.getConnection();
				//AutoCloseable fin = conn::rollback){
			//	SQLCloseable finish = () -> { if(!conn.getAutoCommit()) conn.rollback(); }){
			conn.setAutoCommit(false);
			// Get from account user details and locks the rows it returns 
			fromAccount=getAccountDetails(conn,userTransaction.getFromAccountId());
			// Get to account user details and locks the rows it returns 			
			toAccount=	getAccountDetails(conn,userTransaction.getToAccountId());
			// check both account status status
			if (fromAccount == null || toAccount == null) {
				throw new AccountTransactionException("Failed to lock both accounts for write");
			}		
			BigDecimal accBal = accountBalanceCheck(fromAccount,userTransaction);
			userTransaction.setFromAccountBalance(accBal);
			userTransaction.setToAccountBalance(toAccount.getBalance().add(userTransaction.getAmount()));			
			
			// update both accounts with debit and credit amt
			isTrasfer=	updateAccoutDetails(conn,userTransaction);
			
			if (log.isDebugEnabled()) {
				log.debug("Number of rows updated for the transfer : " + isTrasfer);
			}
			
			//conn.commit();
		} catch (SQLException se) {
			// rollback transaction if exception occurs
			log.error("transferAccountBalance(): User Transaction Failed, rollback initiated for: " + userTransaction,	se);
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException re) {
				throw new AccountTransactionException("Fail to rollback transaction", re);
			}
		} 
		
		return isTrasfer;
	}
	

	/*getAccountDetails() -This function helps to get Account details based on accId
	 * @param accountId
	 * @return Account
	 */
	private Account getAccountDetails(Connection conn,Long accountId) {		
		log.info("getAccountDetails AccountId:"+accountId);
		Account account = null;
		// Get from account user details and locks the rows it returns 
		try(PreparedStatement getAccDet_stmt  =conn.prepareStatement(GET_ACC_BY_ID_LOCK);){				
			getAccDet_stmt.setLong(1, accountId);
			try(ResultSet rs = getAccDet_stmt.executeQuery();){					
					if(rs.next()) {
						account = new Account(rs.getLong("AccountId"), rs.getString("UserName"),
								rs.getBigDecimal("Balance"), rs.getString("CreatedDate"),null);
						if (log.isDebugEnabled())
							log.debug("getAccountDetails from Account: " + account);
					}
			}
		}catch(Exception e) {
			log.error("Exception occured when getting Account details");
		}		
		return account;
	}
	
	
	private boolean updateAccoutDetails(Connection conn, UserTransaction userTransaction) throws SQLException {
		log.info("updateAccoutDetails UserTransaction:"+userTransaction);
		boolean isUpdated = false;		
		// proceed with update to both accounts
		try (PreparedStatement updateStmt = conn.prepareStatement(UPDATE_ACC_BALANCE);) {
			updateStmt.setBigDecimal(1, userTransaction.getFromAccountBalance());
			updateStmt.setLong(2, userTransaction.getFromAccountId());
			updateStmt.addBatch();
			updateStmt.setBigDecimal(1, userTransaction.getFromAccountBalance());
			updateStmt.setLong(2, userTransaction.getToAccountId());
			updateStmt.addBatch();
			int[] rowsUpdated = updateStmt.executeBatch();
			conn.commit();
			
			if (rowsUpdated[0] == rowsUpdated[1])
				isUpdated = true;
		}
		return isUpdated;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dxc.moneytransfer.app.dao.AccountDAO#getAccountById(long)
	 * getAccountById() - This function helps to get account details
	 * @param
	 * @return
	 */
	public Account getAccountById(long accountId) throws AccountTransactionException {
		log.info("getAccountById: " + accountId);
		Account account = null;
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_ACC_BY_ID);) {
			stmt.setLong(1, accountId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					account = new Account(rs.getLong("AccountId"), rs.getString("UserName"),
							rs.getBigDecimal("Balance"), rs.getString("CreatedDate"),rs.getString("LastUpdatedDate"));
					if (log.isDebugEnabled())
						log.debug("Retrieve Account By Id: " + account);
				}
			}
			return account;
		} catch (SQLException e) {
			throw new AccountTransactionException("getAccountById(): Exception occurred while fetching account data", e);
		}
	}
	
	/*accountBalanceCheck() - this function helps to check Account balance check
	 * @param
	 * @return
	 */
	private BigDecimal accountBalanceCheck(Account account, UserTransaction userTransaction) throws AccountTransactionException {
		
		BigDecimal accBalance = account.getBalance().subtract(userTransaction.getAmount());

		if (accBalance.compareTo(Utils.getZeroPrice()) < 0) {
			throw new AccountTransactionException("Transaction failed due to insufficient funds");
		}

		return accBalance;
	}	
}
