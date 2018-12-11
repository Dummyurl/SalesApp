package crm.valai.com.inventorycrm.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.fragments.DashBoardFragment;
import crm.valai.com.inventorycrm.fragments.MyCustomersFragment;
import crm.valai.com.inventorycrm.fragments.MyExpensesFragment;
import crm.valai.com.inventorycrm.fragments.MyLeavesFragment;
import crm.valai.com.inventorycrm.fragments.MySalesFragment;
import crm.valai.com.inventorycrm.fragments.PlaceOrdersFragment;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.GetCustomerDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetExpenseDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetExpensePOJO;
import crm.valai.com.inventorycrm.modals.GetExpenseTypePOJO;
import crm.valai.com.inventorycrm.modals.GetInventoryClassificationPOJO;
import crm.valai.com.inventorycrm.modals.GetOrdersPOJO;
import crm.valai.com.inventorycrm.modals.GetSalesmanCustomerPOJO;
import crm.valai.com.inventorycrm.modals.GetStaffBranchPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.modals.PunchInOutPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;
import crm.valai.com.inventorycrm.utils.AppConstants;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.DELAY_TIME_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_STAFF_ASSIGNED_BRANCH;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TAG_DASHBOARD;
import static crm.valai.com.inventorycrm.utils.AppConstants.TAG_MY_CUSTOMERS;
import static crm.valai.com.inventorycrm.utils.AppConstants.TAG_MY_EXPENSES;
import static crm.valai.com.inventorycrm.utils.AppConstants.TAG_MY_LEAVES;
import static crm.valai.com.inventorycrm.utils.AppConstants.TAG_MY_ORDERS;
import static crm.valai.com.inventorycrm.utils.AppConstants.TAG_SALES_TRACKER;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;
import static crm.valai.com.inventorycrm.utils.AppConstants.punchInType;
import static crm.valai.com.inventorycrm.utils.AppConstants.punchOutType;

/**
 * @author by Mohit Arora on 26/3/18.
 */
public class DashBoardActivity extends BaseActivity implements FragmentListner {
    public static final String TAG = DashBoardActivity.class.getSimpleName();
    private AppPreferencesHelper appPreferencesHelper;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar_selected_customers)
    TextView toolbar_selected_customers;

    private Spinner toolbar_spinners_customers;
    private int navItemIndex = 0;
    private String CURRENT_TAG = TAG_DASHBOARD;
    private String[] activityTitles;
    private Handler mHandler;
    private List<LogInPOJO.Datum> logInResList;
    private List<GetStaffBranchPOJO.Datum> listStaffBranch;
    private int isPrimarySelected = 0;

    @NonNull
    public static Intent getStartIntent(Context context) {
        return new Intent(context, DashBoardActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        mHandler = new Handler();
        View navHeader = nav_view.getHeaderView(0);
        TextView txtName = navHeader.findViewById(R.id.name);
        final ImageView schoolLogo = navHeader.findViewById(R.id.img_logo);
        toolbar_spinners_customers = navHeader.findViewById(R.id.toolbar_spinners_customers);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);

        Gson gson = new Gson();
        Type type = new TypeToken<List<LogInPOJO.Datum>>() {
        }.getType();
        logInResList = new ArrayList<>();
        logInResList = gson.fromJson(appPreferencesHelper.getLogInResponse(), type);
        if (logInResList != null && logInResList.size() > 0) {
            if (logInResList.get(0).getStaffName() != null && !logInResList.get(0).getStaffName().equals("")) {
                txtName.setText(logInResList.get(0).getStaffName());
            } else {
                txtName.setText(getString(R.string.app_name));
            }
        }

        // Initializing Navigation Menu
        setUpNavigationView();

        if (appPreferencesHelper.getPunchValue() == null) {
            appPreferencesHelper.setPunchValue(getString(R.string.nav_punch_in));
        }

        if (appPreferencesHelper.getPunchValue().equals(getString(R.string.nav_punch_in))) {
            nav_view.getMenu().findItem(R.id.nav_punchInOut).setTitle(getString(R.string.nav_punch_out)).setChecked(false);
        } else {
            nav_view.getMenu().findItem(R.id.nav_punchInOut).setTitle(getString(R.string.nav_punch_in)).setChecked(false);
        }

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_DASHBOARD;
            toolbar_selected_customers.setVisibility(View.GONE);
            loadHomeFragment();
        }

        listStaffBranch = new ArrayList<>();
        if (!isNetworkConnected()) {
            listStaffBranch = getStaffBranchList();
            if (listStaffBranch != null && listStaffBranch.size() > 0) {
                setStaffBranchSpinner();
            } else {
                showMessage(getString(R.string.internet_not_available));
            }
        } else {
            getStaffBranch();
        }
    }

    /*
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer_layout.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        mHandler.postDelayed(mPendingRunnable, DELAY_TIME_OUT);
        drawer_layout.closeDrawers();
        invalidateOptionsMenu();
    }

    @NonNull
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return new DashBoardFragment();
            case 1:
                return new MyCustomersFragment();
            case 2:
                return new PlaceOrdersFragment();
            //return new GetOrdersFragment();
            case 3:
                return new MySalesFragment();
            case 4:
                return new MyLeavesFragment();
            case 5:
                return new MyExpensesFragment();
            default:
                return new DashBoardFragment();
        }
    }

    private void setToolbarTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar_title.setText(activityTitles[navItemIndex]);
        }
    }

    private void selectNavMenu() {
        nav_view.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_dashboard:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_DASHBOARD;
                        toolbar_selected_customers.setVisibility(View.GONE);
                        break;
                    case R.id.nav_my_customers:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_MY_CUSTOMERS;
                        toolbar_selected_customers.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nav_my_orders:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_MY_ORDERS;
                        toolbar_selected_customers.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nav_sales_tracker:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_SALES_TRACKER;
                        toolbar_selected_customers.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nav_my_leaves:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_MY_LEAVES;
                        toolbar_selected_customers.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nav_my_expenses:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_MY_EXPENSES;
                        toolbar_selected_customers.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nav_punchInOut:
                        showPunchInOutDialog(DashBoardActivity.this, menuItem.getTitle().toString(), menuItem);
                        break;
                    case R.id.nav_logout:
                        if (logInResList != null) {
                            appPreferencesHelper.setPunchValue(getString(R.string.nav_punch_in));
                            punchInOutRequestCall(logInResList.get(0), punchOutType, true);
                        }
                        break;
                    default:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_DASHBOARD;
                        toolbar_selected_customers.setVisibility(View.GONE);
                }

//                if (menuItem.isChecked()) {
//                    menuItem.setChecked(false);
//                } else {
//                    menuItem.setChecked(true);
//                }
                //menuItem.setChecked(true);
                loadHomeFragment();
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer_layout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer_layout.setDrawerLockMode(lockMode);
    }

    @Override
    public List<LogInPOJO.Datum> getSignInResultList() {
        return logInResList;
    }

    @Override
    public void setOnLoadHome(int itemIndex, String tag) {
        if (itemIndex != 0) {
            toolbar_selected_customers.setVisibility(View.VISIBLE);
        } else {
            toolbar_selected_customers.setVisibility(View.GONE);
        }
        navItemIndex = itemIndex;
        CURRENT_TAG = tag;
        loadHomeFragment();
    }

    @Override
    public void setTopCustomerName(String details) {
        toolbar_selected_customers.setText(details);
    }

    @Override
    public String getTopCustomerName() {
        return toolbar_selected_customers.getText().toString();
    }

    @Override
    public AppPreferencesHelper getAppPreferenceHelper() {
        return appPreferencesHelper;
    }

    @Override
    public void onFragmentAttach(final Fragment fragment, final String tag) {
        //this.tag = tag;
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, tag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        mHandler.postDelayed(mPendingRunnable, DELAY_TIME_OUT);
    }

    @Override
    public void onFragmentDetach(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager.beginTransaction().disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right).remove(fragment)
                    .commitAllowingStateLoss();
        }
    }

    void setStaffBranchSpinner() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listStaffBranch.size(); i++) {
            if (listStaffBranch.get(i).getIsPrimary() == 1) {
                isPrimarySelected = i;
            }
            list.add(listStaffBranch.get(i).getBranchName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toolbar_spinners_customers.setAdapter(dataAdapter);
        toolbar_spinners_customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toolbar_selected_customers.setText(parent.getItemAtPosition(position).toString());
                getAppPreferenceHelper().setBranch(parent.getItemAtPosition(position).toString());
                getAppPreferenceHelper().setBranchId(listStaffBranch.get(position).getBranchId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        toolbar_spinners_customers.setSelection(isPrimarySelected);
    }

    private void getStaffBranch() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getStaffBranchJson(logInResList.get(0).getCompId(), logInResList.get(0).getStaffId(),
                logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), GET_STAFF_ASSIGNED_BRANCH);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetStaffBranchPOJO> call = restClientAPI.getStaffBranch(body);
        call.enqueue(new Callback<GetStaffBranchPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetStaffBranchPOJO> call, @NonNull Response<GetStaffBranchPOJO> response) {
                GetStaffBranchPOJO getStaffBranchPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getStaffBranchPOJO != null) {
                        if (getStaffBranchPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getStaffBranchPOJO.getResponseStatus().equals(TRUE)) {
                                listStaffBranch.addAll(getStaffBranchPOJO.getData());
                                setStaffBranchList(listStaffBranch);
                                setStaffBranchSpinner();
                            } else {
                                hideLoading();
                                showMessage(getStaffBranchPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            if (getStaffBranchPOJO.getResponseMessage().equals(getString(R.string.token_varification_failed))) {
                                logOutIntent();
                            } else {
                                showMessage(getStaffBranchPOJO.getResponseMessage());
                            }
                        }
                    } else {
                        hideLoading();
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetStaffBranchPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    // Request Rest Client API Call For Punch Out
    public void punchInOutRequestCall(final LogInPOJO.Datum logInList, Integer punchInOutType, final Boolean isLogout) {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getPunchInOutJson(logInList.getCompId(), logInList.getStaffId(), punchInOutType,
                appPreferencesHelper.getUserName(), appPreferencesHelper.getIpAddress(), appPreferencesHelper.getDeviceId(),
                logInList.getLoginId(), logInList.getToken());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<PunchInOutPOJO> call = restClientAPI.punchInOut(body);
        call.enqueue(new Callback<PunchInOutPOJO>() {
            @Override
            public void onResponse(@NonNull Call<PunchInOutPOJO> call, @NonNull Response<PunchInOutPOJO> response) {
                PunchInOutPOJO punchInOutPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (punchInOutPOJO != null) {
                        if (punchInOutPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (punchInOutPOJO.getResponseStatus().equals(TRUE)) {
                                if (isLogout) {
                                    logOutIntent();
                                    return;
                                }
                                showMessage(punchInOutPOJO.getResponseMessage());
                            } else {
                                hideLoading();
                                showMessage(punchInOutPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            if (punchInOutPOJO.getResponseMessage().equals(getString(R.string.token_varification_failed))) {
                                logOutIntent();
                            } else {
                                showMessage(punchInOutPOJO.getResponseMessage());
                            }
                        }
                    } else {
                        hideLoading();
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PunchInOutPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void logOutIntent() {
        appPreferencesHelper.logOutFromPref();
        Intent intent = LogInActivity.getStartIntent(DashBoardActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void setStaffBranchList(List<GetStaffBranchPOJO.Datum> staffBranchList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetStaffBranchPOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(staffBranchList, type);
        this.appPreferencesHelper.setStaffBranchListResponse(json);
    }

    @Override
    public List<GetStaffBranchPOJO.Datum> getStaffBranchList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetStaffBranchPOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getStaffBranchListResponse(), type);
    }

    @Override
    public void setSalesManCustomerList(List<GetSalesmanCustomerPOJO.Datum> listGetSalesManCustomer) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetSalesmanCustomerPOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(listGetSalesManCustomer, type);
        this.appPreferencesHelper.setSalesManCustomerListResponse(json);
    }

    @Override
    public List<GetSalesmanCustomerPOJO.Datum> getSalesManCustomerList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetSalesmanCustomerPOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getSalesManCustomerListResponse(), type);
    }

    @Override
    public void setDataHeaderInventoryList(List<GetInventoryClassificationPOJO.Datum> listDataHeaderInventory) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetInventoryClassificationPOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(listDataHeaderInventory, type);
        this.appPreferencesHelper.setDataHeaderInventoryListResponse(json);
    }

    @Override
    public List<GetInventoryClassificationPOJO.Datum> getDataHeaderInventoryList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetInventoryClassificationPOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getDataHeaderInventoryListResponse(), type);
    }

    @Override
    public void setDataChildInventoryList(HashMap<String, List<GetInventoryClassificationPOJO.Datum>> listDataChildInventory) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<GetInventoryClassificationPOJO.Datum>>>() {
        }.getType();
        String json = gson.toJson(listDataChildInventory, type);
        this.appPreferencesHelper.setDataChildInventoryListResponse(json);
    }

    @Override
    public HashMap<String, List<GetInventoryClassificationPOJO.Datum>> getDataChildInventoryList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<GetInventoryClassificationPOJO.Datum>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getDataChildInventoryListResponse(), type);
    }

    @Override
    public void setGetOrderList(HashMap<String, List<GetOrdersPOJO.Datum>> listGetOrder) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<GetOrdersPOJO.Datum>>>() {
        }.getType();
        String json = gson.toJson(listGetOrder, type);
        this.appPreferencesHelper.setOrderListResponse(json);
    }

    @Override
    public HashMap<String, List<GetOrdersPOJO.Datum>> getOrderList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<GetOrdersPOJO.Datum>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getOrderListResponse(), type);
    }

    @Override
    public List<GetExpenseTypePOJO.Datum> getExpenseTypeList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetExpenseTypePOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getExpenseTypeListResponse(), type);
    }

    @Override
    public void setExpenseTypeList(List<GetExpenseTypePOJO.Datum> expenseTypeList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetExpenseTypePOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(expenseTypeList, type);
        this.appPreferencesHelper.setExpenseTypeListResponse(json);
    }

    @Override
    public void setExpenseResultList(HashMap<Integer, List<GetExpensePOJO.Datum>> expenseResultList) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<GetExpensePOJO.Datum>>>() {
        }.getType();
        String json = gson.toJson(expenseResultList, type);
        this.appPreferencesHelper.setExpenseResultListResponse(json);
    }

    @Override
    public HashMap<Integer, List<GetExpensePOJO.Datum>> getExpenseResultList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<GetExpensePOJO.Datum>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getExpenseResultListResponse(), type);
    }

    @Override
    public HashMap<Integer, List<GetExpenseDetailsPOJO.Datum>> getExpenseDetailsResultList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<GetExpenseDetailsPOJO.Datum>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getExpenseDetailsResultListResponse(), type);
    }

    @Override
    public void setExpenseDetailsResultList(HashMap<Integer, List<GetExpenseDetailsPOJO.Datum>> hashExpenseDetails) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<GetExpenseDetailsPOJO.Datum>>>() {
        }.getType();
        String json = gson.toJson(hashExpenseDetails, type);
        this.appPreferencesHelper.setExpenseDetailsResultListResponse(json);
    }

    @Override
    public void setCustomerDetailsList(HashMap<Integer, List<GetCustomerDetailsPOJO.Datum>> listCustomerDetails) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<GetCustomerDetailsPOJO.Datum>>>() {
        }.getType();
        String json = gson.toJson(listCustomerDetails, type);
        this.appPreferencesHelper.setCustomerDetailsListResponse(json);
    }

    @Override
    public HashMap<Integer, List<GetCustomerDetailsPOJO.Datum>> getCustomerDetailsList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<GetCustomerDetailsPOJO.Datum>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getCustomerDetailsListResponse(), type);
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    private void showPunchInOutDialog(final Context mContext, final String msg, final MenuItem menuItem) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View dialogView = null;
        if (inflater != null) {
            dialogView = inflater.inflate(R.layout.alert, null);
            TextView TvAlert = dialogView.findViewById(R.id.tvAlert);
            TvAlert.setText(getString(R.string.do_you_want) + " " + msg + "?");

            Button btnYes = dialogView.findViewById(R.id.btnYes);
            Button btnNo = dialogView.findViewById(R.id.btnNo);
            dialogBuilder.setView(dialogView);

            final AlertDialog alertDialog = dialogBuilder.create();

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    if (msg.equals(getString(R.string.nav_punch_out))) {
                        menuItem.setTitle(getString(R.string.nav_punch_in)).setChecked(false);
                        if (logInResList != null) {
                            appPreferencesHelper.setPunchValue(getString(R.string.nav_punch_out));
                            punchInOutRequestCall(logInResList.get(0), punchOutType, false);
                        }
                    } else {
                        menuItem.setTitle(getString(R.string.nav_punch_out)).setChecked(false);
                        if (logInResList != null) {
                            appPreferencesHelper.setPunchValue(getString(R.string.nav_punch_in));
                            punchInOutRequestCall(logInResList.get(0), punchInType, false);
                        }
                    }
                }
            });

            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        // checking if user is on other navigation menu
        // rather than home
        if (navItemIndex != 0) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_DASHBOARD;
            toolbar_selected_customers.setVisibility(View.GONE);
            loadHomeFragment();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}