package crm.valai.com.inventorycrm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.adapters.SalesDetailsListAdapter;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.GetCustomerDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetExpenseTypePOJO;
import crm.valai.com.inventorycrm.modals.GetSalesmanCustomerPOJO;
import crm.valai.com.inventorycrm.modals.GetStaffDailyTrackerPOJO;
import crm.valai.com.inventorycrm.modals.InsertUpdateItemPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.utils.CommonUtils;
import crm.valai.com.inventorycrm.utils.RecyclerItemTouchHelperSales;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.CREATE;
import static crm.valai.com.inventorycrm.utils.AppConstants.DELETE;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_PURPOSE_OF_VISIT;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

/**
 * @author by Mohit Arora on 31/3/18.
 */
public class SalesTrackerTabTwoFragment extends BaseFragment implements RecyclerItemTouchHelperSales.RecyclerItemTouchHelperListener {
    public static final String TAG = SalesTrackerTabTwoFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentListner fragmentListner;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.tvNoResult)
    TextView tvNoResult;

    @BindView(R.id.spnrModeOfCommunication)
    Spinner spnrModeOfCommunication;

    @BindView(R.id.spnPurposeOfVisit)
    Spinner spnPurposeOfVisit;

    @BindView(R.id.edtPlaceVisited)
    EditText edtPlaceVisited;

    @BindView(R.id.edtCustomerName)
    AutoCompleteTextView edtCustomerName;

    @BindView(R.id.edtContactNumber)
    EditText edtContactNumber;

    @BindView(R.id.edtOrder)
    EditText edtOrder;

    @BindView(R.id.edtCollection)
    EditText edtCollection;

    @BindView(R.id.edtRemarks)
    EditText edtRemarks;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    private List<GetStaffDailyTrackerPOJO.Datum> salesDetailsList;
    private List<GetSalesmanCustomerPOJO.Datum> listGetSalesManCustomer;
    private List<GetExpenseTypePOJO.Datum> listPurposeOfVisit;
    private SalesDetailsListAdapter adapter;
    private List<LogInPOJO.Datum> logInResList;
    private Integer modeOfCommunication;
    private Integer customerId = 0;
    private Integer order = 0;
    private Integer collections = 0;
    private String purposeOfVisit;

    public SalesTrackerTabTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    clearList();
                    customerId = 0;
                    edtContactNumber.setText(null);
                    edtCustomerName.setText(null);
                    edtPlaceVisited.setText(null);
                    edtRemarks.setText(null);
                    edtOrder.setText(null);
                    edtCollection.setText(null);
                    if (logInResList != null && logInResList.size() > 0) {
                        getStaffDailyTracker("FromAdapter");
                    }
                }
            }, 500);
        }
    }

    public static SalesTrackerTabTwoFragment newInstance(String param1, String param2) {
        SalesTrackerTabTwoFragment fragment = new SalesTrackerTabTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sales_tracker_tab_two, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logInResList = fragmentListner.getSignInResultList();

        addModeOfCommunicationSpinner();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //recycleView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperSales(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycleView);

        salesDetailsList = new ArrayList<>();
        listGetSalesManCustomer = new ArrayList<>();
        listPurposeOfVisit = new ArrayList<>();
        setAdapter();

        if (!isNetworkConnected()) {
            listGetSalesManCustomer = fragmentListner.getSalesManCustomerList();
            if (listGetSalesManCustomer != null && listGetSalesManCustomer.size() > 0) {
                setAutoCompleteAdapter();
            } else {
                setAutoCompleteAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        } else if (logInResList != null && logInResList.size() > 0) {
            getSalesManCustomer();
            getPurposeOfVisit();
        }
    }

    private void setAdapter() {
        if (salesDetailsList != null && salesDetailsList.size() > 0) {
            tvNoResult.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
            if (adapter == null) {
                adapter = new SalesDetailsListAdapter(getContext(), salesDetailsList);
                recycleView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
        }
    }

    private void setAutoCompleteAdapter() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listGetSalesManCustomer.size(); i++) {
            list.add(listGetSalesManCustomer.get(i).getCustomerName());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, list);
        edtCustomerName.setThreshold(1);//start List viewing from first character
        edtCustomerName.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        edtCustomerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                for (int i = 0; i < listGetSalesManCustomer.size(); i++) {
                    if (listGetSalesManCustomer.get(i).getCustomerName().equals(arg0.getItemAtPosition(position))) {
                        customerId = listGetSalesManCustomer.get(i).getCustomerId();
                        getCustomerDetails(customerId);
                        getStaffDailyTracker("FromAdapter");
                    }
                }
            }
        });
    }

    private void clearList() {
        if (salesDetailsList != null && salesDetailsList.size() > 0) {
            salesDetailsList.clear();
        }
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitClick() {
        hideKeyboard();

        if (edtCustomerName.getText().toString().equals("")) {
            showMessage(getString(R.string.enter_customer_name));
            return;
        }

        if (spnPurposeOfVisit.getSelectedItem().toString().equals(getString(R.string.purpose_of_visit_text))) {
            showMessage(getString(R.string.purpose_of_visit_text));
            return;
        }

        if (edtOrder.getText().toString().equals("")) {
            order = 0;
        } else {
            order = Integer.parseInt(edtOrder.getText().toString());
        }

        if (edtCollection.getText().toString().equals("")) {
            collections = 0;
        } else {
            collections = Integer.parseInt(edtCollection.getText().toString());
        }

        insertUpdateSalesReport();
    }

    void addModeOfCommunicationSpinner() {
        List<String> list = new ArrayList<>();
        list.add("Telephone");
        list.add("Visit");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrModeOfCommunication.setAdapter(dataAdapter);
        //spnrCustomerType.setSelection(0, false);
        spnrModeOfCommunication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItem().equals("Telephone")) {
                    modeOfCommunication = 1;
                } else {
                    modeOfCommunication = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }

    void purposeOfVisitSpinner() {
        List<String> list = new ArrayList<>();
        list.add(getString(R.string.purpose_of_visit_text));
        for (int i = 0; i < listPurposeOfVisit.size(); i++) {
            list.add(listPurposeOfVisit.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPurposeOfVisit.setAdapter(dataAdapter);
        //spnrCustomerType.setSelection(0, false);
        spnPurposeOfVisit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    purposeOfVisit = String.valueOf(listPurposeOfVisit.get(position - 1).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }

    private void insertUpdateSalesReport() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.insertUpdateEmployDailyStatusJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(),
                logInResList.get(0).getCompId(), logInResList.get(0).getStaffId(), customerId, CommonUtils.getCurrentDateForGetOrder(), 0,
                CREATE, edtCustomerName.getText().toString(), edtPlaceVisited.getText().toString(), modeOfCommunication,
                purposeOfVisit, 0, 1, order, 1, collections,
                edtRemarks.getText().toString(), fragmentListner.getAppPreferenceHelper().getUserName(),
                fragmentListner.getAppPreferenceHelper().getIpAddress(), edtContactNumber.getText().toString(), "");

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateEmployDailyStatus(body);
        call.enqueue(new retrofit2.Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                InsertUpdateItemPOJO getOrdersPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    //hideLoading();
                    if (getOrdersPOJO != null) {
                        if (getOrdersPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getOrdersPOJO.getResponseStatus().equals(TRUE)) {
                                getStaffDailyTracker("AfterInsert");
                                //showMessage(getOrdersPOJO.getResponseMessage());
                            } else {
                                hideLoading();
                                showMessage(getOrdersPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                        }
                    } else {
                        hideLoading();
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void getStaffDailyTracker(final String from) {
        hideKeyboard();
        if (from.equals("AfterInsert")) {
            showLoading();
        }

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getStaffDailyTrackerJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(),
                logInResList.get(0).getCompId(), logInResList.get(0).getStaffId(), customerId, CommonUtils.getCurrentDateForGetOrder(),
                CommonUtils.getCurrentDateForGetOrder());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetStaffDailyTrackerPOJO> call = restClientAPI.getStaffDailyTracker(body);
        call.enqueue(new retrofit2.Callback<GetStaffDailyTrackerPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetStaffDailyTrackerPOJO> call, @NonNull Response<GetStaffDailyTrackerPOJO> response) {
                GetStaffDailyTrackerPOJO getOrdersPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getOrdersPOJO != null) {
                        if (getOrdersPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getOrdersPOJO.getResponseStatus().equals(TRUE)) {
                                clearList();
                                if (from.equals("AfterInsert")) {
                                    clearAllFields();
                                }
                                salesDetailsList.addAll(getOrdersPOJO.getData());
                                setAdapter();
                            } else {
                                clearList();
                                setAdapter();
                                showMessage(getOrdersPOJO.getResponseMessage());
                            }
                        } else {
                            clearList();
                            setAdapter();
                        }
                    } else {
                        clearList();
                        setAdapter();
                    }

                } else {
                    clearList();
                    setAdapter();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetStaffDailyTrackerPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                clearList();
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void deleteSalesDetails(Integer reportId) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.deleteSalesJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(),
                logInResList.get(0).getCompId(), logInResList.get(0).getStaffId(), CommonUtils.getCurrentDateForGetOrder(), reportId, DELETE);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateEmployDailyStatus(body);
        call.enqueue(new retrofit2.Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                InsertUpdateItemPOJO insertUpdateItemPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (insertUpdateItemPOJO != null) {
                        if (insertUpdateItemPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (insertUpdateItemPOJO.getResponseStatus().equals(TRUE)) {
                                showMessage(insertUpdateItemPOJO.getResponseMessage());
                            } else {
                                showMessage(insertUpdateItemPOJO.getResponseMessage());
                            }
                        } else {
                            showMessage(insertUpdateItemPOJO.getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

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
                                fragmentListner.setSalesManCustomerList(listGetSalesManCustomer);
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

    private void getPurposeOfVisit() {
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getPurposeOfVisitJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), GET_PURPOSE_OF_VISIT);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetExpenseTypePOJO> call = restClientAPI.getExpenseType(body);
        call.enqueue(new retrofit2.Callback<GetExpenseTypePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetExpenseTypePOJO> call, @NonNull Response<GetExpenseTypePOJO> response) {
                GetExpenseTypePOJO getPurposeOfVisit = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getPurposeOfVisit != null) {
                        if (getPurposeOfVisit.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getPurposeOfVisit.getResponseStatus().equals(TRUE)) {
                                listPurposeOfVisit.addAll(getPurposeOfVisit.getData());
                                purposeOfVisitSpinner();
                            } else {
                                purposeOfVisitSpinner();
                                showMessage(getPurposeOfVisit.getResponseMessage());
                            }
                        } else {
                            purposeOfVisitSpinner();
                            showMessage(getPurposeOfVisit.getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetExpenseTypePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                purposeOfVisitSpinner();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void getCustomerDetails(final Integer customerId) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getCustomerDetailsJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                customerId, logInResList.get(0).getToken());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetCustomerDetailsPOJO> call = restClientAPI.getCustomerDetails(body);
        call.enqueue(new retrofit2.Callback<GetCustomerDetailsPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetCustomerDetailsPOJO> call, @NonNull Response<GetCustomerDetailsPOJO> response) {
                GetCustomerDetailsPOJO getCustomerDetailsPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getCustomerDetailsPOJO != null) {
                        if (getCustomerDetailsPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getCustomerDetailsPOJO.getResponseStatus().equals(TRUE)) {
                                edtContactNumber.setText(getCustomerDetailsPOJO.getData().get(0).getCustomerPhoneNumber());
                                edtPlaceVisited.setText(getCustomerDetailsPOJO.getData().get(0).getCurrentAddress());
                            } else {
                                edtContactNumber.setText(null);
                                edtPlaceVisited.setText(null);
                                showMessage(getCustomerDetailsPOJO.getResponseMessage());
                            }
                        } else {
                            edtContactNumber.setText(null);
                            edtPlaceVisited.setText(null);
                            showMessage(getCustomerDetailsPOJO.getResponseMessage());
                        }
                    }
                } else {
                    edtContactNumber.setText(null);
                    edtPlaceVisited.setText(null);
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCustomerDetailsPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                edtContactNumber.setText(null);
                edtPlaceVisited.setText(null);
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof SalesDetailsListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = salesDetailsList.get(viewHolder.getAdapterPosition()).getCustomerName();

            // backup of removed item for undo purpose
            final GetStaffDailyTrackerPOJO.Datum deletedItem = salesDetailsList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            deleteSalesDetails(deletedItem.getReportId());
            checkListSize();
        }
    }

    private void checkListSize() {
        if (salesDetailsList != null && salesDetailsList.size() == 0) {
            tvNoResult.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
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

    private void clearAllFields() {
        customerId = 0;
        edtContactNumber.setText(null);
        edtCustomerName.setText(null);
        edtPlaceVisited.setText(null);
        edtRemarks.setText(null);
        edtOrder.setText(null);
        edtCollection.setText(null);
        addModeOfCommunicationSpinner();
        purposeOfVisitSpinner();
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}