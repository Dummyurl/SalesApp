package crm.valai.com.inventorycrm.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
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
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.adapters.MyOrderAddItemAdapter;
import crm.valai.com.inventorycrm.interfaces.MyOrderSubList;
import crm.valai.com.inventorycrm.modals.GetItemPOJO;
import crm.valai.com.inventorycrm.modals.GetQuantityAndRatePOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.modals.MyOrderResultPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;
import crm.valai.com.inventorycrm.utils.AppConstants;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

public class MyOrderAddSelectedItemActivity extends BaseActivity implements MyOrderSubList {
    public static final String TAG = MyOrderAddSelectedItemActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.lvorderitem)
    ExpandableListView expandList;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar_selected_customers)
    TextView toolbar_selected_customers;

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.tvNoResult)
    TextView tvNoResult;

    private String branchName;
    private String customerName;
    private Integer customerId;
    private Intent intentUp;
    private MyOrderSubList myOrderSubList;
    private Integer parentId;
    private Integer inventoryClassificationId;
    private List<LogInPOJO.Datum> logInResList;
    private List<GetItemPOJO.Datum> listDataHeaderItem;
    private HashMap<String, List<GetQuantityAndRatePOJO.Datum>> listDataChildItem;
    private HashMap<Integer, List<GetItemPOJO.Datum>> hashListDataHeaderItem;
    private HashMap<Integer, HashMap<String, List<GetQuantityAndRatePOJO.Datum>>> hashListDataChildItem;
    private AppPreferencesHelper appPreferencesHelper;
    private HashMap<Integer, List<MyOrderResultPOJO>> myOrderResultHashMap;
    private List<MyOrderResultPOJO> myOrderResultList;
    private int pos = 0;

    @SuppressLint({"SetTextI18n", "UseSparseArrays"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_selecteditem_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        myOrderSubList = this;
        intentUp = getIntent();
        branchName = intentUp.getStringExtra("BRANCH_NAME");
        customerName = intentUp.getStringExtra("CUSTOMER_NAME");
        customerId = intentUp.getIntExtra("CUSTOMER_ID", 0);
        parentId = intentUp.getIntExtra("PARENT_ID", 0);
        inventoryClassificationId = intentUp.getIntExtra("I_CLASSIFICATION_ID", 0);
        toolbar_selected_customers.setText(branchName);
        toolbar_title.setText(intentUp.getStringExtra("SELECTEDMAINCATEGORY") + "-" + intentUp.getStringExtra("SELECTEDSUBCATEGORY"));

        expandList.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);

        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        Gson gson = new Gson();
        Type type = new TypeToken<List<LogInPOJO.Datum>>() {
        }.getType();
        logInResList = new ArrayList<>();
        logInResList = gson.fromJson(appPreferencesHelper.getLogInResponse(), type);

        listDataHeaderItem = new ArrayList<>();
        listDataChildItem = new HashMap<>();

        hashListDataHeaderItem = getDataHeaderItemList();
        if (hashListDataHeaderItem == null) {
            hashListDataHeaderItem = new HashMap<>();
        }

        hashListDataChildItem = getDataChildItemList();
        if (hashListDataChildItem == null) {
            hashListDataChildItem = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (hashListDataHeaderItem.size() > 0 && hashListDataHeaderItem.containsKey(inventoryClassificationId)) {
                listDataHeaderItem = hashListDataHeaderItem.get(inventoryClassificationId);
                for (int i = 0; i < listDataHeaderItem.size(); i++) {
                    if (hashListDataChildItem.size() > 0 && hashListDataChildItem.containsKey(listDataHeaderItem.get(i).getItemId())) {
                        listDataChildItem = hashListDataChildItem.get(listDataHeaderItem.get(i).getItemId());
                    }
                }

                if (listDataHeaderItem != null && listDataHeaderItem.size() > 0
                        && listDataChildItem != null && listDataChildItem.size() > 0) {
                    setAdapter();
                } else {
                    setAdapter();
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }

        } else {
            setAdapter();
            getItem();
        }
    }

    private void setListScrollListener() {
        expandList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && firstVisibleItem > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });
    }

    private void setAdapter() {
        if (listDataHeaderItem != null && listDataHeaderItem.size() > 0 && listDataChildItem != null && listDataChildItem.size() > 0) {
            expandList.setVisibility(View.VISIBLE);
            tvNoResult.setVisibility(View.GONE);
            MyOrderAddItemAdapter expAdapter = new MyOrderAddItemAdapter(this, listDataHeaderItem, listDataChildItem, myOrderSubList, branchName, customerName,
                    intentUp.getStringExtra("SELECTEDMAINCATEGORY"), intentUp.getStringExtra("SELECTEDSUBCATEGORY"));
            expandList.setAdapter(expAdapter);
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
            expandList.setVisibility(View.GONE);
        }

        setListScrollListener();
    }

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onResume() {
        super.onResume();
        myOrderResultList = new ArrayList<>();
//        myOrderResultHashMap = getMyOrderResultList();
//        if (myOrderResultHashMap == null) {
//            myOrderResultHashMap = new HashMap<>();
//            myOrderResultList = new ArrayList<>();
//        } else {
//            if (myOrderResultHashMap.size() > 0 && myOrderResultHashMap.containsKey(inventoryClassificationId)) {
//                myOrderResultList = myOrderResultHashMap.get(inventoryClassificationId);
//            }
//        }

        myOrderResultList = getMyOrderResultListNew();
        if (myOrderResultList == null) {
            myOrderResultList = new ArrayList<>();
        }
    }

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        hideKeyboard();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard();
        finish();
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        hideKeyboard();
        if (listDataChildItem != null && listDataChildItem.size() > 0) {
            Intent intent = new Intent(this, MyOrderShowBillActivity.class);
            intent.putExtra("BRANCH_NAME", branchName);
            intent.putExtra("CUSTOMER_NAME", customerName);
            intent.putExtra("CUSTOMER_ID", customerId);
            intent.putExtra("SELECTEDMAINCATEGORY", intentUp.getStringExtra("SELECTEDMAINCATEGORY"));
            intent.putExtra("SELECTEDSUBCATEGORY", intentUp.getStringExtra("SELECTEDSUBCATEGORY"));
            intent.putExtra("PARENT_ID", parentId);
            intent.putExtra("I_CLASSIFICATION_ID", inventoryClassificationId);
            startActivityForResult(intent, 100);
        } else {
            showMessage(getString(R.string.no_information_text));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();//finishing activity
        }
    }

    @Override
    public void addMtPcsSpinner(Context ctx, Spinner spinner) {
        List<String> list = new ArrayList<>();
        list.add(ctx.getString(R.string.mt));
        list.add(ctx.getString(R.string.pcs));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(ctx, R.layout.spinner_item_mt_pcs, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }

    @Override
    public void showMessages(String message) {
        showMessage(message);
    }

    @Override
    public void hideKeyBoards() {
        hideKeyboard();
    }

    @Override
    public void saveResultList(MyOrderResultPOJO myOrderResultPOJO) {
        myOrderResultList.add(myOrderResultPOJO);
        //myOrderResultHashMap.put(inventoryClassificationId, myOrderResultList);
        setMyOrderResultListNew(myOrderResultList);
    }

    private void getItem() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getItemJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), parentId, inventoryClassificationId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetItemPOJO> call = restClientAPI.getItem(body);
        call.enqueue(new retrofit2.Callback<GetItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetItemPOJO> call, @NonNull Response<GetItemPOJO> response) {
                GetItemPOJO getItemPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "codeUp>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getItemPOJO != null) {
                        if (getItemPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getItemPOJO.getResponseStatus().equals(TRUE)) {

                                listDataHeaderItem.addAll(getItemPOJO.getData());
                                if (listDataHeaderItem != null && listDataHeaderItem.size() > 0) {
                                    getQuantityAndRate(listDataHeaderItem.get(pos).getItemId(), inventoryClassificationId);
                                }
                            } else {
                                hideLoading();
                                showMessage(getItemPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showMessage(getItemPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetItemPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void getQuantityAndRate(final Integer itemId, final Integer categoryId) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getQuantityAndRateJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), logInResList.get(0).getBranchId(), itemId, categoryId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetQuantityAndRatePOJO> call = restClientAPI.getQuantityAndRate(body);
        call.enqueue(new retrofit2.Callback<GetQuantityAndRatePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetQuantityAndRatePOJO> call, @NonNull Response<GetQuantityAndRatePOJO> response) {
                GetQuantityAndRatePOJO getQuantityAndRatePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "codeDown>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getQuantityAndRatePOJO != null) {
                        if (getQuantityAndRatePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getQuantityAndRatePOJO.getResponseStatus().equals(TRUE)) {

                                List<GetQuantityAndRatePOJO.Datum> list = new ArrayList<>(getQuantityAndRatePOJO.getData());
                                listDataChildItem.put(listDataHeaderItem.get(pos).getItemName(), list);
                                if (pos == listDataHeaderItem.size() - 1) {
                                    hashListDataHeaderItem.put(categoryId, listDataHeaderItem);
                                    hashListDataChildItem.put(itemId, listDataChildItem);
                                    setDataHeaderItemList(hashListDataHeaderItem);
                                    setDataChildItemList(hashListDataChildItem);
                                    pos = 0;
                                    hideLoading();
                                    setAdapter();
                                } else {
                                    hashListDataHeaderItem.put(categoryId, listDataHeaderItem);
                                    hashListDataChildItem.put(itemId, listDataChildItem);
                                    pos = pos + 1;
                                    getQuantityAndRate(listDataHeaderItem.get(pos).getItemId(), inventoryClassificationId);
                                }

                            } else {
                                hideLoading();
                                showMessage(getQuantityAndRatePOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showMessage(getQuantityAndRatePOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetQuantityAndRatePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    public void setDataHeaderItemList(HashMap<Integer, List<GetItemPOJO.Datum>> listDataHeader) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<GetItemPOJO.Datum>>>() {
        }.getType();
        String json = gson.toJson(listDataHeader, type);
        this.appPreferencesHelper.setDataHeaderItemListResponse(json);
    }

    public HashMap<Integer, List<GetItemPOJO.Datum>> getDataHeaderItemList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<GetItemPOJO.Datum>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getDataHeaderItemListResponse(), type);
    }

    private void setDataChildItemList(HashMap<Integer, HashMap<String, List<GetQuantityAndRatePOJO.Datum>>> listDataChildItem) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, HashMap<String, List<GetQuantityAndRatePOJO.Datum>>>>() {
        }.getType();
        String json = gson.toJson(listDataChildItem, type);
        this.appPreferencesHelper.setDataChildItemListResponse(json);
    }

    private HashMap<Integer, HashMap<String, List<GetQuantityAndRatePOJO.Datum>>> getDataChildItemList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, HashMap<String, List<GetQuantityAndRatePOJO.Datum>>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getDataChildItemListResponse(), type);
    }

//    public void setMyOrderResultList(HashMap<Integer, List<MyOrderResultPOJO>> list) {
//        Gson gson = new Gson();
//        Type type = new TypeToken<HashMap<Integer, List<MyOrderResultPOJO>>>() {
//        }.getType();
//        String json = gson.toJson(list, type);
//        this.appPreferencesHelper.setMyOrderResultListResponse(json);
//    }
//
//    private HashMap<Integer, List<MyOrderResultPOJO>> getMyOrderResultList() {
//        Gson gson = new Gson();
//        Type type = new TypeToken<HashMap<Integer, List<MyOrderResultPOJO>>>() {
//        }.getType();
//        return gson.fromJson(appPreferencesHelper.getMyOrderResultListListResponse(), type);
//    }

    public void setMyOrderResultListNew(List<MyOrderResultPOJO> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MyOrderResultPOJO>>() {
        }.getType();
        String json = gson.toJson(list, type);
        this.appPreferencesHelper.setMyOrderResultListResponse(json);
    }

    private List<MyOrderResultPOJO> getMyOrderResultListNew() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MyOrderResultPOJO>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getMyOrderResultListListResponse(), type);
    }
}