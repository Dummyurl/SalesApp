package crm.valai.com.inventorycrm.network;

import crm.valai.com.inventorycrm.modals.GetCityPOJO;
import crm.valai.com.inventorycrm.modals.GetCountryPOJO;
import crm.valai.com.inventorycrm.modals.GetCustomerDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetCustomerDocumentPath;
import crm.valai.com.inventorycrm.modals.GetCustomerTargetPOJO;
import crm.valai.com.inventorycrm.modals.GetExpenseDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetExpensePOJO;
import crm.valai.com.inventorycrm.modals.GetExpenseTypePOJO;
import crm.valai.com.inventorycrm.modals.GetInventoryClassificationPOJO;
import crm.valai.com.inventorycrm.modals.GetItemPOJO;
import crm.valai.com.inventorycrm.modals.GetLeaveStatusPOJO;
import crm.valai.com.inventorycrm.modals.GetLeaveTypePOJO;
import crm.valai.com.inventorycrm.modals.GetOrderDetailPOJO;
import crm.valai.com.inventorycrm.modals.GetOrdersPOJO;
import crm.valai.com.inventorycrm.modals.GetQuantityAndRatePOJO;
import crm.valai.com.inventorycrm.modals.GetSalesDailySummary;
import crm.valai.com.inventorycrm.modals.GetSalesmanCustomerPOJO;
import crm.valai.com.inventorycrm.modals.GetStaffBranchPOJO;
import crm.valai.com.inventorycrm.modals.GetStaffDailyTrackerPOJO;
import crm.valai.com.inventorycrm.modals.GetStatePOJO;
import crm.valai.com.inventorycrm.modals.InsertUpdateItemPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.modals.PunchInOutPOJO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestClient {

    @POST("./Login/getLogin")
    Call<LogInPOJO> logIn(@Body RequestBody logInJsonData);

    @POST("./Login/InsertPunchIn")
    Call<PunchInOutPOJO> punchInOut(@Body RequestBody punchInJsonData);

    @POST("./Order/getOrders")
    Call<GetOrdersPOJO> getOrders(@Body RequestBody getOrderJsonData);

    @POST("./Order/getOrders")
    Call<GetOrderDetailPOJO> getOrderDetail(@Body RequestBody getOrderJsonData);

    @POST("./Employee/getStaffBranch")
    Call<GetStaffBranchPOJO> getStaffBranch(@Body RequestBody getStaffBranchJsonData);

    @POST("./Customer/getSalesmanCustomer")
    Call<GetSalesmanCustomerPOJO> getSalesmanCustomer(@Body RequestBody getSalesmanCustomerJsonData);

    @POST("./Customer/getCustomer")
    Call<GetCustomerDetailsPOJO> getCustomerDetails(@Body RequestBody getCustomerDetails);

    @POST("./Customer/insertUpdateCustomer")
    Call<InsertUpdateItemPOJO> insertUpdateCustomer(@Body RequestBody insertUpdate);

    @POST("./InventoryClassification/getInventoryClassification")
    Call<GetInventoryClassificationPOJO> getInventoryClassification(@Body RequestBody getInventoryClassificationJsonData);

    @POST("./Item/getItem")
    Call<GetItemPOJO> getItem(@Body RequestBody getItemJsonData);

    @POST("./Order/getQuantityAndRate")
    Call<GetQuantityAndRatePOJO> getQuantityAndRate(@Body RequestBody getQuantityAndRateJsonData);

    @POST("./Order/InsertUpdateItem")
    Call<InsertUpdateItemPOJO> insertUpdateItem(@Body RequestBody insertUpdateItemJsonData);

    @POST("./Expense/InsertUpdateExpense")
    Call<InsertUpdateItemPOJO> insertUpdateExpense(@Body RequestBody insertUpdateExpenseJsonData);

    @POST("./Expense/getExpense")
    Call<GetExpensePOJO> getExpense(@Body RequestBody getExpense);

    @POST("./Expense/getExpense")
    Call<GetExpenseDetailsPOJO> getExpenseDetails(@Body RequestBody getExpense);

    @Multipart
    @POST("./ExpenseImageupload")
    Call<InsertUpdateItemPOJO> insertImage(@Part MultipartBody.Part imageFile,
                                           @Part MultipartBody.Part compId,
                                           @Part MultipartBody.Part expenseId,
                                           @Part MultipartBody.Part expenseDetailId,
                                           @Part MultipartBody.Part loginid,
                                           @Part MultipartBody.Part token);

    @POST("./MasterSetting/getMasterData")
    Call<GetExpenseTypePOJO> getExpenseType(@Body RequestBody getExpenseType);

    @POST("./Leave/getLeave")
    Call<GetLeaveTypePOJO> getLeaveType(@Body RequestBody getLeaveType);

    @POST("./Leave/InsertUpdateLeaveApplication")
    Call<InsertUpdateItemPOJO> insertUpdateLeaveApplication(@Body RequestBody insertUpdateLeaveJsonData);

    @POST("./Leave/GetStaffLeaveStatus")
    Call<GetLeaveStatusPOJO> getLeaveStatus(@Body RequestBody getLeaveStatus);

    @POST("./Leave/deleteLeaveApplication")
    Call<InsertUpdateItemPOJO> getLeaveDelete(@Body RequestBody leaveDelete);

    @POST("./MasterSetting/getCountry")
    Call<GetCountryPOJO> getCountry(@Body RequestBody body);

    @POST("./MasterSetting/getState")
    Call<GetStatePOJO> getState(@Body RequestBody body);

    @POST("./MasterSetting/getCity")
    Call<GetCityPOJO> getCity(@Body RequestBody body);

    @POST("./Customer/getCustomerTarget")
    Call<GetCustomerTargetPOJO> getCustomerTarget(@Body RequestBody insertUpdate);

    @POST("./Customer/insertUpdateCustomerTarget")
    Call<InsertUpdateItemPOJO> insertUpdateCustomerTarget(@Body RequestBody insertUpdate);

    @POST("./Employee/insertUpdateEmployeDailyStatus")
    Call<InsertUpdateItemPOJO> insertUpdateEmployDailyStatus(@Body RequestBody insertUpdate);

    @POST("./Employee/getStaffDailyTracker")
    Call<GetStaffDailyTrackerPOJO> getStaffDailyTracker(@Body RequestBody insertUpdate);

    @POST("./Employee/getStaffDailyTrackerSummary")
    Call<GetSalesDailySummary> getStaffDailyTrackerSummary(@Body RequestBody insertUpdate);

    @Multipart
    @POST("./Customer/DocumentUpdate")
    Call<InsertUpdateItemPOJO> uploadDocuments(@Part MultipartBody.Part fileBody,
                                               @Header("compId") String compId,
                                               @Header("customerId") String customerId,
                                               @Header("loginid") String loginid,
                                               @Header("token") String token,
                                               @Header("documentType") String documentType);

    @POST("./MasterSetting/getMasterData")
    Call<GetCustomerDocumentPath> getCustomerDocumentPath(@Body RequestBody insertUpdate);
}