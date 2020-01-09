package com.dxc.moneytransfer.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTransaction {

	@JsonProperty(required = true)
	private BigDecimal amount;

	@JsonProperty(required = true)
	private Long fromAccountId;

	@JsonProperty(required = true)
	private Long toAccountId;

	@JsonIgnore
	private BigDecimal fromAccountBalance;

	@JsonIgnore
	private BigDecimal toAccountBalance;

	public UserTransaction() {
	}

	public UserTransaction(BigDecimal amount, Long fromAccountId, Long toAccountId) {
		this.amount = amount;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Long getFromAccountId() {
		return fromAccountId;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

	public BigDecimal getFromAccountBalance() {
		return fromAccountBalance;
	}

	public void setFromAccountBalance(BigDecimal fromAccountBalance) {
		this.fromAccountBalance = fromAccountBalance;
	}

	public BigDecimal getToAccountBalance() {
		return toAccountBalance;
	}

	public void setToAccountBalance(BigDecimal toAccountBalance) {
		this.toAccountBalance = toAccountBalance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserTransaction that = (UserTransaction) o;

		if (!amount.equals(that.amount))
			return false;
		if (!fromAccountId.equals(that.fromAccountId))
			return false;
		return toAccountId.equals(that.toAccountId);

	}

	@Override
	public int hashCode() {
		int result = amount.hashCode();
		result = 31 * result + fromAccountId.hashCode();
		result = 31 * result + toAccountId.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "UserTransaction [amount=" + amount + ", fromAccountId=" + fromAccountId + ", toAccountId=" + toAccountId
				+ ", fromAccountBalance=" + fromAccountBalance + ", toAccountBalance=" + toAccountBalance + "]";
	}

}
