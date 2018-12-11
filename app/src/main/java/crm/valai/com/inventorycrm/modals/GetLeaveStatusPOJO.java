package crm.valai.com.inventorycrm.modals;

/*
 * @author by Mohit Arora on 22/6/18.
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
public class GetLeaveStatusPOJO {

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
            "application_Id",
            "from_Date",
            "to_Date",
            "remarks",
            "addressOnLeave",
            "TotalLeave",
            "day_Count",
            "leave_Avail",
            "no_Of_Day",
            "leave_Name",
            "status_Of_Leave",
            "staff_No",
            "name",
            "document",
            "compoff_Leave",
            "staff_Id",
            "errorMessage",
            "errorCode"
    })
    public class Datum {

        @JsonProperty("application_Id")
        private Integer application_Id;
        @JsonProperty("from_Date")
        private String from_Date;
        @JsonProperty("to_Date")
        private String to_Date;
        @JsonProperty("remarks")
        private String remarks;
        @JsonProperty("addressOnLeave")
        private String addressOnLeave;
        @JsonProperty("TotalLeave")
        private Object TotalLeave;
        @JsonProperty("day_Count")
        private Integer day_Count;
        @JsonProperty("leave_Avail")
        private Integer leave_Avail;
        @JsonProperty("no_Of_Day")
        private Integer no_Of_Day;
        @JsonProperty("leave_Name")
        private Object leave_Name;
        @JsonProperty("status_Of_Leave")
        private Integer status_Of_Leave;
        @JsonProperty("staff_No")
        private String staff_No;
        @JsonProperty("name")
        private String name;
        @JsonProperty("document")
        private String document;
        @JsonProperty("compoff_Leave")
        private Object compoff_Leave;
        @JsonProperty("staff_Id")
        private Integer staff_Id;
        @JsonProperty("errorMessage")
        private String errorMessage;
        @JsonProperty("errorCode")
        private String errorCode;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("application_Id")
        public Integer getApplicationId() {
            return application_Id;
        }

        @JsonProperty("application_Id")
        public void setApplicationId(Integer application_Id) {
            this.application_Id = application_Id;
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

        @JsonProperty("remarks")
        public String getRemarks() {
            return remarks;
        }

        @JsonProperty("remarks")
        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        @JsonProperty("addressOnLeave")
        public String getAddressOnLeave() {
            return addressOnLeave;
        }

        @JsonProperty("addressOnLeave")
        public void setAddressOnLeave(String addressOnLeave) {
            this.addressOnLeave = addressOnLeave;
        }

        @JsonProperty("TotalLeave")
        public Object getTotalLeave() {
            return TotalLeave;
        }

        @JsonProperty("TotalLeave")
        public void setTotalLeave(Object TotalLeave) {
            this.TotalLeave = TotalLeave;
        }

        @JsonProperty("day_Count")
        public Integer getDayCount() {
            return day_Count;
        }

        @JsonProperty("day_Count")
        public void setDayCount(Integer day_Count) {
            this.day_Count = day_Count;
        }

        @JsonProperty("leave_Avail")
        public Integer getLeaveAvail() {
            return leave_Avail;
        }

        @JsonProperty("leave_Avail")
        public void setLeaveAvail(Integer leave_Avail) {
            this.leave_Avail = leave_Avail;
        }

        @JsonProperty("no_Of_Day")
        public Integer getNoOfDay() {
            return no_Of_Day;
        }

        @JsonProperty("no_Of_Day")
        public void setNoOfDay(Integer no_Of_Day) {
            this.no_Of_Day = no_Of_Day;
        }

        @JsonProperty("leave_Name")
        public Object getLeaveName() {
            return leave_Name;
        }

        @JsonProperty("leave_Name")
        public void setLeaveName(Object leave_Name) {
            this.leave_Name = leave_Name;
        }

        @JsonProperty("status_Of_Leave")
        public Integer getStatusOfLeave() {
            return status_Of_Leave;
        }

        @JsonProperty("status_Of_Leave")
        public void setStatusOfLeave(Integer status_Of_Leave) {
            this.status_Of_Leave = status_Of_Leave;
        }

        @JsonProperty("staff_No")
        public String getStaffNo() {
            return staff_No;
        }

        @JsonProperty("staff_No")
        public void setStaffNo(String staff_No) {
            this.staff_No = staff_No;
        }

        @JsonProperty("name")
        public String getName() {
            return name;
        }

        @JsonProperty("name")
        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("document")
        public String getDocument() {
            return document;
        }

        @JsonProperty("document")
        public void setDocument(String document) {
            this.document = document;
        }

        @JsonProperty("compoff_Leave")
        public Object getCompoffLeave() {
            return compoff_Leave;
        }

        @JsonProperty("compoff_Leave")
        public void setCompoffLeave(Object compoffLeave) {
            this.compoff_Leave = compoff_Leave;
        }

        @JsonProperty("staff_Id")
        public Integer getStaffId() {
            return staff_Id;
        }

        @JsonProperty("staff_Id")
        public void setStaffId(Integer staff_Id) {
            this.staff_Id = staff_Id;
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