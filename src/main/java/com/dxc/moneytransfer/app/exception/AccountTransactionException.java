package com.dxc.moneytransfer.app.exception;

public class AccountTransactionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccountTransactionException(String msg) {
		super(msg);
	}

	public AccountTransactionException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
