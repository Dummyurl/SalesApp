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
public class GetQuantityAndRatePOJO {

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
            "quantityMT",
            "quantityPCS",
            "priceMT",
            "pricePCS",
            "errorMessage",
            "errorCode",
            "Id"
    })
    public class Datum {

        @JsonProperty("quantityMT")
        private Double quantityMT;
        @JsonProperty("quantityPCS")
        private Double quantityPCS;
        @JsonProperty("priceMT")
        private Double priceMT;
        @JsonProperty("pricePCS")
        private Double pricePCS;
        @JsonProperty("errorMessage")
        private String errorMessage;
        @JsonProperty("errorCode")
        private String errorCode;
        @JsonProperty("Id")
        private Object Id;
        private Double quantity;
        private Double totalAmount;

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }

        public Double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
        }

        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("quantityMT")
        public Double getQuantityMT() {
            return quantityMT;
        }

        @JsonProperty("quantityMT")
        public void setQuantityMT(Double quantityMT) {
            this.quantityMT = quantityMT;
        }

        @JsonProperty("quantityPCS")
        public Double getQuantityPCS() {
            return quantityPCS;
        }

        @JsonProperty("quantityPCS")
        public void setQuantityPCS(Double quantityPCS) {
            this.quantityPCS = quantityPCS;
        }

        @JsonProperty("priceMT")
        public Double getPriceMT() {
            return priceMT;
        }

        @JsonProperty("priceMT")
        public void setPriceMT(Double priceMT) {
            this.priceMT = priceMT;
        }

        @JsonProperty("pricePCS")
        public Double getPricePCS() {
            return pricePCS;
        }

        @JsonProperty("pricePCS")
        public void setPricePCS(Double pricePCS) {
            this.pricePCS = pricePCS;
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
        public Object getId() {
            return Id;
        }

        @JsonProperty("Id")
        public void setId(Object Id) {
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