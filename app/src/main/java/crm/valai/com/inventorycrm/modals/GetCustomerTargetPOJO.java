package crm.valai.com.inventorycrm.modals;

/*
 * @author by Mohit Arora on 28/6/18.
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
public class GetCustomerTargetPOJO {

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
            "cluster_Id",
            "cluster_Name",
            "target_Quantity",
            "errorMessage",
            "errorCode"
    })
    public class Datum {

        @JsonProperty("customer_Id")
        private Integer customer_Id;
        @JsonProperty("cluster_Id")
        private Integer cluster_Id;
        @JsonProperty("cluster_Name")
        private String cluster_Name;
        @JsonProperty("target_Quantity")
        private Integer target_Quantity;
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

        @JsonProperty("cluster_Id")
        public Integer getClusterId() {
            return cluster_Id;
        }

        @JsonProperty("cluster_Id")
        public void setClusterId(Integer cluster_Id) {
            this.cluster_Id = cluster_Id;
        }

        @JsonProperty("cluster_Name")
        public String getClusterName() {
            return cluster_Name;
        }

        @JsonProperty("cluster_Name")
        public void setClusterName(String cluster_Name) {
            this.cluster_Name = cluster_Name;
        }

        @JsonProperty("target_Quantity")
        public Integer getTargetQuantity() {
            return target_Quantity;
        }

        @JsonProperty("target_Quantity")
        public void setTargetQuantity(Integer target_Quantity) {
            this.target_Quantity = target_Quantity;
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