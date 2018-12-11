package crm.valai.com.inventorycrm.interfaces;


import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.List;

import crm.valai.com.inventorycrm.modals.GetCustomerDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetExpenseDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetExpensePOJO;
import crm.valai.com.inventorycrm.modals.GetExpenseTypePOJO;
import crm.valai.com.inventorycrm.modals.GetInventoryClassificationPOJO;
import crm.valai.com.inventorycrm.modals.GetOrdersPOJO;
import crm.valai.com.inventorycrm.modals.GetSalesmanCustomerPOJO;
import crm.valai.com.inventorycrm.modals.GetStaffBranchPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;


public interface FragmentListner {
    void setOnLoadHome(int navIndex, String tag);

    void setTopCustomerName(String customerName);

    String getTopCustomerName();

    AppPreferencesHelper getAppPreferenceHelper();

    void onFragmentDetach(String tag);

    void onFragmentAttach(Fragment fragment, String tag);

    void setDrawerEnabled(boolean enabled);

    List<LogInPOJO.Datum> getSignInResultList();

    void setStaffBranchList(List<GetStaffBranchPOJO.Datum> staffBranchList);

    List<GetStaffBranchPOJO.Datum> getStaffBranchList();

    void setSalesManCustomerList(List<GetSalesmanCustomerPOJO.Datum> listGetSalesManCustomer);

    List<GetSalesmanCustomerPOJO.Datum> getSalesManCustomerList();

    void setDataHeaderInventoryList(List<GetInventoryClassificationPOJO.Datum> listDataHeaderInventory);

    List<GetInventoryClassificationPOJO.Datum> getDataHeaderInventoryList();

    void setDataChildInventoryList(HashMap<String, List<GetInventoryClassificationPOJO.Datum>> listDataChildInventory);

    HashMap<String, List<GetInventoryClassificationPOJO.Datum>> getDataChildInventoryList();

    void setGetOrderList(HashMap<String, List<GetOrdersPOJO.Datum>> listGetOrder);

    HashMap<String, List<GetOrdersPOJO.Datum>> getOrderList();

    List<GetExpenseTypePOJO.Datum> getExpenseTypeList();

    void setExpenseTypeList(List<GetExpenseTypePOJO.Datum> listDataChildInventory);

    void setExpenseResultList(HashMap<Integer, List<GetExpensePOJO.Datum>> expenseResultList);

    HashMap<Integer, List<GetExpensePOJO.Datum>> getExpenseResultList();

    HashMap<Integer, List<GetExpenseDetailsPOJO.Datum>> getExpenseDetailsResultList();

    void setExpenseDetailsResultList(HashMap<Integer, List<GetExpenseDetailsPOJO.Datum>> hashExpenseDetails);

    void setCustomerDetailsList(HashMap<Integer, List<GetCustomerDetailsPOJO.Datum>> listCustomerDetails);

    HashMap<Integer, List<GetCustomerDetailsPOJO.Datum>> getCustomerDetailsList();
}