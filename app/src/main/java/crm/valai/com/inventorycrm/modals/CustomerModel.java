package crm.valai.com.inventorycrm.modals;

import java.io.Serializable;

/**
 * @author by Mohit Arora on 27/6/18.
 */

public class CustomerModel implements Serializable {
    private String customerName;
    private String customerPhoneNumber;
    private String customerEmail;
    private String contactPersonName;
    private String contactPersonNameDesignation;
    private String contactPersonNumber;
    private String contactPersonEmail;
    private String creditLimit;
    private String creditDays;
    private String panNumber;
    private String tinNumber;
    private String gstNumber;
    private String nature;
    private String startDateBusiness;
    private String customerType;
    private String paymentMode;
    private String annualTurnOver;
    private String referredBy;
    private String salesmen;
    private String statusVal;
    private String current_country;
    private String current_state;
    private String current_city;

    public String getCurrent_country() {
        return current_country;
    }

    public void setCurrent_country(String current_country) {
        this.current_country = current_country;
    }

    public String getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(String current_state) {
        this.current_state = current_state;
    }

    public String getCurrent_city() {
        return current_city;
    }

    public void setCurrent_city(String current_city) {
        this.current_city = current_city;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonNameDesignation() {
        return contactPersonNameDesignation;
    }

    public void setContactPersonNameDesignation(String contactPersonNameDesignation) {
        this.contactPersonNameDesignation = contactPersonNameDesignation;
    }

    public String getContactPersonNumber() {
        return contactPersonNumber;
    }

    public void setContactPersonNumber(String contactPersonNumber) {
        this.contactPersonNumber = contactPersonNumber;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(String creditDays) {
        this.creditDays = creditDays;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getStartDateBusiness() {
        return startDateBusiness;
    }

    public void setStartDateBusiness(String startDateBusiness) {
        this.startDateBusiness = startDateBusiness;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getAnnualTurnOver() {
        return annualTurnOver;
    }

    public void setAnnualTurnOver(String annualTurnOver) {
        this.annualTurnOver = annualTurnOver;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getSalesmen() {
        return salesmen;
    }

    public void setSalesmen(String salesmen) {
        this.salesmen = salesmen;
    }

    public String getStatusVal() {
        return statusVal;
    }

    public void setStatusVal(String statusVal) {
        this.statusVal = statusVal;
    }

}