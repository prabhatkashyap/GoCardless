package gocardless.api;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PreAuthorizedBill implements Serializable {

  private static final long serialVersionUID = 1L;

  private BigDecimal amount;

  private String preAuthorizationId;

  private String name;

  private String description;

  private Date chargeCustomerAt;

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getPreAuthorizationId() {
    return preAuthorizationId;
  }

  public void setPreAuthorizationId(String preAuthorizationId) {
    this.preAuthorizationId = preAuthorizationId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getChargeCustomerAt() {
    return this.chargeCustomerAt;
  }

  public void setChargeCustomerAt(Date chargeCustomerAt) {
    this.chargeCustomerAt = chargeCustomerAt;
  }

  @Override
  public boolean equals(Object obj) {
      return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this);
  }

  public static class Builder {

    private BigDecimal amount;

    private String preAuthorizationId;

    private String name;

    private String description;

    private Date chargeCustomerAt;

    public Builder amount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public Builder preAuthorizationId(String preAuthorizationId) {
      this.preAuthorizationId = preAuthorizationId;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder chargeCustomerAt(Date chargeCustomerAt) {
      this.chargeCustomerAt = chargeCustomerAt;
      return this;
    }

    public PreAuthorizedBill build() {
      PreAuthorizedBill preAuthorizedBill = new PreAuthorizedBill();
      preAuthorizedBill.amount = this.amount;
      preAuthorizedBill.preAuthorizationId = this.preAuthorizationId;
      preAuthorizedBill.name = this.name;
      preAuthorizedBill.description = this.description;
      preAuthorizedBill.chargeCustomerAt = this.chargeCustomerAt;
      return preAuthorizedBill;
    }

  }

}
