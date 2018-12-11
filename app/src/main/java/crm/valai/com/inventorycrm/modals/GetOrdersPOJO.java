package crm.valai.com.inventorycrm.modals;

/*
 * @author by Mohit Arora on 17/4/18.
 */

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
public class GetOrdersPOJO {

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
            "customer_Name",
            "branch_Id",
            "order_Id",
            "order_Ref_Number",
            "order_Date",
            "Manager_Id",
            "Manager",
            "manager_Approved",
            "manager_remarks",
            "staff_Remarks",
            "amount",
            "total_tax_Amount",
            "total_Amount",
            "status",
            "errorMessage",
            "errorCode"
    })
    public class Datum {

        @JsonProperty("customer_Id")
        private Integer customer_Id;
        @JsonProperty("customer_Name")
        private String customer_Name;
        @JsonProperty("branch_Id")
        private Integer branch_Id;
        @JsonProperty("order_Id")
        private Integer order_Id;
        @JsonProperty("order_Ref_Number")
        private String order_Ref_Number;
        @JsonProperty("order_Date")
        private String order_Date;
        @JsonProperty("Manager_Id")
        private Integer Manager_Id;
        @JsonProperty("Manager")
        private String Manager;
        @JsonProperty("manager_Approved")
        private Integer manager_Approved;
        @JsonProperty("manager_remarks")
        private String manager_remarks;
        @JsonProperty("staff_Remarks")
        private String staff_Remarks;
        @JsonProperty("amount")
        private Integer amount;
        @JsonProperty("total_tax_Amount")
        private Double total_tax_Amount;
        @JsonProperty("total_Amount")
        private Double total_Amount;
        @JsonProperty("status")
        private Integer status;
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

        @JsonProperty("customer_Name")
        public String getCustomerName() {
            return customer_Name;
        }

        @JsonProperty("customer_Name")
        public void setCustomerName(String customer_Name) {
            this.customer_Name = customer_Name;
        }

        @JsonProperty("branch_Id")
        public Integer getBranchId() {
            return branch_Id;
        }

        @JsonProperty("branch_Id")
        public void setBranchId(Integer branch_Id) {
            this.branch_Id = branch_Id;
        }

        @JsonProperty("order_Id")
        public Integer getOrderId() {
            return order_Id;
        }

        @JsonProperty("order_Id")
        public void setOrderId(Integer order_Id) {
            this.order_Id = order_Id;
        }

        @JsonProperty("order_Ref_Number")
        public String getOrderRefNumber() {
            return order_Ref_Number;
        }

        @JsonProperty("order_Ref_Number")
        public void setOrderRefNumber(String order_Ref_Number) {
            this.order_Ref_Number = order_Ref_Number;
        }

        @JsonProperty("order_Date")
        public String getOrderDate() {
            return order_Date;
        }

        @JsonProperty("order_Date")
        public void setOrderDate(String order_Date) {
            this.order_Date = order_Date;
        }

        @JsonProperty("Manager_Id")
        public Integer getManagerId() {
            return Manager_Id;
        }

        @JsonProperty("Manager_Id")
        public void setManagerId(Integer Manager_Id) {
            this.Manager_Id = Manager_Id;
        }

        @JsonProperty("Manager")
        public String getManager() {
            return Manager;
        }

        @JsonProperty("Manager")
        public void setManager(String Manager) {
            this.Manager = Manager;
        }

        @JsonProperty("manager_Approved")
        public Integer getManagerApproved() {
            return manager_Approved;
        }

        @JsonProperty("manager_Approved")
        public void setManagerApproved(Integer manager_Approved) {
            this.manager_Approved = manager_Approved;
        }

        @JsonProperty("manager_remarks")
        public String getManagerRemarks() {
            return manager_remarks;
        }

        @JsonProperty("manager_remarks")
        public void setManagerRemarks(String manager_remarks) {
            this.manager_remarks = manager_remarks;
        }

        @JsonProperty("staff_Remarks")
        public String getStaffRemarks() {
            return staff_Remarks;
        }

        @JsonProperty("staff_Remarks")
        public void setStaffRemarks(String staff_Remarks) {
            this.staff_Remarks = staff_Remarks;
        }

        @JsonProperty("amount")
        public Integer getAmount() {
            return amount;
        }

        @JsonProperty("amount")
        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        @JsonProperty("total_tax_Amount")
        public Double getTotalTaxAmount() {
            return total_tax_Amount;
        }

        @JsonProperty("total_tax_Amount")
        public void setTotalTaxAmount(Double total_tax_Amount) {
            this.total_tax_Amount = total_tax_Amount;
        }

        @JsonProperty("total_Amount")
        public Double getTotalAmount() {
            return total_Amount;
        }

        @JsonProperty("total_Amount")
        public void setTotalAmount(Double total_Amount) {
            this.total_Amount = total_Amount;
        }

        @JsonProperty("status")
        public Integer getStatus() {
            return status;
        }

        @JsonProperty("status")
        public void setStatus(Integer status) {
            this.status = status;
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