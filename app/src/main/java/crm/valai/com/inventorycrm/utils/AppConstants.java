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

package crm.valai.com.inventorycrm.utils;

public final class AppConstants {
    // Splash Screen Timer Delay
    public static final int SPLASH_TIME_OUT = 3000;
    public static final int ACTIVITY_TIME_OUT = 1000;
    // Preference Name
    public static final String PREF_NAME = "SLS_Pref";

    // Menu Items Fragments Timer Delay
    public static final int DELAY_TIME_OUT = 250;
    // Request API RESPONSE CODE
    public static final int RESPONSE_CODE = 200;
    public static final int REQUEST_PERMISSION_CODE_STORAGE = 101;
    public static final int REQUEST_PERMISSION_CODE_READ_PHONE_STATE = 102;
    public static final int REQUEST_CAMERA = 102;
    public static final int REQUEST_CHOOSER = 1234;
    public static final int REQUEST_PERMISSION_CODE_CAMERA = 1023;
    public static final int REQUEST_DOC_FILE = 1222;
    public static final int REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS = 104;
    // Request API RESPONSE CODE
    public static final String API_RESPONSE_CODE_WITH_DATA = "-106";
    public static final String API_RESPONSE_CODE_PUNCH_IN_OUT = "0";
    public static final String API_RESPONSE_CODE_UPLOAD = "0000";
    public static final String TRUE = "True";

    public static final String TAG_DASHBOARD = "DashBoard";
    public static final String TAG_MY_CUSTOMERS = "My Customers";
    public static final String TAG_MY_ORDERS = "My Orders";
    public static final String TAG_MY_LEAVES = "My Leaves";
    public static final String TAG_MY_EXPENSES = "My Expenses";
    public static final String TAG_SALES_TRACKER = "My Sales";

    public static final String FROM_DATE_TAG = "fromDate";
    public static final String TO_DATE_TAG = "toDate";
    public static final String SLSS = "SLSS";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static final String GET_STAFF_ASSIGNED_BRANCH = "GET_STAFF_ASSIGNED_BRANCH";
    public static final String GET_ORDER_BY_DATE = "GET_ORDER_BY_DATE";
    public static final String GET_ORDER_DETAIL = "GET_ORDER_DETAIL";
    public static final String GET_INVENTORY_CLASSIFICATION = "GET_INVENTORY_CLASSIFICATION";
    public static final String GET_INVENTORY_CLASSIFICATION_CATEGORY = "GET_INVENTORY_CLASSIFICATION_CATEGORY";
    public static final String INSERT_ORDER = "INSERT_ORDER";
    public static final String INSERT_ORDER_TAX = "INSERT_ORDER_TAX";
    public static final String INSERT_ORDER_DETAIL = "INSERT_ORDER_DETAIL";
    public static final String INSERT_EXPENSE = "INSERT_EXPENSE";
    public static final String INSERT_EXPENSE_DETAIL = "INSERT_EXPENSE_DETAIL";
    public static final String GETLEAVEDRP = "GETLEAVEDRP";
    public static final String SAVELEAVE = "SAVELEAVE";
    public static final String GET_EXPENSETYPE = "GET_EXPENSETYPE";
    public static final String DELETE_EXPENSE_DETAIL = "DELETE_EXPENSE_DETAIL";
    public static final String GET_STAFF_EXPENSE = "GET_STAFF_EXPENSE";
    public static final String GET_STAFF_EXPENSE_DETAIL = "GET_STAFF_EXPENSE_DETAIL";
    public static final String GET_EXPENSE_STATUS = "GET_Expense_Status";
    public static final String SEND_FOR_APPROVAL = "SEND_FOR_APPROVAL";
    public static final String GETLEAVESANPENDIS = "GETLEAVESANPENDIS";
    public static final String INSERT_CUSTOMER = "INSERT_CUSTOMER";
    public static final String GET_CUSTOMER_TYPE = "GET_CUSTOMER_TYPE";
    public static final String GET_MODE_OF_PAYMENT = "GET_MODE_OF_PAYMENT";
    public static final String CREATE = "CREATE";
    public static final String DELETE = "DELETE";
    public static final String GET_PURPOSE_OF_VISIT = "GET_PURPOSE_OF_VISIT";
    public static final String GET_CUSTOMER_DOCUMENT_PATH = "GET_CUSTOMER_DOCUMENT_PATH";

    public static final int punchInType = 1;
    public static final int punchOutType = 2;

    public static final int taxIdGST = 1;
    public static final int taxIdSGST = 2;

    public static final int uomMt = 1;
    public static final int uomPcs = 2;

    // Network Request API Root URL
    public static final String ROOT = "http://slswebapi.evalai.com/api/";

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}