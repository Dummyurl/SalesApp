package crm.valai.com.inventorycrm.modals;

/*
 * @author by Mohit Arora on 6/7/18.
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
public class GetSalesDailySummary implements Serializable{

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
            "salesTarget",
            "collectionTillDate",
            "tillDateSales",
            "todaysCollection",
            "todaysSales",
            "pendingOrders",
            "errorMessage",
            "errorCode",
            "Id"
    })
    public class Datum implements Serializable{

        @JsonProperty("salesTarget")
        private Integer salesTarget;
        @JsonProperty("collectionTillDate")
        private Integer collectionTillDate;
        @JsonProperty("tillDateSales")
        private Integer tillDateSales;
        @JsonProperty("todaysCollection")
        private Integer todaysCollection;
        @JsonProperty("todaysSales")
        private Integer todaysSales;
        @JsonProperty("pendingOrders")
        private Integer pendingOrders;
        @JsonProperty("errorMessage")
        private String errorMessage;
        @JsonProperty("errorCode")
        private String errorCode;
        @JsonProperty("Id")
        private Integer Id;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("salesTarget")
        public Integer getSalesTarget() {
            return salesTarget;
        }

        @JsonProperty("salesTarget")
        public void setSalesTarget(Integer salesTarget) {
            this.salesTarget = salesTarget;
        }

        @JsonProperty("collectionTillDate")
        public Integer getCollectionTillDate() {
            return collectionTillDate;
        }

        @JsonProperty("collectionTillDate")
        public void setCollectionTillDate(Integer collectionTillDate) {
            this.collectionTillDate = collectionTillDate;
        }

        @JsonProperty("tillDateSales")
        public Integer getTillDateSales() {
            return tillDateSales;
        }

        @JsonProperty("tillDateSales")
        public void setTillDateSales(Integer tillDateSales) {
            this.tillDateSales = tillDateSales;
        }

        @JsonProperty("todaysCollection")
        public Integer getTodaysCollection() {
            return todaysCollection;
        }

        @JsonProperty("todaysCollection")
        public void setTodaysCollection(Integer todaysCollection) {
            this.todaysCollection = todaysCollection;
        }

        @JsonProperty("todaysSales")
        public Integer getTodaysSales() {
            return todaysSales;
        }

        @JsonProperty("todaysSales")
        public void setTodaysSales(Integer todaysSales) {
            this.todaysSales = todaysSales;
        }

        @JsonProperty("pendingOrders")
        public Integer getPendingOrders() {
            return pendingOrders;
        }

        @JsonProperty("pendingOrders")
        public void setPendingOrders(Integer pendingOrders) {
            this.pendingOrders = pendingOrders;
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

        @JsonProperty("Id")
        public Integer getId() {
            return Id;
        }

        @JsonProperty("Id")
        public void setId(Integer Id) {
            this.Id = Id;
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
