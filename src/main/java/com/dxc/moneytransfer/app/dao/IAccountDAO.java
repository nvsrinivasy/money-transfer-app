package com.dxc.moneytransfer.app.dao;

import com.dxc.moneytransfer.app.exception.AccountTransactionException;
import com.dxc.moneytransfer.app.model.Account;
import com.dxc.moneytransfer.app.model.UserTransaction;

public interface IAccountDAO {

	// List<Account> getAllAccounts() throws AccountTransferException;
	Account getAccountById(long accountId) throws AccountTransactionException;
	boolean transferFund(UserTransaction userTransaction) throws AccountTransactionException;
}
