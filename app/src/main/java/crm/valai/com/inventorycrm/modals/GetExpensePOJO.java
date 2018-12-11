package crm.valai.com.inventorycrm.modals;

/*
 * @author by Mohit Arora on 11/6/18.
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
public class GetExpensePOJO {

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
            "expense_id",
            "name",
            "staff_No",
            "staff_Name",
            "submitted_Date",
            "total_Amount",
            "approver_remarks",
            "status",
            "staff_remarks",
            "errorMessage",
            "errorCode"
    })
    public class Datum {

        @JsonProperty("expense_id")
        private Integer expense_id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("staff_No")
        private String staff_No;
        @JsonProperty("staff_Name")
        private String staff_Name;
        @JsonProperty("submitted_Date")
        private String submitted_Date;
        @JsonProperty("total_Amount")
        private Integer total_Amount;
        @JsonProperty("approver_remarks")
        private Object approver_remarks;
        @JsonProperty("status")
        private Integer status;
        @JsonProperty("staff_remarks")
        private String staff_remarks;
        @JsonProperty("errorMessage")
        private String errorMessage;
        @JsonProperty("errorCode")
        private String errorCode;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("expense_id")
        public Integer getExpenseId() {
            return expense_id;
        }

        @JsonProperty("expense_id")
        public void setExpenseId(Integer expense_id) {
            this.expense_id = expense_id;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("staff_No")
        public String getStaffNo() {
            return staff_No;
        }

        @JsonProperty("staff_No")
        public void setStaffNo(String staff_No) {
            this.staff_No = staff_No;
        }

        @JsonProperty("staff_Name")
        public String getStaffName() {
            return staff_Name;
        }

        @JsonProperty("staff_Name")
        public void setStaffName(String staff_Name) {
            this.staff_Name = staff_Name;
        }

        @JsonProperty("submitted_Date")
        public String getSubmittedDate() {
            return submitted_Date;
        }

        @JsonProperty("submitted_Date")
        public void setSubmittedDate(String submitted_Date) {
            this.submitted_Date = submitted_Date;
        }

        @JsonProperty("total_Amount")
        public Integer getTotalAmount() {
            return total_Amount;
        }

        @JsonProperty("total_Amount")
        public void setTotalAmount(Integer total_Amount) {
            this.total_Amount = total_Amount;
        }

        @JsonProperty("approver_remarks")
        public Object getApproverRemarks() {
            return approver_remarks;
        }

        @JsonProperty("approver_remarks")
        public void setApproverRemarks(Object approver_remarks) {
            this.approver_remarks = approver_remarks;
        }

        @JsonProperty("status")
        public Integer getStatus() {
            return status;
        }

        @JsonProperty("status")
        public void setStatus(Integer status) {
            this.status = status;
        }

        @JsonProperty("staff_remarks")
        public String getStaffRemarks() {
            return staff_remarks;
        }

        @JsonProperty("staff_remarks")
        public void setStaffRemarks(String staff_remarks) {
            this.staff_remarks = staff_remarks;
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