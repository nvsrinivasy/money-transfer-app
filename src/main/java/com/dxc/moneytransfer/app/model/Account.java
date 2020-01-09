package com.dxc.moneytransfer.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class Account {

	@JsonProperty(required = true)
    private long accountId;

    @JsonProperty(required = true)
    private String userName;

    @JsonProperty(required = true)
    private BigDecimal balance;

    @JsonProperty(required = true)
    private String createdDate;
    
    @JsonProperty(required = true)
    private String lastUpdatedDate;

    public Account() {
    }

    public Account(String userName, BigDecimal balance, String createdDate) {
        this.userName = userName;
        this.balance = balance;
        this.createdDate = createdDate;
    }

    public Account(long accountId, String userName, BigDecimal balance, String createdDate,String lastUpdatedDate) {
        this.accountId = accountId;
        this.userName = userName;
        this.balance = balance;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getUserName() {
        return userName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", userName=" + userName + ", balance=" + balance + ", createdDate="
				+ createdDate + ", lastUpdatedDate=" + lastUpdatedDate + "]";
	}       


}
