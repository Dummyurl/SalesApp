package crm.valai.com.inventorycrm.network;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author by Mohit Arora on 30/3/18.
 */

public class JsonClient {

    public JsonClient() {
        //empty constructor
    }

    public JSONObject getLogInJson(String userName, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_Name", userName);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getPunchInOutJson(Integer compId, Integer staffId, int punchType, String userName,
                                        String ipAddress, String deviceId, Integer loginId, String token) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("staff_Id", staffId);
            jsonObject.put("punch_Type", punchType);
            jsonObject.put("userName", userName);
            jsonObject.put("ip_Address", ipAddress);
            jsonObject.put("device_Id", deviceId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getStaffBranchJson(Integer compId, Integer staffId, Integer loginId, String token, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("staff_Id", staffId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getOrderJson(Integer compId, Integer staffId, String orderDate, String deviceId, Integer loginId, String token, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("staff_Id", staffId);
            jsonObject.put("order_Date", orderDate);
            jsonObject.put("device_Id", deviceId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getSalesManCustomerJson(Integer compId, Integer staffId, Integer loginId, String token) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("staff_Id", staffId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getCustomerDetailsJson(Integer compId, Integer loginId, Integer customerId, String token) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("customer_Id", customerId);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject insertUpdateCustomerDetailsJson(Integer compId, String mode, Integer login_Id, String token, Integer customer_Id, Integer salesman, String reffered_By, String contact_Person_Name,
                                                      Integer customer_Type, String contact_Person_designation, String customer_Name, String customer_Phone_Number, String contact_Person_Mobile_Number, String customer_Email,
                                                      String contact_Person_Email, String nature_Of_Buisness, String annual_Turnover, String credit_Limit, Integer credit_days, String mode_Of_Payment,
                                                      String current_Address, String current_State, String current_City, String current_Country, String shipping_Address, Integer shipping_City, Integer shipping_State, Integer shipping_Country,
                                                      String current_Zipcode, String shipping_Zipcode, String pan_Number, String tin_Number, String gst_Number, Integer status, String userId, String buisness_Start_Date) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("mode", mode);
            jsonObject.put("login_Id", login_Id);
            jsonObject.put("token", token);
            jsonObject.put("customer_Id", customer_Id);
            jsonObject.put("salesman", salesman);
            jsonObject.put("reffered_By", reffered_By);
            jsonObject.put("contact_Person_Name", contact_Person_Name);
            jsonObject.put("customer_Type", customer_Type);
            jsonObject.put("contact_Person_designation", contact_Person_designation);
            jsonObject.put("customer_Name", customer_Name);
            jsonObject.put("customer_Phone_Number", customer_Phone_Number);
            jsonObject.put("contact_Person_Mobile_Number", contact_Person_Mobile_Number);
            jsonObject.put("customer_Email", customer_Email);
            jsonObject.put("contact_Person_Email", contact_Person_Email);
            jsonObject.put("nature_Of_Buisness", nature_Of_Buisness);
            jsonObject.put("annual_Turnover", annual_Turnover);
            jsonObject.put("credit_Limit", credit_Limit);
            jsonObject.put("credit_days", credit_days);
            jsonObject.put("mode_Of_Payment", mode_Of_Payment);
            jsonObject.put("current_Address", current_Address);
            jsonObject.put("current_State", current_State);
            jsonObject.put("current_City", current_City);
            jsonObject.put("current_Country", current_Country);
            jsonObject.put("shipping_Address", shipping_Address);
            jsonObject.put("shipping_City", shipping_City);
            jsonObject.put("shipping_State", shipping_State);
            jsonObject.put("shipping_Country", shipping_Country);
            jsonObject.put("current_Zipcode", current_Zipcode);
            jsonObject.put("shipping_Zipcode", shipping_Zipcode);
            jsonObject.put("pan_Number", pan_Number);
            jsonObject.put("tin_Number", tin_Number);
            jsonObject.put("gst_Number", gst_Number);
            jsonObject.put("status", status);
            jsonObject.put("userId", userId);
            jsonObject.put("buisness_Start_Date", buisness_Start_Date);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getSalesManJson(Integer compId, Integer id, Integer loginId, String token, Integer isActive, Integer parent_Id, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("Id", id);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("is_Active", isActive);
            jsonObject.put("parent_Id", parent_Id);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getInventoryCategoryJson(Integer compId, Integer id, Integer loginId, String token, Integer isActive, Integer parent_Id, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("Id", id);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("is_Active", isActive);
            jsonObject.put("parent_Id", parent_Id);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getItemJson(Integer compId, Integer loginId, String token, Integer parentId, Integer inventoryClassificationId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("parent_Id", parentId);
            jsonObject.put("inventory_classification_id", inventoryClassificationId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getQuantityAndRateJson(Integer compId, Integer loginId, String token, Integer branchId, Integer itemId, Integer categoryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("branch_Id", branchId);
            jsonObject.put("item_Id", itemId);
            jsonObject.put("category_Id", categoryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getInsertOrderJson(Integer compId, Integer loginId, String token, Integer branchId, Integer customerId,
                                         Integer staffId, String orderRefNumber, String staffRemarks, String deviceId, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("branch_Id", branchId);
            jsonObject.put("customer_Id", customerId);
            jsonObject.put("staff_Id", staffId);
            jsonObject.put("order_Ref_Number", orderRefNumber);
            jsonObject.put("staff_Remarks", staffRemarks);
            jsonObject.put("device_Id", deviceId);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getInsertOrderTaxJson(Integer compId, Integer loginId, String token, String mode,
                                            Integer orderId, Integer taxId, Double amount, String deviceId, String userName, String ipAddress,
                                            Double forDiscount, Double plNegativeDiscount, Double plPositiveDiscount, Double discount,
                                            String discountReason) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
            jsonObject.put("order_Id", orderId);
            jsonObject.put("tax_Id", taxId);
            jsonObject.put("amount", amount);
            jsonObject.put("device_Id", deviceId);
            jsonObject.put("userName", userName);
            jsonObject.put("ip_Address", ipAddress);
            jsonObject.put("for_Discount", forDiscount);
            jsonObject.put("pl_Negative_discount", plNegativeDiscount);
            jsonObject.put("pl_Positive_discount", plPositiveDiscount);
            jsonObject.put("discount", discount);
            jsonObject.put("discount_Reason", discountReason);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getInsertOrderDetailJson(Integer compId, Integer loginId, String token, String mode,
                                               Integer orderId, Integer itemId, Double quantity, Integer uom, Double price,
                                               String deviceId, String userName, String ipAddress) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
            jsonObject.put("order_Id", orderId);
            jsonObject.put("item_id", itemId);
            jsonObject.put("quantity", quantity);
            jsonObject.put("uom", uom);
            jsonObject.put("price", price);
            jsonObject.put("device_Id", deviceId);
            jsonObject.put("userName", userName);
            jsonObject.put("ip_Address", ipAddress);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getOrderDetailJson(Integer loginId, String token, Integer compId, Integer orderId, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("comp_Id", compId);
            jsonObject.put("order_Id", orderId);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject insertUpdateExpenseJson(Integer compId, Integer loginId, String token, String mode, Integer staffId, Integer expenseId, Integer staffid, String name, String userName,
                                              String ipAddress, String deviceId, String staffRemarks) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_id", compId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
            jsonObject.put("staff_Id", staffId);
            jsonObject.put("expense_id", expenseId);
            jsonObject.put("staff_id", staffid);
            jsonObject.put("name", name);
            jsonObject.put("userName", userName);
            jsonObject.put("ip_Address", ipAddress);
            jsonObject.put("device_Id", deviceId);
            jsonObject.put("staff_remarks", staffRemarks);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject insertExpenseDetailsJson(Integer compId, Integer loginId, String token, String mode, Integer staffId, Integer expenseId, Integer staffid, String name, String userName,
                                               String ipAddress, String deviceId, String staffRemarks, String expense_detail_id, String fromDate, String toDate, String from_location, String to_location,
                                               String bill_amount, String remarks, Integer expense_type) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_id", compId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
            jsonObject.put("staff_Id", staffId);
            jsonObject.put("expense_id", expenseId);
            jsonObject.put("staff_id", staffid);
            jsonObject.put("name", name);
            jsonObject.put("userName", userName);
            jsonObject.put("ip_Address", ipAddress);
            jsonObject.put("device_Id", deviceId);
            jsonObject.put("staff_remarks", staffRemarks);
            jsonObject.put("expense_detail_id", expense_detail_id);
            jsonObject.put("from_Date", fromDate);
            jsonObject.put("to_Date", toDate);
            jsonObject.put("from_location", from_location);
            jsonObject.put("to_location", to_location);
            jsonObject.put("bill_amount", bill_amount);
            jsonObject.put("remarks", remarks);
            jsonObject.put("expense_type", expense_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getExpenseStaffJson(Integer comp_id, Integer staff_id, Integer expense_id, Integer status, Integer login_Id, String token,
                                          String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_id", comp_id);
            jsonObject.put("staff_id", staff_id);
            jsonObject.put("expense_id", expense_id);
            jsonObject.put("status", status);
            jsonObject.put("login_Id", login_Id);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getExpenseStaffDetailJson(Integer comp_id, Integer staff_id, Integer expense_id, Integer status, Integer login_Id, String token,
                                                String mode, Integer expense_detail_id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_id", comp_id);
            jsonObject.put("staff_id", staff_id);
            jsonObject.put("expense_id", expense_id);
            jsonObject.put("status", status);
            jsonObject.put("login_Id", login_Id);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
            jsonObject.put("expense_detail_id", expense_detail_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getLeaveTypeJson(Integer comp_id, Integer login_Id, String token, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_id", comp_id);
            jsonObject.put("login_Id", login_Id);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getExpenseStatusJson(Integer comp_id, Integer login_Id, String token, String mode, Integer isActive) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", comp_id);
            jsonObject.put("login_Id", login_Id);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
            jsonObject.put("is_Active", isActive);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject insertLeaveJson(Integer comp_Id, Integer year_Id, Integer leave_Id, Integer staffId, Integer noOfDay, Integer login_Id,
                                      String token, Integer TotalLeave, Integer leaveTaken, Integer designation_Id, String from_Date, String to_Date,
                                      Integer document_Need, String addressOnLeave, String remarks, String device_Id, String ipAddress, String userNameUpdate, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", comp_Id);
            jsonObject.put("year_Id", year_Id);
            jsonObject.put("leave_Id", leave_Id);
            jsonObject.put("staffId", staffId);
            jsonObject.put("noOfDay", noOfDay);
            jsonObject.put("login_Id", login_Id);
            jsonObject.put("token", token);
            jsonObject.put("TotalLeave", TotalLeave);
            jsonObject.put("leaveTaken", leaveTaken);
            jsonObject.put("designation_Id", designation_Id);
            jsonObject.put("from_Date", from_Date);
            jsonObject.put("to_Date", to_Date);
            jsonObject.put("document_Need", document_Need);
            jsonObject.put("addressOnLeave", addressOnLeave);
            jsonObject.put("remarks", remarks);
            jsonObject.put("device_Id", device_Id);
            jsonObject.put("ipAddress", ipAddress);
            jsonObject.put("userNameUpdate", userNameUpdate);
            jsonObject.put("mode", mode);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getLeaveStatusJson(Integer comp_Id, Integer year_Id, Integer status_Of_Leave, Integer login_Id, String token,
                                         Integer staffId, Integer application_Id, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", comp_Id);
            jsonObject.put("year_Id", year_Id);
            jsonObject.put("status_Of_Leave", status_Of_Leave);
            jsonObject.put("login_Id", login_Id);
            jsonObject.put("token", token);
            jsonObject.put("staffId", staffId);
            jsonObject.put("application_Id", application_Id);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getSubmitForApprovalJson(Integer comp_Id, Integer login_Id, String token, String mode,
                                               String userName, String ipAddress, String deviceId, Integer expenseId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", comp_Id);
            jsonObject.put("login_Id", login_Id);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
            jsonObject.put("userName", userName);
            jsonObject.put("ip_address", ipAddress);
            jsonObject.put("device_Id", deviceId);
            jsonObject.put("expense_id", expenseId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject deleteExpenseDetailsJson(Integer comp_Id, Integer login_Id, String token, String mode,
                                               String userName, String ipAddress, String deviceId, Integer expenseId, Integer expenseDetailId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", comp_Id);
            jsonObject.put("login_Id", login_Id);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
            jsonObject.put("userName", userName);
            jsonObject.put("ip_address", ipAddress);
            jsonObject.put("device_Id", deviceId);
            jsonObject.put("expense_id", expenseId);
            jsonObject.put("expense_detail_id", expenseDetailId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getCustomerTypeJson(Integer loginId, String token, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getCountryJson(Integer loginId, String token) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getStateJson(Integer loginId, String token, Integer countryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("country_Id", countryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getCityJson(Integer loginId, String token, Integer countryId, Integer stateId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("country_Id", countryId);
            jsonObject.put("state_Id", stateId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getCustomerTargetJson(Integer loginId, String token, Integer comp_Id, Integer customer_Id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("comp_Id", comp_Id);
            jsonObject.put("customer_Id", customer_Id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject insertUpdateCustomerTargetJson(Integer loginId, String token, Integer comp_Id, Integer customer_Id, Integer cluster_Id, Double target_Quantity,
                                                     String userName, String ipAddress, String device_Id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("comp_Id", comp_Id);
            jsonObject.put("customer_Id", customer_Id);
            jsonObject.put("cluster_Id", cluster_Id);
            jsonObject.put("target_Quantity", target_Quantity);
            jsonObject.put("userName", userName);
            jsonObject.put("ipAdress", ipAddress);
            jsonObject.put("device_Id", device_Id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject insertUpdateEmployDailyStatusJson(Integer loginId, String token, Integer comp_Id, Integer staff_id, Integer customer_Id,
                                                        String visited_date, Integer report_id, String mode, String customer_name, String location_name,
                                                        Integer mode_of_communication, String purpose_of_visit, Integer pending_Orders, Integer pending_Order_Uom,
                                                        Integer sales, Integer sales_Uom, Integer collection_in_rs, String remarks, String userName, String ip_address,
                                                        String contact_Number, String manager_Remarks) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("comp_id", comp_Id);
            jsonObject.put("staff_id", staff_id);
            jsonObject.put("customer_Id", customer_Id);
            jsonObject.put("visited_date", visited_date);
            jsonObject.put("report_id", report_id);
            jsonObject.put("mode", mode);
            jsonObject.put("customer_name", customer_name);
            jsonObject.put("location_name", location_name);
            jsonObject.put("mode_of_communication", mode_of_communication);
            jsonObject.put("purpose_of_visit", purpose_of_visit);
            jsonObject.put("pending_Orders", pending_Orders);
            jsonObject.put("pending_Order_Uom", pending_Order_Uom);
            jsonObject.put("sales", sales);
            jsonObject.put("sales_Uom", sales_Uom);
            jsonObject.put("collection_in_rs", collection_in_rs);
            jsonObject.put("remarks", remarks);
            jsonObject.put("userName", userName);
            jsonObject.put("ip_address", ip_address);
            jsonObject.put("contact_Number", contact_Number);
            jsonObject.put("manager_Remarks", manager_Remarks);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getStaffDailyTrackerJson(Integer loginId, String token, Integer comp_Id, Integer staff_id, Integer customer_Id,
                                               String visited_date_to, String visited_date_from) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("comp_id", comp_Id);
            jsonObject.put("staff_id", staff_id);
            jsonObject.put("customer_Id", customer_Id);
            jsonObject.put("visited_date_to", visited_date_to);
            jsonObject.put("visited_date_from", visited_date_from);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject deleteSalesJson(Integer loginId, String token, Integer comp_id, Integer staff_id, String visited_date, Integer report_id,
                                      String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("comp_id", comp_id);
            jsonObject.put("staff_id", staff_id);
            jsonObject.put("visited_date", visited_date);
            jsonObject.put("report_id", report_id);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getStaffDailySummaryJson(Integer loginId, String token, Integer compId, Integer staffId, String visited_date) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("comp_id", compId);
            jsonObject.put("staff_id", staffId);
            jsonObject.put("visited_date", visited_date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getPurposeOfVisitJson(Integer loginId, String token, String mode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getLeaveDeleteJson(Integer compId, Integer loginId, String token, Integer applicationId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comp_Id", compId);
            jsonObject.put("login_Id", loginId);
            jsonObject.put("token", token);
            jsonObject.put("application_Id", applicationId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}