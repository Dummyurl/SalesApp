package crm.valai.com.inventorycrm.modals;

/*
 * @author by Mohit Arora on 26/6/18.
 */

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ResponseStatus",
        "ResponseCode",
        "ResponseMessage",
        "data"
})
public class GetCustomerDetailsPOJO implements Serializable {

    @JsonProperty("ResponseStatus")
    private String ResponseStatus;
    @JsonProperty("ResponseCode")
    private String ResponseCode;
    @JsonProperty("ResponseMessage")
    private String ResponseMessage;
    @JsonProperty("data")
    private List<Datum> data = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ResponseStatus")
    public String getResponseStatus() {
        return ResponseStatus;
    }

    @JsonProperty("ResponseStatus")
    public void setResponseStatus(String ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    @JsonProperty("ResponseCode")
    public String getResponseCode() {
        return ResponseCode;
    }

    @JsonProperty("ResponseCode")
    public void setResponseCode(String ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    @JsonProperty("ResponseMessage")
    public String getResponseMessage() {
        return ResponseMessage;
    }

    @JsonProperty("ResponseMessage")
    public void setResponseMessage(String ResponseMessage) {
        this.ResponseMessage = ResponseMessage;
    }

    @JsonProperty("data")
    public List<Datum> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<Datum> data) {
        this.data = data;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "customer_Id",
            "reffered_By",
            "contact_Person_Name",
            "customer_Type",
            "contact_Person_designation",
            "customer_Name",
            "customer_Phone_Number",
            "contact_Person_Mobile_Number",
            "customer_Email",
            "contact_Person_Email",
            "nature_Of_Buisness",
            "annual_Turnover",
            "credit_Limit",
            "credit_days",
            "mode_Of_Payment",
            "current_Address",
            "current_State",
            "current_City",
            "current_Country",
            "shipping_Address",
            "shipping_City",
            "shipping_State",
            "shipping_Country",
            "current_Zipcode",
            "shipping_Zipcode",
            "pan_Number",
            "tin_Number",
            "gst_Number",
            "status",
            "visiting_card",
            "visiting_card_back",
            "id_Card_Proof",
            "address_Proof",
            "gst_Certificate",
            "dept_name",
            "buisness_Start_Date",
            "salesman",
            "salesman_Name",
            "errorMessage",
            "errorCode"
    })
    public class Datum implements Serializable {

        @JsonProperty("customer_Id")
        private Integer customer_Id;
        @JsonProperty("reffered_By")
        private String reffered_By;
        @JsonProperty("contact_Person_Name")
        private String contact_Person_Name;
        @JsonProperty("customer_Type")
        private Integer customer_Type;
        @JsonProperty("contact_Person_designation")
        private String contact_Person_designation;
        @JsonProperty("customer_Name")
        private String customer_Name;
        @JsonProperty("customer_Phone_Number")
        private String customer_Phone_Number;
        @JsonProperty("contact_Person_Mobile_Number")
        private String contact_Person_Mobile_Number;
        @JsonProperty("customer_Email")
        private String customer_Email;
        @JsonProperty("contact_Person_Email")
        private String contact_Person_Email;
        @JsonProperty("nature_Of_Buisness")
        private String nature_Of_Buisness;
        @JsonProperty("annual_Turnover")
        private Integer annual_Turnover;
        @JsonProperty("credit_Limit")
        private String credit_Limit;
        @JsonProperty("credit_days")
        private Integer credit_days;
        @JsonProperty("mode_Of_Payment")
        private Integer mode_Of_Payment;
        @JsonProperty("current_Address")
        private String current_Address;
        @JsonProperty("current_State")
        private Integer current_State;
        @JsonProperty("current_City")
        private Integer current_City;
        @JsonProperty("current_Country")
        private Integer current_Country;
        @JsonProperty("shipping_Address")
        private String shipping_Address;
        @JsonProperty("shipping_City")
        private Integer shipping_City;
        @JsonProperty("shipping_State")
        private Integer shipping_State;
        @JsonProperty("shipping_Country")
        private Integer shipping_Country;
        @JsonProperty("current_Zipcode")
        private String current_Zipcode;
        @JsonProperty("shipping_Zipcode")
        private String shipping_Zipcode;
        @JsonProperty("pan_Number")
        private String pan_Number;
        @JsonProperty("tin_Number")
        private String tin_Number;
        @JsonProperty("gst_Number")
        private String gst_Number;
        @JsonProperty("status")
        private Integer status;
        @JsonProperty("buisness_Start_Date")
        private String buisness_Start_Date;
        @JsonProperty("salesman")
        private Integer salesman;
        @JsonProperty("salesman_Name")
        private String salesman_Name;
        @JsonProperty("visiting_card")
        private String visiting_card;
        @JsonProperty("visiting_card_back")
        private String visiting_card_back;
        @JsonProperty("id_Card_Proof")
        private String id_Card_Proof;
        @JsonProperty("address_Proof")
        private String address_Proof;
        @JsonProperty("gst_Certificate")
        private String gst_Certificate;
        @JsonProperty("dept_name")
        private String dept_name;
        @JsonProperty("errorMessage")
        private String errorMessage;
        @JsonProperty("errorCode")
        private String errorCode;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("customer_Id")
        public Integer getCustomerId() {
            return customer_Id;
        }

        @JsonProperty("customer_Id")
        public void setCustomerId(Integer customer_Id) {
            this.customer_Id = customer_Id;
        }

        @JsonProperty("reffered_By")
        public String getRefferedBy() {
            return reffered_By;
        }

        @JsonProperty("reffered_By")
        public void setRefferedBy(String reffered_By) {
            this.reffered_By = reffered_By;
        }

        @JsonProperty("contact_Person_Name")
        public String getContactPersonName() {
            return contact_Person_Name;
        }

        @JsonProperty("contact_Person_Name")
        public void setContactPersonName(String contact_Person_Name) {
            this.contact_Person_Name = contact_Person_Name;
        }

        @JsonProperty("customer_Type")
        public Integer getCustomerType() {
            return customer_Type;
        }

        @JsonProperty("customer_Type")
        public void setCustomerType(Integer customer_Type) {
            this.customer_Type = customer_Type;
        }

        @JsonProperty("contact_Person_designation")
        public String getContactPersonDesignation() {
            return contact_Person_designation;
        }

        @JsonProperty("contact_Person_designation")
        public void setContactPersonDesignation(String contact_Person_designation) {
            this.contact_Person_designation = contact_Person_designation;
        }

        @JsonProperty("customer_Name")
        public String getCustomerName() {
            return customer_Name;
        }

        @JsonProperty("customer_Name")
        public void setCustomerName(String customer_Name) {
            this.customer_Name = customer_Name;
        }

        @JsonProperty("customer_Phone_Number")
        public String getCustomerPhoneNumber() {
            return customer_Phone_Number;
        }

        @JsonProperty("customer_Phone_Number")
        public void setCustomerPhoneNumber(String customer_Phone_Number) {
            this.customer_Phone_Number = customer_Phone_Number;
        }

        @JsonProperty("contact_Person_Mobile_Number")
        public String getContactPersonMobileNumber() {
            return contact_Person_Mobile_Number;
        }

        @JsonProperty("contact_Person_Mobile_Number")
        public void setContactPersonMobileNumber(String contact_Person_Mobile_Number) {
            this.contact_Person_Mobile_Number = contact_Person_Mobile_Number;
        }

        @JsonProperty("customer_Email")
        public String getCustomerEmail() {
            return customer_Email;
        }

        @JsonProperty("customer_Email")
        public void setCustomerEmail(String customer_Email) {
            this.customer_Email = customer_Email;
        }

        @JsonProperty("contact_Person_Email")
        public String getContactPersonEmail() {
            return contact_Person_Email;
        }

        @JsonProperty("contact_Person_Email")
        public void setContactPersonEmail(String contact_Person_Email) {
            this.contact_Person_Email = contact_Person_Email;
        }

        @JsonProperty("nature_Of_Buisness")
        public String getNatureOfBuisness() {
            return nature_Of_Buisness;
        }

        @JsonProperty("nature_Of_Buisness")
        public void setNatureOfBuisness(String nature_Of_Buisness) {
            this.nature_Of_Buisness = nature_Of_Buisness;
        }

        @JsonProperty("annual_Turnover")
        public Integer getAnnualTurnover() {
            return annual_Turnover;
        }

        @JsonProperty("annual_Turnover")
        public void setAnnualTurnover(Integer annual_Turnover) {
            this.annual_Turnover = annual_Turnover;
        }

        @JsonProperty("credit_Limit")
        public String getCreditLimit() {
            return credit_Limit;
        }

        @JsonProperty("credit_Limit")
        public void setCreditLimit(String credit_Limit) {
            this.credit_Limit = credit_Limit;
        }

        @JsonProperty("credit_days")
        public Integer getCreditDays() {
            return credit_days;
        }

        @JsonProperty("credit_days")
        public void setCreditDays(Integer credit_days) {
            this.credit_days = credit_days;
        }

        @JsonProperty("mode_Of_Payment")
        public Integer getModeOfPayment() {
            return mode_Of_Payment;
        }

        @JsonProperty("mode_Of_Payment")
        public void setModeOfPayment(Integer mode_Of_Payment) {
            this.mode_Of_Payment = mode_Of_Payment;
        }

        @JsonProperty("current_Address")
        public String getCurrentAddress() {
            return current_Address;
        }

        @JsonProperty("current_Address")
        public void setCurrentAddress(String current_Address) {
            this.current_Address = current_Address;
        }

        @JsonProperty("current_State")
        public Integer getCurrentState() {
            return current_State;
        }

        @JsonProperty("current_State")
        public void setCurrentState(Integer current_State) {
            this.current_State = current_State;
        }

        @JsonProperty("current_City")
        public Integer getCurrentCity() {
            return current_City;
        }

        @JsonProperty("current_City")
        public void setCurrentCity(Integer current_City) {
            this.current_City = current_City;
        }

        @JsonProperty("current_Country")
        public Integer getCurrentCountry() {
            return current_Country;
        }

        @JsonProperty("current_Country")
        public void setCurrentCountry(Integer current_Country) {
            this.current_Country = current_Country;
        }

        @JsonProperty("shipping_Address")
        public String getShippingAddress() {
            return shipping_Address;
        }

        @JsonProperty("shipping_Address")
        public void setShippingAddress(String shipping_Address) {
            this.shipping_Address = shipping_Address;
        }

        @JsonProperty("shipping_City")
        public Integer getShippingCity() {
            return shipping_City;
        }

        @JsonProperty("shipping_City")
        public void setShippingCity(Integer shipping_City) {
            this.shipping_City = shipping_City;
        }

        @JsonProperty("shipping_State")
        public Integer getShippingState() {
            return shipping_State;
        }

        @JsonProperty("shipping_State")
        public void setShippingState(Integer shipping_State) {
            this.shipping_State = shipping_State;
        }

        @JsonProperty("shipping_Country")
        public Integer getShippingCountry() {
            return shipping_Country;
        }

        @JsonProperty("shipping_Country")
        public void setShippingCountry(Integer shipping_Country) {
            this.shipping_Country = shipping_Country;
        }

        @JsonProperty("current_Zipcode")
        public String getCurrentZipcode() {
            return current_Zipcode;
        }

        @JsonProperty("current_Zipcode")
        public void setCurrentZipcode(String current_Zipcode) {
            this.current_Zipcode = current_Zipcode;
        }

        @JsonProperty("shipping_Zipcode")
        public String getShippingZipcode() {
            return shipping_Zipcode;
        }

        @JsonProperty("shipping_Zipcode")
        public void setShippingZipcode(String shipping_Zipcode) {
            this.shipping_Zipcode = shipping_Zipcode;
        }

        @JsonProperty("pan_Number")
        public String getPanNumber() {
            return pan_Number;
        }

        @JsonProperty("pan_Number")
        public void setPanNumber(String pan_Number) {
            this.pan_Number = pan_Number;
        }

        @JsonProperty("tin_Number")
        public String getTinNumber() {
            return tin_Number;
        }

        @JsonProperty("tin_Number")
        public void setTinNumber(String tin_Number) {
            this.tin_Number = tin_Number;
        }

        @JsonProperty("gst_Number")
        public String getGstNumber() {
            return gst_Number;
        }

        @JsonProperty("gst_Number")
        public void setGstNumber(String gst_Number) {
            this.gst_Number = gst_Number;
        }

        @JsonProperty("status")
        public Integer getStatus() {
            return status;
        }

        @JsonProperty("status")
        public void setStatus(Integer status) {
            this.status = status;
        }

        @JsonProperty("buisness_Start_Date")
        public String getBuisnessStartDate() {
            return buisness_Start_Date;
        }

        @JsonProperty("buisness_Start_Date")
        public void setBuisnessStartDate(String buisness_Start_Date) {
            this.buisness_Start_Date = buisness_Start_Date;
        }

        @JsonProperty("salesman")
        public Integer getSalesman() {
            return salesman;
        }

        @JsonProperty("salesman")
        public void setSalesman(Integer salesman) {
            this.salesman = salesman;
        }

        @JsonProperty("salesman_Name")
        public String getSalesmanName() {
            return salesman_Name;
        }

        @JsonProperty("salesman_Name")
        public void setSalesmanName(String salesman_Name) {
            this.salesman_Name = salesman_Name;
        }

        public String getVisiting_card() {
            return visiting_card;
        }

        public void setVisiting_card(String visiting_card) {
            this.visiting_card = visiting_card;
        }

        public String getVisiting_card_back() {
            return visiting_card_back;
        }

        public void setVisiting_card_back(String visiting_card_back) {
            this.visiting_card_back = visiting_card_back;
        }

        public String getId_Card_Proof() {
            return id_Card_Proof;
        }

        public void setId_Card_Proof(String id_Card_Proof) {
            this.id_Card_Proof = id_Card_Proof;
        }

        public String getAddress_Proof() {
            return address_Proof;
        }

        public void setAddress_Proof(String address_Proof) {
            this.address_Proof = address_Proof;
        }

        public String getGst_Certificate() {
            return gst_Certificate;
        }

        public void setGst_Certificate(String gst_Certificate) {
            this.gst_Certificate = gst_Certificate;
        }

        public String getDept_name() {
            return dept_name;
        }

        public void setDept_name(String dept_name) {
            this.dept_name = dept_name;
        }

        @JsonProperty("errorMessage")
        public String getErrorMessage() {
            return errorMessage;
        }

        @JsonProperty("errorMessage")
        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @JsonProperty("errorCode")
        public String getErrorCode() {
            return errorCode;
        }

        @JsonProperty("errorCode")
        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
