/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package crm.valai.com.inventorycrm.prefs;

/**
 * @author by Mohit Arora on 15/9/17.
 */

public interface PreferencesHelper {

    void logOutFromPref();

    int getLogInId();

    void setLogInId(int logInId);

    String getBranch();

    void setBranch(String branch);

    Integer getBranchId();

    void setBranchId(Integer branchId);

    String getPunchDate();

    void setPunchDate(String punchDate);

    String getLogInResponse();

    void setLogInResponse(String logInResponse);

    void setDeviceId(String deviceId);

    String getDeviceId();

    void setIpAddress(String ipAddress);

    String getIpAddress();

    void setUserName(String userName);

    String getUserName();

    void setStaffBranchListResponse(String json);

    String getStaffBranchListResponse();

    void setSalesManCustomerListResponse(String json);

    String getSalesManCustomerListResponse();

    void setDataHeaderInventoryListResponse(String json);

    String getDataHeaderInventoryListResponse();

    void setDataChildInventoryListResponse(String json);

    String getDataChildInventoryListResponse();

    void setDataHeaderItemListResponse(String json);

    String getDataHeaderItemListResponse();

    void setDataChildItemListResponse(String json);

    String getDataChildItemListResponse();

    void setMyOrderResultListResponse(String json);

    String getMyOrderResultListListResponse();

    void setOrderListResponse(String json);

    String getOrderListResponse();

    String getExpenseTypeListResponse();

    void setExpenseTypeListResponse(String json);

    void setExpenseResultListResponse(String json);

    String getExpenseResultListResponse();

    String getExpenseDetailsResultListResponse();

    void setExpenseDetailsResultListResponse(String json);

    String getExpensesTypeListResponse();

    void setExpensesTypeListResponse(String json);

    String getPunchValue();

    void setPunchValue(String punchValue);

    void setCustomerDetailsListResponse(String json);

    String getCustomerDetailsListResponse();

    void setCustomerId(Integer customerId);

    Integer getCustomerId();
}