package crm.valai.com.inventorycrm.modals;

/*
 * @author by Mohit Arora on 3/7/18.
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
public class GetStaffDailyTrackerPOJO {

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
            "visited_date",
            "report_id",
            "customer_Id",
            "customer_name",
            "contact_Number",
            "location_name",
            "mode_of_communication",
            "purpose_of_visit",
            "pending_Orders",
            "pending_Order_Uom",
            "sales",
            "sales_Uom",
            "collection_in_rs",
            "remarks",
            "manager_Remarks",
            "errorMessage",
            "errorCode"
    })
    public class Datum {

        @JsonProperty("visited_date")
        private String visited_date;
        @JsonProperty("report_id")
        private Integer report_id;
        @JsonProperty("customer_Id")
        private Integer customer_Id;
        @JsonProperty("customer_name")
        private String customer_name;
        @JsonProperty("contact_Number")
        private String contact_Number;
        @JsonProperty("location_name")
        private String location_name;
        @JsonProperty("mode_of_communication")
        private Integer mode_of_communication;
        @JsonProperty("purpose_of_visit")
        private String purpose_of_visit;
        @JsonProperty("pending_Orders")
        private Integer pending_Orders;
        @JsonProperty("pending_Order_Uom")
        private Integer pending_Order_Uom;
        @JsonProperty("sales")
        private Integer sales;
        @JsonProperty("sales_Uom")
        private Integer sales_Uom;
        @JsonProperty("collection_in_rs")
        private Integer collection_in_rs;
        @JsonProperty("remarks")
        private String remarks;
        @JsonProperty("manager_Remarks")
        private String manager_Remarks;
        @JsonProperty("errorMessage")
        private String errorMessage;
        @JsonProperty("errorCode")
        private String errorCode;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("visited_date")
        public String getVisitedDate() {
            return visited_date;
        }

        @JsonProperty("visited_date")
        public void setVisitedDate(String visited_date) {
            this.visited_date = visited_date;
        }

        @JsonProperty("report_id")
        public Integer getReportId() {
            return report_id;
        }

        @JsonProperty("report_id")
        public void setReportId(Integer report_id) {
            this.report_id = report_id;
        }

        @JsonProperty("customer_Id")
        public Integer getCustomerId() {
            return customer_Id;
        }

        @JsonProperty("customer_Id")
        public void setCustomerId(Integer customer_Id) {
            this.customer_Id = customer_Id;
        }

        @JsonProperty("customer_name")
        public String getCustomerName() {
            return customer_name;
        }

        @JsonProperty("customer_name")
        public void setCustomerName(String customer_name) {
            this.customer_name = customer_name;
        }

        @JsonProperty("contact_Number")
        public String getContactNumber() {
            return contact_Number;
        }

        @JsonProperty("contact_Number")
        public void setContactNumber(String contact_Number) {
            this.contact_Number = contact_Number;
        }

        @JsonProperty("location_name")
        public String getLocationName() {
            return location_name;
        }

        @JsonProperty("location_name")
        public void setLocationName(String location_name) {
            this.location_name = location_name;
        }

        @JsonProperty("mode_of_communication")
        public Integer getModeOfCommunication() {
            return mode_of_communication;
        }

        @JsonProperty("mode_of_communication")
        public void setModeOfCommunication(Integer mode_of_communication) {
            this.mode_of_communication = mode_of_communication;
        }

        @JsonProperty("purpose_of_visit")
        public String getPurposeOfVisit() {
            return purpose_of_visit;
        }

        @JsonProperty("purpose_of_visit")
        public void setPurposeOfVisit(String purpose_of_visit) {
            this.purpose_of_visit = purpose_of_visit;
        }

        @JsonProperty("pending_Orders")
        public Integer getPendingOrders() {
            return pending_Orders;
        }

        @JsonProperty("pending_Orders")
        public void setPendingOrders(Integer pending_Orders) {
            this.pending_Orders = pending_Orders;
        }

        @JsonProperty("pending_Order_Uom")
        public Integer getPendingOrderUom() {
            return pending_Order_Uom;
        }

        @JsonProperty("pending_Order_Uom")
        public void setPendingOrderUom(Integer pending_Order_Uom) {
            this.pending_Order_Uom = pending_Order_Uom;
        }

        @JsonProperty("sales")
        public Integer getSales() {
            return sales;
        }

        @JsonProperty("sales")
        public void setSales(Integer sales) {
            this.sales = sales;
        }

        @JsonProperty("sales_Uom")
        public Integer getSalesUom() {
            return sales_Uom;
        }

        @JsonProperty("sales_Uom")
        public void setSalesUom(Integer sales_Uom) {
            this.sales_Uom = sales_Uom;
        }

        @JsonProperty("collection_in_rs")
        public Integer getCollectionInRs() {
            return collection_in_rs;
        }

        @JsonProperty("collection_in_rs")
        public void setCollectionInRs(Integer collection_in_rs) {
            this.collection_in_rs = collection_in_rs;
        }

        @JsonProperty("remarks")
        public String getRemarks() {
            return remarks;
        }

        @JsonProperty("remarks")
        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        @JsonProperty("manager_Remarks")
        public String getManagerRemarks() {
            return manager_Remarks;
        }

        @JsonProperty("manager_Remarks")
        public void setManagerRemarks(String manager_Remarks) {
            this.manager_Remarks = manager_Remarks;
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