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

import android.content.Context;
import android.content.SharedPreferences;

import crm.valai.com.inventorycrm.utils.CommonUtils;

public class AppPreferencesHelper implements PreferencesHelper {


    private static final String PREF_KEY_LOGIN_ID = "PREF_KEY_LOGIN_ID";

    private static final String PREF_KEY_BRANCH_NAME = "PREF_KEY_BRANCH_NAME";

    private static final String PREF_KEY_BRANCH_ID = "PREF_KEY_BRANCH_ID";

    private static final String PREF_KEY_PUNCH_DATE = "PREF_KEY_PUNCH_DATE";

    private static final String PREF_KEY_PUNCH_VALUE = "PREF_KEY_PUNCH_VALUE";

    private static final String PREF_KEY_LOGIN_RESPONSE = "PREF_KEY_LOGIN_RESPONSE";

    private static final String PREF_KEY_DEVICE_ID = "PREF_KEY_DEVICE_ID";

    private static final String PREF_KEY_IP_ADDRESS = "PREF_KEY_IP_ADDRESS";

    private static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME";

    private static final String PREF_KEY_STAFF_BRANCH_LIST = "PREF_KEY_STAFF_BRANCH_LIST";

    private static final String PREF_KEY_SALESMAN_CUSTOMER_LIST = "PREF_KEY_SALESMAN_CUSTOMER_LIST";

    private static final String PREF_KEY_DATA_HEADER_INVENTORY_LIST = "PREF_KEY_DATA_HEADER_INVENTORY_LIST";

    private static final String PREF_KEY_DATA_CHILD_INVENTORY_LIST = "PREF_KEY_DATA_CHILD_INVENTORY_LIST";

    private static final String PREF_KEY_DATA_HEADER_ITEM_LIST = "PREF_KEY_DATA_HEADER_ITEM_LIST";

    private static final String PREF_KEY_DATA_CHILD_ITEM_LIST = "PREF_KEY_DATA_CHILD_ITEM_LIST";

    private static final String PREF_KEY_MY_ORDER_RESULT_LIST = "PREF_KEY_MY_ORDER_RESULT_LIST";

    private static final String PREF_KEY_GET_ORDER_RESULT_LIST = "PREF_KEY_GET_ORDER_RESULT_LIST";

    private static final String PREF_KEY_EXPENSE_TYPE_LIST = "PREF_KEY_EXPENSE_TYPE_LIST";

    private static final String PREF_KEY_EXPENSE_RESULT_LIST = "PREF_KEY_EXPENSE_RESULT_LIST";

    private static final String PREF_KEY_EXPENSE_DETAILS_RESULT_LIST = "PREF_KEY_EXPENSE_DETAILS_RESULT_LIST";

    private static final String PREF_KEY_EXPENSES_TYPE_LIST = "PREF_KEY_EXPENSES_TYPE_LIST";

    private static final String PREF_KEY_CUSTOMER_DETAILS_LIST = "PREF_KEY_CUSTOMER_DETAILS_LIST";

    private static final String PREF_KEY_CUSTOMER_ID = "PREF_KEY_CUSTOMER_ID";

    private final SharedPreferences mPrefs;

    public AppPreferencesHelper(Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void logOutFromPref() {
        mPrefs.edit().putInt(PREF_KEY_LOGIN_ID, 0).apply();
        mPrefs.edit().putString(PREF_KEY_BRANCH_NAME, null).apply();
        mPrefs.edit().putString(PREF_KEY_BRANCH_ID, null).apply();
        mPrefs.edit().putString(PREF_KEY_PUNCH_DATE, CommonUtils.getCurrentDate()).apply();
        mPrefs.edit().putString(PREF_KEY_LOGIN_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_DEVICE_ID, null).apply();
        mPrefs.edit().putString(PREF_KEY_IP_ADDRESS, null).apply();
        mPrefs.edit().putString(PREF_KEY_USER_NAME, null).apply();
        mPrefs.edit().putString(PREF_KEY_STAFF_BRANCH_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_SALESMAN_CUSTOMER_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_DATA_HEADER_INVENTORY_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_DATA_CHILD_INVENTORY_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_DATA_HEADER_ITEM_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_DATA_CHILD_ITEM_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_MY_ORDER_RESULT_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_GET_ORDER_RESULT_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_EXPENSE_TYPE_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_EXPENSE_RESULT_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_EXPENSE_DETAILS_RESULT_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_EXPENSES_TYPE_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_PUNCH_VALUE, null).apply();
        mPrefs.edit().putString(PREF_KEY_CUSTOMER_DETAILS_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_CUSTOMER_ID, null).apply();
    }

    @Override
    public int getLogInId() {
        return mPrefs.getInt(PREF_KEY_LOGIN_ID, 0);
    }

    @Override
    public void setLogInId(int logInId) {
        mPrefs.edit().putInt(PREF_KEY_LOGIN_ID, logInId).apply();
    }

    @Override
    public String getBranch() {
        return mPrefs.getString(PREF_KEY_BRANCH_NAME, null);
    }

    @Override
    public void setBranch(String branch) {
        mPrefs.edit().putString(PREF_KEY_BRANCH_NAME, branch).apply();
    }

    @Override
    public Integer getBranchId() {
        return mPrefs.getInt(PREF_KEY_BRANCH_ID, 0);
    }

    @Override
    public void setBranchId(Integer branchId) {
        mPrefs.edit().putInt(PREF_KEY_BRANCH_ID, branchId).apply();
    }

    @Override
    public String getPunchDate() {
        return mPrefs.getString(PREF_KEY_PUNCH_DATE, CommonUtils.getCurrentDate());
    }

    @Override
    public void setPunchDate(String punchDate) {
        mPrefs.edit().putString(PREF_KEY_PUNCH_DATE, punchDate).apply();
    }

    @Override
    public String getLogInResponse() {
        return mPrefs.getString(PREF_KEY_LOGIN_RESPONSE, CommonUtils.getCurrentDate());
    }

    @Override
    public void setLogInResponse(String logInResponse) {
        mPrefs.edit().putString(PREF_KEY_LOGIN_RESPONSE, logInResponse).apply();
    }

    @Override
    public void setDeviceId(String deviceId) {
        mPrefs.edit().putString(PREF_KEY_DEVICE_ID, deviceId).apply();
    }

    @Override
    public String getDeviceId() {
        return mPrefs.getString(PREF_KEY_DEVICE_ID, null);
    }

    @Override
    public void setIpAddress(String ipAddress) {
        mPrefs.edit().putString(PREF_KEY_IP_ADDRESS, ipAddress).apply();
    }

    @Override
    public String getIpAddress() {
        return mPrefs.getString(PREF_KEY_IP_ADDRESS, null);
    }

    @Override
    public void setUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_USER_NAME, userName).apply();
    }

    @Override
    public String getUserName() {
        return mPrefs.getString(PREF_KEY_USER_NAME, null);
    }

    @Override
    public void setStaffBranchListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_STAFF_BRANCH_LIST, json).apply();
    }

    @Override
    public String getStaffBranchListResponse() {
        return mPrefs.getString(PREF_KEY_STAFF_BRANCH_LIST, null);
    }

    @Override
    public void setSalesManCustomerListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_SALESMAN_CUSTOMER_LIST, json).apply();
    }

    @Override
    public String getSalesManCustomerListResponse() {
        return mPrefs.getString(PREF_KEY_SALESMAN_CUSTOMER_LIST, null);
    }

    @Override
    public void setDataHeaderInventoryListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_DATA_HEADER_INVENTORY_LIST, json).apply();
    }

    @Override
    public String getDataHeaderInventoryListResponse() {
        return mPrefs.getString(PREF_KEY_DATA_HEADER_INVENTORY_LIST, null);
    }

    @Override
    public void setDataChildInventoryListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_DATA_CHILD_INVENTORY_LIST, json).apply();
    }

    @Override
    public String getDataChildInventoryListResponse() {
        return mPrefs.getString(PREF_KEY_DATA_CHILD_INVENTORY_LIST, null);
    }

    @Override
    public void setDataHeaderItemListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_DATA_HEADER_ITEM_LIST, json).apply();
    }

    @Override
    public String getDataHeaderItemListResponse() {
        return mPrefs.getString(PREF_KEY_DATA_HEADER_ITEM_LIST, null);
    }

    @Override
    public void setDataChildItemListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_DATA_CHILD_ITEM_LIST, json).apply();
    }

    @Override
    public String getDataChildItemListResponse() {
        return mPrefs.getString(PREF_KEY_DATA_CHILD_ITEM_LIST, null);
    }

    @Override
    public void setMyOrderResultListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_MY_ORDER_RESULT_LIST, json).apply();
    }

    @Override
    public String getMyOrderResultListListResponse() {
        return mPrefs.getString(PREF_KEY_MY_ORDER_RESULT_LIST, null);
    }

    @Override
    public void setOrderListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_GET_ORDER_RESULT_LIST, json).apply();
    }

    @Override
    public String getOrderListResponse() {
        return mPrefs.getString(PREF_KEY_GET_ORDER_RESULT_LIST, null);
    }

    @Override
    public String getExpenseTypeListResponse() {
        return mPrefs.getString(PREF_KEY_EXPENSE_TYPE_LIST, null);
    }

    @Override
    public void setExpenseTypeListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_EXPENSE_TYPE_LIST, json).apply();
    }

    @Override
    public void setExpenseResultListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_EXPENSE_RESULT_LIST, json).apply();
    }

    @Override
    public String getExpenseResultListResponse() {
        return mPrefs.getString(PREF_KEY_EXPENSE_RESULT_LIST, null);
    }

    @Override
    public String getExpenseDetailsResultListResponse() {
        return mPrefs.getString(PREF_KEY_EXPENSE_DETAILS_RESULT_LIST, null);
    }

    @Override
    public void setExpenseDetailsResultListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_EXPENSE_DETAILS_RESULT_LIST, json).apply();
    }

    @Override
    public String getExpensesTypeListResponse() {
        return mPrefs.getString(PREF_KEY_EXPENSES_TYPE_LIST, null);
    }

    @Override
    public void setExpensesTypeListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_EXPENSES_TYPE_LIST, json).apply();
    }

    @Override
    public String getPunchValue() {
        return mPrefs.getString(PREF_KEY_PUNCH_VALUE, null);
    }

    @Override
    public void setPunchValue(String punchValue) {
        mPrefs.edit().putString(PREF_KEY_PUNCH_VALUE, punchValue).apply();
    }

    @Override
    public void setCustomerDetailsListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_CUSTOMER_DETAILS_LIST, json).apply();
    }

    @Override
    public String getCustomerDetailsListResponse() {
        return mPrefs.getString(PREF_KEY_CUSTOMER_DETAILS_LIST, null);
    }

    @Override
    public void setCustomerId(Integer customerId) {
        mPrefs.edit().putInt(PREF_KEY_CUSTOMER_ID, customerId).apply();
    }

    @Override
    public Integer getCustomerId() {
        return mPrefs.getInt(PREF_KEY_CUSTOMER_ID, -1);
    }
}