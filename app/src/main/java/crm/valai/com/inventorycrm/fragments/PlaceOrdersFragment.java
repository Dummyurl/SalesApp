package crm.valai.com.inventorycrm.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
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
import crm.valai.com.inventorycrm.activities.MyOrderAddSelectedItemActivity;
import crm.valai.com.inventorycrm.adapters.MyOrderListAdapter;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.GetInventoryClassificationPOJO;
import crm.valai.com.inventorycrm.modals.GetSalesmanCustomerPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_INVENTORY_CLASSIFICATION;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_INVENTORY_CLASSIFICATION_CATEGORY;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

public class PlaceOrdersFragment extends BaseFragment {
    public static final String TAG = PlaceOrdersFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar_selected_customers)
    TextView toolbar_selected_customers;

    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;

    @BindView(R.id.expandCategoriesList)
    ExpandableListView expandCategoriesList;

    @BindView(R.id.tvNoResult)
    TextView tvNoResult;

    private List<LogInPOJO.Datum> logInResList;
    private List<GetSalesmanCustomerPOJO.Datum> listGetSalesManCustomer;
    private List<GetInventoryClassificationPOJO.Datum> listDataHeaderInventory;
    private HashMap<String, List<GetInventoryClassificationPOJO.Datum>> listDataChildInventory;
    private int pos = 0;
    private Integer customerId = 0;
    private AppPreferencesHelper appPreferencesHelper;
    private FragmentListner fragmentListner;

    public PlaceOrdersFragment() {
        // Required empty public constructor
    }

    public static PlaceOrdersFragment newInstance(String param1, String param2) {
        PlaceOrdersFragment fragment = new PlaceOrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_myorders, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logInResList = new ArrayList<>();
        logInResList = fragmentListner.getSignInResultList();
        appPreferencesHelper = fragmentListner.getAppPreferenceHelper();

        if (logInResList != null && logInResList.size() > 0) {
            listDataHeaderInventory = new ArrayList<>();
            listDataChildInventory = new HashMap<>();

            if (!isNetworkConnected()) {
                listDataHeaderInventory = getDataHeaderInventoryList();
                listDataChildInventory = getDataChildInventoryList();
                if (listDataHeaderInventory != null && listDataHeaderInventory.size() > 0 &&
                        listDataChildInventory != null && listDataChildInventory.size() > 0) {
                    setExpandableListAdapter();
                } else {
                    setExpandableListAdapter();
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                showLoading();
                getInventoryCategory(0, GET_INVENTORY_CLASSIFICATION);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fragmentListner = (FragmentListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MyInterface ");
        }
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_myorders);
//        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);
//
//        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
//        toolbar_selected_customers.setText(appPreferencesHelper.getBranch());
//        toolbar_title.setText(getString(R.string.nav_my_orders));
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<LogInPOJO.Datum>>() {
//        }.getType();
//        logInResList = new ArrayList<>();
//        logInResList = gson.fromJson(appPreferencesHelper.getLogInResponse(), type);
//        listGetSalesManCustomer = new ArrayList<>();
//
//        listDataHeaderInventory = new ArrayList<>();
//        listDataChildInventory = new HashMap<>();
//
//        if (!isNetworkConnected()) {
//            listGetSalesManCustomer = getSalesManCustomerList();
//            if (listGetSalesManCustomer != null && listGetSalesManCustomer.size() > 0) {
//                setAutoCompleteAdapter();
//            } else {
//                setAutoCompleteAdapter();
//                showMessage(getString(R.string.internet_not_available));
//            }
//        } else {
//            getSalesManCustomer();
//        }
//    }

    private void setAutoCompleteAdapter() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listGetSalesManCustomer.size(); i++) {
            list.add(listGetSalesManCustomer.get(i).getCustomerName());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, list);
        autoCompleteTextView.setThreshold(1);//start List viewing from first character
        autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                for (int i = 0; i < listGetSalesManCustomer.size(); i++) {
                    if (listGetSalesManCustomer.get(i).getCustomerName().equals(arg0.getItemAtPosition(position))) {
                        customerId = listGetSalesManCustomer.get(i).getCustomerId();
                    }
                }
                hideKeyboard();

//                listDataHeaderInventory = new ArrayList<>();
//                listDataChildInventory = new HashMap<>();
//
//                if (!isNetworkConnected()) {
//                    listDataHeaderInventory = getDataHeaderInventoryList();
//                    listDataChildInventory = getDataChildInventoryList();
//                    if (listDataHeaderInventory != null && listDataHeaderInventory.size() > 0 &&
//                            listDataChildInventory != null && listDataChildInventory.size() > 0) {
//                        setExpandableListAdapter();
//                    } else {
//                        setExpandableListAdapter();
//                        showMessage(getString(R.string.internet_not_available));
//                    }
//                } else {
//                    showLoading();
//                    getInventoryCategory(0, GET_INVENTORY_CLASSIFICATION);
//                }
            }
        });
    }

    private void setExpandableListAdapter() {
        if (listDataHeaderInventory.size() > 0 && listDataChildInventory.size() > 0) {
            tvNoResult.setVisibility(View.GONE);
            expandCategoriesList.setVisibility(View.VISIBLE);
            final MyOrderListAdapter listAdapter = new MyOrderListAdapter(getContext(), listDataHeaderInventory, listDataChildInventory);
            expandCategoriesList.setAdapter(listAdapter);
            expandCategoriesList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    //if (!autoCompleteTextView.getText().toString().equals("")) {
                    final String subCategory = (String) listAdapter.getChild(groupPosition, childPosition);
                    final String mainCategory = (String) listAdapter.getGroup(groupPosition);
                    final Integer parentId = (Integer) listAdapter.getChildParentId(groupPosition, childPosition);
                    final Integer inventoryClassificationId = (Integer) listAdapter.getChildItemId(groupPosition, childPosition);
                    hideKeyboard();
                    Intent intent = new Intent(getContext(), MyOrderAddSelectedItemActivity.class);
                    intent.putExtra("BRANCH_NAME", appPreferencesHelper.getBranch());
                    intent.putExtra("CUSTOMER_NAME", autoCompleteTextView.getText().toString());
                    intent.putExtra("CUSTOMER_ID", customerId);
                    intent.putExtra("SELECTEDMAINCATEGORY", mainCategory);
                    intent.putExtra("SELECTEDSUBCATEGORY", subCategory);
                    intent.putExtra("PARENT_ID", parentId);
                    intent.putExtra("I_CLASSIFICATION_ID", inventoryClassificationId);
                    startActivityForResult(intent, 100);
//                    } else {
//                        showMessage(getString(R.string.select_customer_text));
//                    }

                    return true;
                }
            });
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
            expandCategoriesList.setVisibility(View.GONE);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            finish();//finishing activity
//        }
//    }

    private void getSalesManCustomer() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getSalesManCustomerJson(logInResList.get(0).getCompId(), logInResList.get(0).getStaffId(),
                logInResList.get(0).getLoginId(), logInResList.get(0).getToken());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetSalesmanCustomerPOJO> call = restClientAPI.getSalesmanCustomer(body);
        call.enqueue(new retrofit2.Callback<GetSalesmanCustomerPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetSalesmanCustomerPOJO> call, @NonNull Response<GetSalesmanCustomerPOJO> response) {
                GetSalesmanCustomerPOJO getSalesmanCustomerPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getSalesmanCustomerPOJO != null) {
                        if (getSalesmanCustomerPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getSalesmanCustomerPOJO.getResponseStatus().equals(TRUE)) {
                                listGetSalesManCustomer.addAll(getSalesmanCustomerPOJO.getData());
                                setSalesManCustomerList(listGetSalesManCustomer);
                                setAutoCompleteAdapter();
                            } else {
                                showMessage(getSalesmanCustomerPOJO.getResponseMessage());
                            }
                        } else {
                            showMessage(getSalesmanCustomerPOJO.getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetSalesmanCustomerPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void getInventoryCategory(final Integer parentId, String mode) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getInventoryCategoryJson(logInResList.get(0).getCompId(), 0, logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), 1, parentId, mode);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetInventoryClassificationPOJO> call = restClientAPI.getInventoryClassification(body);
        call.enqueue(new retrofit2.Callback<GetInventoryClassificationPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetInventoryClassificationPOJO> call, @NonNull Response<GetInventoryClassificationPOJO> response) {
                GetInventoryClassificationPOJO getInventoryClassificationPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getInventoryClassificationPOJO != null) {
                        if (getInventoryClassificationPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getInventoryClassificationPOJO.getResponseStatus().equals(TRUE)) {
                                if (parentId == 0) {
                                    listDataHeaderInventory.addAll(getInventoryClassificationPOJO.getData());
                                    if (listDataHeaderInventory != null && listDataHeaderInventory.size() > 0) {
                                        getInventoryCategory(listDataHeaderInventory.get(pos).getId(), GET_INVENTORY_CLASSIFICATION_CATEGORY);
                                    }
                                } else {
                                    List<GetInventoryClassificationPOJO.Datum> list = new ArrayList<>(getInventoryClassificationPOJO.getData());
                                    listDataChildInventory.put(listDataHeaderInventory.get(pos).getName(), list);

                                    if (pos == listDataHeaderInventory.size() - 1) {
                                        setDataHeaderInventoryList(listDataHeaderInventory);
                                        setDataChildInventoryList(listDataChildInventory);
                                        pos = 0;
                                        hideLoading();
                                        setExpandableListAdapter();
                                    } else {
                                        pos = pos + 1;
                                        getInventoryCategory(listDataHeaderInventory.get(pos).getId(), GET_INVENTORY_CLASSIFICATION_CATEGORY);
                                    }
                                }

                            } else {
                                hideLoading();
                                setExpandableListAdapter();
                                showMessage(getInventoryClassificationPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            setExpandableListAdapter();
                            showMessage(getInventoryClassificationPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        setExpandableListAdapter();
                    }

                } else {
                    hideLoading();
                    setExpandableListAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetInventoryClassificationPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                setExpandableListAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setSalesManCustomerList(List<GetSalesmanCustomerPOJO.Datum> listGetSalesManCustomer) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetSalesmanCustomerPOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(listGetSalesManCustomer, type);
        this.appPreferencesHelper.setSalesManCustomerListResponse(json);
    }

    private List<GetSalesmanCustomerPOJO.Datum> getSalesManCustomerList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetSalesmanCustomerPOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getSalesManCustomerListResponse(), type);
    }

    private void setDataHeaderInventoryList(List<GetInventoryClassificationPOJO.Datum> listDataHeaderInventory) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetInventoryClassificationPOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(listDataHeaderInventory, type);
        this.appPreferencesHelper.setDataHeaderInventoryListResponse(json);
    }

    private List<GetInventoryClassificationPOJO.Datum> getDataHeaderInventoryList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetInventoryClassificationPOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getDataHeaderInventoryListResponse(), type);
    }

    private void setDataChildInventoryList(HashMap<String, List<GetInventoryClassificationPOJO.Datum>> listDataChildInventory) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<GetInventoryClassificationPOJO.Datum>>>() {
        }.getType();
        String json = gson.toJson(listDataChildInventory, type);
        this.appPreferencesHelper.setDataChildInventoryListResponse(json);
    }

    private HashMap<String, List<GetInventoryClassificationPOJO.Datum>> getDataChildInventoryList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<GetInventoryClassificationPOJO.Datum>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getDataChildInventoryListResponse(), type);
    }

//    @OnClick(R.id.btnBack)
//    public void onBackClick() {
//        hideKeyboard();
//        finish();
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        hideKeyboard();
//        finish();
//    }
}