package crm.valai.com.inventorycrm.modals;

/**
 * @author by Mohit Arora on 2/5/18.
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
public class GetOrderDetailPOJO {

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
            "order_id",
            "order_detail_id",
            "item_id",
            "Item_Name",
            "quantity",
            "uom",
            "price",
            "for_Discount",
            "pl_Negative_discount",
            "pl_Postive_Discount",
            "discount",
            "discount_Reason",
            "CGST",
            "SGST",
            "errorMessage",
            "errorCode"
    })
    public class Datum {

        @JsonProperty("order_id")
        private Integer order_id;
        @JsonProperty("order_detail_id")
        private Integer order_detail_id;
        @JsonProperty("item_id")
        private Integer item_id;
        @JsonProperty("Item_Name")
        private String Item_Name;
        @JsonProperty("quantity")
        private Double quantity;
        @JsonProperty("uom")
        private Integer uom;
        @JsonProperty("price")
        private Double price;
        @JsonProperty("for_Discount")
        private Double for_Discount;
        @JsonProperty("pl_Negative_discount")
        private Double pl_Negative_discount;
        @JsonProperty("pl_Postive_Discount")
        private Double pl_Postive_Discount;
        @JsonProperty("discount")
        private Double discount;
        @JsonProperty("discount_Reason")
        private String discount_Reason;
        @JsonProperty("CGST")
        private Double CGST;
        @JsonProperty("SGST")
        private Double SGST;
        @JsonProperty("errorMessage")
        private String errorMessage;
        @JsonProperty("errorCode")
        private String errorCode;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("order_id")
        public Integer getOrderId() {
            return order_id;
        }

        @JsonProperty("order_id")
        public void setOrderId(Integer order_id) {
            this.order_id = order_id;
        }

        @JsonProperty("order_detail_id")
        public Integer getOrderDetailId() {
            return order_detail_id;
        }

        @JsonProperty("order_detail_id")
        public void setOrderDetailId(Integer order_detail_id) {
            this.order_detail_id = order_detail_id;
        }

        @JsonProperty("item_id")
        public Integer getItemId() {
            return item_id;
        }

        @JsonProperty("item_id")
        public void setItemId(Integer item_id) {
            this.item_id = item_id;
        }

        @JsonProperty("Item_Name")
        public String getItemName() {
            return Item_Name;
        }

        @JsonProperty("Item_Name")
        public void setItemName(String Item_Name) {
            this.Item_Name = Item_Name;
        }

        @JsonProperty("quantity")
        public Double getQuantity() {
            return quantity;
        }

        @JsonProperty("quantity")
        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }

        @JsonProperty("uom")
        public Integer getUom() {
            return uom;
        }

        @JsonProperty("uom")
        public void setUom(Integer uom) {
            this.uom = uom;
        }

        @JsonProperty("price")
        public Double getPrice() {
            return price;
        }

        @JsonProperty("price")
        public void setPrice(Double price) {
            this.price = price;
        }

        @JsonProperty("for_Discount")
        public Double getFor_Discount() {
            return for_Discount;
        }

        @JsonProperty("for_Discount")
        public void setFor_Discount(Double for_Discount) {
            this.for_Discount = for_Discount;
        }

        @JsonProperty("pl_Negative_discount")
        public Double getPl_Negative_discount() {
            return pl_Negative_discount;
        }

        @JsonProperty("pl_Negative_discount")
        public void setPl_Negative_discount(Double pl_Negative_discount) {
            this.pl_Negative_discount = pl_Negative_discount;
        }

        @JsonProperty("pl_Postive_Discount")
        public Double getPl_Postive_Discount() {
            return pl_Postive_Discount;
        }

        @JsonProperty("pl_Postive_Discount")
        public void setPl_Postive_Discount(Double pl_Postive_Discount) {
            this.pl_Postive_Discount = pl_Postive_Discount;
        }

        @JsonProperty("discount")
        public Double getDiscount() {
            return discount;
        }

        @JsonProperty("discount")
        public void setDiscount(Double discount) {
            this.discount = discount;
        }

        @JsonProperty("discount_Reason")
        public String getDiscount_Reason() {
            return discount_Reason;
        }

        @JsonProperty("discount_Reason")
        public void setDiscount_Reason(String discount_Reason) {
            this.discount_Reason = discount_Reason;
        }

        @JsonProperty("CGST")
        public Double getCGST() {
            return CGST;
        }

        @JsonProperty("CGST")
        public void setCGST(Double CGST) {
            this.CGST = CGST;
        }

        @JsonProperty("SGST")
        public Double getSGST() {
            return SGST;
        }

        @JsonProperty("SGST")
        public void setSGST(Double SGST) {
            this.SGST = SGST;
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


