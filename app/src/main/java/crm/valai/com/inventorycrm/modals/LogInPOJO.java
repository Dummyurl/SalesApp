package crm.valai.com.inventorycrm.modals;

/*
 * @author by Mohit Arora on 29/3/18.
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
public class LogInPOJO implements Serializable{

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
            "login_Id",
            "token",
            "comp_Id",
            "staff_Id",
            "staff_Name",
            "role_Id",
            "branch_Id",
            "reporting_Manager",
            "login_Code",
            "user_Type",
            "message",
            "errorMessage",
            "errorCode"
    })
    public class Datum implements Serializable{

        @JsonProperty("login_Id")
        private Integer login_Id;
        @JsonProperty("token")
        private String token;
        @JsonProperty("comp_Id")
        private Integer comp_Id;
        @JsonProperty("staff_Id")
        private Integer staff_Id;
        @JsonProperty("staff_Name")
        private String staff_Name;
        @JsonProperty("role_Id")
        private Integer role_Id;
        @JsonProperty("branch_Id")
        private Integer branch_Id;
        @JsonProperty("reporting_Manager")
        private Integer reporting_Manager;
        @JsonProperty("login_Code")
        private Integer login_Code;
        @JsonProperty("user_Type")
        private Integer user_Type;
        @JsonProperty("message")
        private String message;
        @JsonProperty("errorMessage")
        private String errorMessage;
        @JsonProperty("errorCode")
        private String errorCode;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("login_Id")
        public Integer getLoginId() {
            return login_Id;
        }

        @JsonProperty("login_Id")
        public void setLoginId(Integer login_Id) {
            this.login_Id = login_Id;
        }

        @JsonProperty("token")
        public String getToken() {
            return token;
        }

        @JsonProperty("token")
        public void setToken(String token) {
            this.token = token;
        }

        @JsonProperty("comp_Id")
        public Integer getCompId() {
            return comp_Id;
        }

        @JsonProperty("comp_Id")
        public void setCompId(Integer comp_Id) {
            this.comp_Id = comp_Id;
        }

        @JsonProperty("staff_Id")
        public Integer getStaffId() {
            return staff_Id;
        }

        @JsonProperty("staff_Id")
        public void setStaffId(Integer staff_Id) {
            this.staff_Id = staff_Id;
        }

        @JsonProperty("staff_Name")
        public String getStaffName() {
            return staff_Name;
        }

        @JsonProperty("staff_Name")
        public void setStaffName(String staff_Name) {
            this.staff_Name = staff_Name;
        }

        @JsonProperty("role_Id")
        public Integer getRoleId() {
            return role_Id;
        }

        @JsonProperty("role_Id")
        public void setRoleId(Integer role_Id) {
            this.role_Id = role_Id;
        }

        @JsonProperty("branch_Id")
        public Integer getBranchId() {
            return branch_Id;
        }

        @JsonProperty("branch_Id")
        public void setBranchId(Integer branch_Id) {
            this.branch_Id = branch_Id;
        }

        @JsonProperty("reporting_Manager")
        public Integer getReportingManager() {
            return reporting_Manager;
        }

        @JsonProperty("reporting_Manager")
        public void setReportingManager(Integer reporting_Manager) {
            this.reporting_Manager = reporting_Manager;
        }

        @JsonProperty("login_Code")
        public Integer getLoginCode() {
            return login_Code;
        }

        @JsonProperty("login_Code")
        public void setLoginCode(Integer login_Code) {
            this.login_Code = login_Code;
        }

        @JsonProperty("user_Type")
        public Integer getUserType() {
            return user_Type;
        }

        @JsonProperty("user_Type")
        public void setUserType(Integer user_Type) {
            this.user_Type = user_Type;
        }

        @JsonProperty("message")
        public String getMessage() {
            return message;
        }

        @JsonProperty("message")
        public void setMessage(String message) {
            this.message = message;
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