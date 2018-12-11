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
public class GetItemPOJO {

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
            "parent_Id",
            "parent_Name",
            "inventory_classification_id",
            "Name",
            "brand_Id",
            "brand_Name",
            "item_Id",
            "item_Name",
            "is_Active",
            "pcs_Per_Weight",
            "item_Code",
            "item_Category",
            "item_Type",
            "item_price",
            "order_Flag",
            "errorMessage",
            "errorCode"
    })
    public class Datum {

        @JsonProperty("parent_Id")
        private Integer parent_Id;
        @JsonProperty("parent_Name")
        private String parent_Name;
        @JsonProperty("inventory_classification_id")
        private Integer inventory_classification_id;
        @JsonProperty("Name")
        private String Name;
        @JsonProperty("brand_Id")
        private Integer brand_Id;
        @JsonProperty("brand_Name")
        private String brand_Name;
        @JsonProperty("item_Id")
        private Integer item_Id;
        @JsonProperty("item_Name")
        private String item_Name;
        @JsonProperty("is_Active")
        private Integer is_Active;
        @JsonProperty("pcs_Per_Weight")
        private Integer pcs_Per_Weight;
        @JsonProperty("item_Code")
        private String item_Code;
        @JsonProperty("item_Category")
        private Integer item_Category;
        @JsonProperty("item_Type")
        private Integer item_Type;
        @JsonProperty("item_price")
        private Double item_price;
        @JsonProperty("order_Flag")
        private Integer order_Flag;
        @JsonProperty("errorMessage")
        private String errorMessage;
        @JsonProperty("errorCode")
        private String errorCode;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("parent_Id")
        public Integer getParentId() {
            return parent_Id;
        }

        @JsonProperty("parent_Id")
        public void setParentId(Integer parent_Id) {
            this.parent_Id = parent_Id;
        }

        @JsonProperty("parent_Name")
        public String getParentName() {
            return parent_Name;
        }

        @JsonProperty("parent_Name")
        public void setParentName(String parent_Name) {
            this.parent_Name = parent_Name;
        }

        @JsonProperty("inventory_classification_id")
        public Integer getInventoryClassificationId() {
            return inventory_classification_id;
        }

        @JsonProperty("inventory_classification_id")
        public void setInventoryClassificationId(Integer inventory_classification_id) {
            this.inventory_classification_id = inventory_classification_id;
        }

        @JsonProperty("Name")
        public String getName() {
            return Name;
        }

        @JsonProperty("Name")
        public void setName(String Name) {
            this.Name = Name;
        }

        @JsonProperty("brand_Id")
        public Integer getBrandId() {
            return brand_Id;
        }

        @JsonProperty("brand_Id")
        public void setBrandId(Integer brand_Id) {
            this.brand_Id = brand_Id;
        }

        @JsonProperty("brand_Name")
        public String getBrandName() {
            return brand_Name;
        }

        @JsonProperty("brand_Name")
        public void setBrandName(String brand_Name) {
            this.brand_Name = brand_Name;
        }

        @JsonProperty("item_Id")
        public Integer getItemId() {
            return item_Id;
        }

        @JsonProperty("item_Id")
        public void setItemId(Integer item_Id) {
            this.item_Id = item_Id;
        }

        @JsonProperty("item_Name")
        public String getItemName() {
            return item_Name;
        }

        @JsonProperty("item_Name")
        public void setItemName(String item_Name) {
            this.item_Name = item_Name;
        }

        @JsonProperty("is_Active")
        public Integer getIsActive() {
            return is_Active;
        }

        @JsonProperty("is_Active")
        public void setIsActive(Integer is_Active) {
            this.is_Active = is_Active;
        }

        @JsonProperty("pcs_Per_Weight")
        public Integer getPcsPerWeight() {
            return pcs_Per_Weight;
        }

        @JsonProperty("pcs_Per_Weight")
        public void setPcsPerWeight(Integer pcs_Per_Weight) {
            this.pcs_Per_Weight = pcs_Per_Weight;
        }

        @JsonProperty("item_Code")
        public String getItemCode() {
            return item_Code;
        }

        @JsonProperty("item_Code")
        public void setItemCode(String item_Code) {
            this.item_Code = item_Code;
        }

        @JsonProperty("item_Category")
        public Integer getItemCategory() {
            return item_Category;
        }

        @JsonProperty("item_Category")
        public void setItemCategory(Integer item_Category) {
            this.item_Category = item_Category;
        }

        @JsonProperty("item_Type")
        public Integer getItemType() {
            return item_Type;
        }

        @JsonProperty("item_Type")
        public void setItemType(Integer item_Type) {
            this.item_Type = item_Type;
        }

        @JsonProperty("item_price")
        public Double getItemPrice() {
            return item_price;
        }

        @JsonProperty("item_price")
        public void setItemPrice(Double item_price) {
            this.item_price = item_price;
        }

        @JsonProperty("order_Flag")
        public Integer getOrderFlag() {
            return order_Flag;
        }

        @JsonProperty("order_Flag")
        public void setOrderFlag(Integer order_Flag) {
            this.order_Flag = order_Flag;
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