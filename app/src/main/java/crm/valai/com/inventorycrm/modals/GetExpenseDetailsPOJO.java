package crm.valai.com.inventorycrm.modals;

/*
 * @author by Mohit Arora on 11/6/18.
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ResponseStatus",
        "ResponseCode",
        "ResponseMessage",
        "data"
})
public class GetExpenseDetailsPOJO {

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
            "expense_detail_id",
            "from_Date",
            "to_Date",
            "from_location",
            "to_location",
            "bill_amount",
            "expense_Category_Name",
            "expense_type",
            "amount",
            "total_Amount",
            "remarks",
            "name",
            "errorMessage",
            "errorCode"
    })
    public class Datum {

        @JsonProperty("expense_id")
        private Integer expense_id;
        @JsonProperty("expense_detail_id")
        private Integer expense_detail_id;
        @JsonProperty("from_Date")
        private String from_Date;
        @JsonProperty("to_Date")
        private String to_Date;
        @JsonProperty("from_location")
        private String from_location;
        @JsonProperty("to_location")
        private String to_location;
        @JsonProperty("bill_amount")
        private Integer bill_amount;
        @JsonProperty("expense_Category_Name")
        private String expense_Category_Name;
        @JsonProperty("expense_type")
        private Integer expense_type;
        @JsonProperty("amount")
        private Integer amount;
        @JsonProperty("total_Amount")
        private Integer total_Amount;
        @JsonProperty("remarks")
        private String remarks;
        @JsonProperty("name")
        private String name;
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

        @JsonProperty("expense_detail_id")
        public Integer getExpenseDetailId() {
            return expense_detail_id;
        }

        @JsonProperty("expense_detail_id")
        public void setExpenseDetailId(Integer expense_detail_id) {
            this.expense_detail_id = expense_detail_id;
        }

        @JsonProperty("from_Date")
        public String getFromDate() {
            return from_Date;
        }

        @JsonProperty("from_Date")
        public void setFromDate(String from_Date) {
            this.from_Date = from_Date;
        }

        @JsonProperty("to_Date")
        public String getToDate() {
            return to_Date;
        }

        @JsonProperty("to_Date")
        public void setToDate(String to_Date) {
            this.to_Date = to_Date;
        }

        @JsonProperty("from_location")
        public String getFromLocation() {
            return from_location;
        }

        @JsonProperty("from_location")
        public void setFromLocation(String from_location) {
            this.from_location = from_location;
        }

        @JsonProperty("to_location")
        public String getToLocation() {
            return to_location;
        }

        @JsonProperty("to_location")
        public void setToLocation(String to_location) {
            this.to_location = to_location;
        }

        @JsonProperty("bill_amount")
        public Integer getBillAmount() {
            return bill_amount;
        }

        @JsonProperty("bill_amount")
        public void setBillAmount(Integer bill_amount) {
            this.bill_amount = bill_amount;
        }

        @JsonProperty("expense_Category_Name")
        public String getExpenseCategoryName() {
            return expense_Category_Name;
        }

        @JsonProperty("expense_Category_Name")
        public void setExpenseCategoryName(String expense_Category_Name) {
            this.expense_Category_Name = expense_Category_Name;
        }

        @JsonProperty("expense_type")
        public Integer getExpenseType() {
            return expense_type;
        }

        @JsonProperty("expense_type")
        public void setExpenseType(Integer expense_type) {
            this.expense_type = expense_type;
        }

        @JsonProperty("amount")
        public Integer getAmount() {
            return amount;
        }

        @JsonProperty("amount")
        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        @JsonProperty("total_Amount")
        public Integer getTotalAmount() {
            return total_Amount;
        }

        @JsonProperty("total_Amount")
        public void setTotalAmount(Integer total_Amount) {
            this.total_Amount = total_Amount;
        }

        @JsonProperty("remarks")
        public String getRemarks() {
            return remarks;
        }

        @JsonProperty("remarks")
        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
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