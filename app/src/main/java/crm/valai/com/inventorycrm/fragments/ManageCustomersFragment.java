package crm.valai.com.inventorycrm.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.adapters.ManageCustomerAdapter;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.interfaces.GetCustomers;
import crm.valai.com.inventorycrm.modals.GetCustomerDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetSalesmanCustomerPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.utils.RecyclerItemClickListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

public class ManageCustomersFragment extends BaseFragment implements GetCustomers {
    public static final String TAG = ManageCustomersFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentListner fragmentListner;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.tvNoResult)
    TextView tvNoResult;

    @BindView(R.id.addNewCustomer)
    FloatingActionButton mFloatingActionButton;

    @BindView(R.id.spnrCustomerType)
    Spinner spnrCustomerType;

    private List<GetSalesmanCustomerPOJO.Datum> listGetSalesManCustomer;
    private List<GetCustomerDetailsPOJO.Datum> listCustomerDetails;
    private HashMap<Integer, List<GetCustomerDetailsPOJO.Datum>> hashCustomerDetailsList;
    private List<LogInPOJO.Datum> logInResList;
    private ManageCustomerAdapter adapter;
    public static GetCustomers getCustomers;
    private Integer customerId;

    public ManageCustomersFragment() {
        // Required empty public constructor
    }

    public static ManageCustomersFragment newInstance(String param1, String param2) {
        ManageCustomersFragment fragment = new ManageCustomersFragment();
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
        getCustomers = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_manage_customers, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setAutoCompleteAdapter();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    mFloatingActionButton.hide();
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    mFloatingActionButton.show();
                }
            }
        });

        logInResList = new ArrayList<>();
        logInResList = fragmentListner.getSignInResultList();
        listGetSalesManCustomer = new ArrayList<>();

        listCustomerDetails = new ArrayList<>();
        hashCustomerDetailsList = new HashMap<>();

        getSalesmanCustomerCall();
        //setRecycleTouchListener();
    }

    private void getSalesmanCustomerCall() {
        if (!isNetworkConnected()) {
            listGetSalesManCustomer = fragmentListner.getSalesManCustomerList();
            if (listGetSalesManCustomer != null && listGetSalesManCustomer.size() > 0) {
                getSalesManCustomer();
            } else {
                getSalesManCustomer();
                showMessage(getString(R.string.internet_not_available));
            }
        } else if (logInResList != null && logInResList.size() > 0) {
            getSalesManCustomer();
        }
    }

    private void setAdapter() {
        if (listCustomerDetails != null && listCustomerDetails.size() > 0) {
            tvNoResult.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
            if (adapter == null) {
                adapter = new ManageCustomerAdapter(getContext(), listCustomerDetails, getCustomers);
                recycleView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
        }
    }

    private void setRecycleTouchListener() {
        recycleView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recycleView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showDialogExpenseDetails(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @OnClick(R.id.addNewCustomer)
    public void addNewCustomerClick() {
        AddCustomerDialogue.newInstance().show(getFragmentManager());
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

    private void getSalesManCustomer() {
        showLoading();
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
                                addCustomerTypeSpinner();
                            } else {
                                hideLoading();
                                addCustomerTypeSpinner();
                                showMessage(getSalesmanCustomerPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            addCustomerTypeSpinner();
                            showMessage(getSalesmanCustomerPOJO.getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetSalesmanCustomerPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                addCustomerTypeSpinner();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void getCustomerDetails(final Integer customerId) {
        clearList();
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
                                listCustomerDetails.addAll(getCustomerDetailsPOJO.getData());
                                hashCustomerDetailsList.put(customerId, listCustomerDetails);
                                fragmentListner.setCustomerDetailsList(hashCustomerDetailsList);
                                setAdapter();
                            } else {
                                setAdapter();
                                showMessage(getCustomerDetailsPOJO.getResponseMessage());
                            }
                        } else {
                            setAdapter();
                            showMessage(getCustomerDetailsPOJO.getResponseMessage());
                        }
                    }
                } else {
                    hideLoading();
                    setAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCustomerDetailsPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void getCustomers() {
        clearListCustomers();
        getSalesmanCustomerCall();
    }

    @Override
    public void showDialogDetails(int position) {
        showDialogExpenseDetails(position);
    }

    @Override
    public void openEditCustomerActivity(int position) {
        if (listCustomerDetails != null && listCustomerDetails.size() > 0) {
            EditCustomerDialogue.newInstance(listCustomerDetails.get(position), customerId)
                    .show(getFragmentManager());
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    void addCustomerTypeSpinner() {
        List<String> list = new ArrayList<>();
        if (listGetSalesManCustomer != null && listGetSalesManCustomer.size() > 0) {
            for (int i = 0; i < listGetSalesManCustomer.size(); i++) {
                list.add(listGetSalesManCustomer.get(i).getCustomerName());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrCustomerType.setAdapter(dataAdapter);
        //spnrCustomerType.setSelection(0, false);
        spnrCustomerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseSparseArrays")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hideKeyboard();

                hashCustomerDetailsList = fragmentListner.getCustomerDetailsList();
                if (hashCustomerDetailsList == null) {
                    hashCustomerDetailsList = new HashMap<>();
                }

                if (listGetSalesManCustomer.size() > 0) {
                    customerId = listGetSalesManCustomer.get(position).getCustomerId();
                    fragmentListner.getAppPreferenceHelper().setCustomerId(customerId);
                    if (!isNetworkConnected()) {
                        if (hashCustomerDetailsList != null && hashCustomerDetailsList.size() > 0 && hashCustomerDetailsList.containsKey(listGetSalesManCustomer.get(position).getCustomerId())) {
                            clearList();
                            listCustomerDetails.addAll(hashCustomerDetailsList.get(customerId));
                            if (listCustomerDetails != null && listCustomerDetails.size() > 0) {
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
                        getCustomerDetails(customerId);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }

    private void clearListCustomers() {
        if (listGetSalesManCustomer != null && listGetSalesManCustomer.size() > 0) {
            listGetSalesManCustomer.clear();
        }
    }

    private void clearList() {
        if (listCustomerDetails != null && listCustomerDetails.size() > 0) {
            listCustomerDetails.clear();
        }
    }

    @SuppressLint({"SetTextI18n", "UseSparseArrays"})
    private void showDialogExpenseDetails(int position) {
        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customer_detail_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setCanceledOnTouchOutside(false);

        TextView tvCustomerName = dialog.findViewById(R.id.tvCustomerName);
        TextView tvCustomerPhoneNumber = dialog.findViewById(R.id.tvCustomerPhoneNumber);
        TextView tvCustomerEmail = dialog.findViewById(R.id.tvCustomerEmail);
        TextView tvContactPerson = dialog.findViewById(R.id.tvContactPerson);
        TextView tvContactPersonDesignation = dialog.findViewById(R.id.tvContactPersonDesignation);
        TextView tvMobileNumber = dialog.findViewById(R.id.tvMobileNumber);
        TextView tvEmail = dialog.findViewById(R.id.tvEmail);
        TextView tvCreditLimit = dialog.findViewById(R.id.tvCreditLimit);
        TextView tvCreditDays = dialog.findViewById(R.id.tvCreditDays);
        TextView tvPanNumber = dialog.findViewById(R.id.tvPanNumber);
        TextView tvTinNumber = dialog.findViewById(R.id.tvTinNumber);
        TextView tvGstNumber = dialog.findViewById(R.id.tvGstNumber);
        TextView tvNature = dialog.findViewById(R.id.tvNature);
        TextView tvStartDateBusiness = dialog.findViewById(R.id.tvStartDateBusiness);
        TextView tvCustomerType = dialog.findViewById(R.id.tvCustomerType);
        TextView tvModeOfPayment = dialog.findViewById(R.id.tvModeOfPayment);
        TextView tvCurrentAddress = dialog.findViewById(R.id.tvCurrentAddress);
        TextView tvZipCode = dialog.findViewById(R.id.tvZipCode);
        TextView tvShippingAddress = dialog.findViewById(R.id.tvShippingAddress);
        TextView tvShippingZipCode = dialog.findViewById(R.id.tvShippingZipCode);
        TextView tvAnnualTurnOver = dialog.findViewById(R.id.tvAnnualTurnOver);
        TextView tvReferredBy = dialog.findViewById(R.id.tvReferredBy);
        TextView tvStatus = dialog.findViewById(R.id.tvStatus);

        ImageButton btnClose = dialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        if (listCustomerDetails != null && listCustomerDetails.size() > 0) {
            tvCustomerName.setText(listCustomerDetails.get(position).getCustomerName());
            tvCustomerPhoneNumber.setText(listCustomerDetails.get(position).getCustomerPhoneNumber());
            tvCustomerEmail.setText(listCustomerDetails.get(position).getCustomerEmail());
            tvContactPerson.setText(listCustomerDetails.get(position).getContactPersonName());
            tvContactPersonDesignation.setText(listCustomerDetails.get(position).getContactPersonDesignation());
            tvMobileNumber.setText(listCustomerDetails.get(position).getContactPersonMobileNumber());
            tvEmail.setText(listCustomerDetails.get(position).getContactPersonEmail());
            tvCreditLimit.setText(String.valueOf(listCustomerDetails.get(position).getCreditLimit()));
            tvCreditDays.setText(String.valueOf(listCustomerDetails.get(position).getCreditDays()));
            tvPanNumber.setText(String.valueOf(listCustomerDetails.get(position).getPanNumber()));
            if (listCustomerDetails.get(position).getTinNumber() != null) {
                tvTinNumber.setText(String.valueOf(listCustomerDetails.get(position).getTinNumber()));
            }
            tvGstNumber.setText(String.valueOf(listCustomerDetails.get(position).getGstNumber()));

            if (listCustomerDetails.get(position).getNatureOfBuisness() != null) {
                tvNature.setText(String.valueOf(listCustomerDetails.get(position).getNatureOfBuisness()));
            }

            if (listCustomerDetails.get(position).getBuisnessStartDate() != null) {
                tvStartDateBusiness.setText(String.valueOf(listCustomerDetails.get(position).getBuisnessStartDate()));
            }

            if(listCustomerDetails.get(position).getCustomerType() != null){
                if (listCustomerDetails.get(position).getCustomerType() == 1) {
                    tvCustomerType.setText(getString(R.string.targeted_text));
                } else if (listCustomerDetails.get(position).getCustomerType() == 2) {
                    tvCustomerType.setText(getString(R.string.non_target_text));
                } else {
                    tvCustomerType.setText(null);
                }
            }else {
                tvCustomerType.setText(null);
            }

            if(listCustomerDetails.get(position).getModeOfPayment() != null){
                if (listCustomerDetails.get(position).getModeOfPayment() == 1) {
                    tvModeOfPayment.setText(getString(R.string.cash));
                } else if (listCustomerDetails.get(position).getCustomerType() == 2) {
                    tvModeOfPayment.setText(getString(R.string.rtgs));
                } else if (listCustomerDetails.get(position).getCustomerType() == 3) {
                    tvModeOfPayment.setText(getString(R.string.bg));
                } else if (listCustomerDetails.get(position).getCustomerType() == 4) {
                    tvModeOfPayment.setText(getString(R.string.check));
                } else if (listCustomerDetails.get(position).getCustomerType() == 5) {
                    tvModeOfPayment.setText(getString(R.string.lc));
                } else {
                    tvModeOfPayment.setText(null);
                }
            }else {
                tvModeOfPayment.setText(null);
            }

            if (listCustomerDetails.get(position).getCurrentAddress() != null) {
                tvCurrentAddress.setText(String.valueOf(listCustomerDetails.get(position).getCurrentAddress()));
            }

            if (listCustomerDetails.get(position).getCurrentZipcode() != null) {
                tvZipCode.setText(String.valueOf(listCustomerDetails.get(position).getCurrentZipcode()));
            }

            if (listCustomerDetails.get(position).getShippingAddress() != null) {
                tvShippingAddress.setText(String.valueOf(listCustomerDetails.get(position).getShippingAddress()));
            }

            if (listCustomerDetails.get(position).getShippingZipcode() != null) {
                tvShippingZipCode.setText(String.valueOf(listCustomerDetails.get(position).getShippingZipcode()));
            }

            if (listCustomerDetails.get(position).getAnnualTurnover() != null) {
                tvAnnualTurnOver.setText(String.valueOf(listCustomerDetails.get(position).getAnnualTurnover()));
            }

            if (listCustomerDetails.get(position).getRefferedBy() != null) {
                tvReferredBy.setText(String.valueOf(listCustomerDetails.get(position).getRefferedBy()));
            }

            if (listCustomerDetails.get(position).getStatus() == 0) {
                tvStatus.setText(getString(R.string.customer_created));
//                tvStatus.setBackgroundColor(Color.parseColor("#840AD9"));
//                tvStatus.setTextColor(Color.WHITE);
            } else if (listCustomerDetails.get(position).getStatus() == 1) {
                tvStatus.setText(getString(R.string.order_submitted_for_approval));
//                tvStatus.setBackgroundColor(Color.parseColor("#FFB822"));
//                tvStatus.setTextColor(Color.WHITE);
            } else if (listCustomerDetails.get(position).getStatus() == 2) {
                tvStatus.setText(getString(R.string.approved));
//                tvStatus.setBackgroundColor(Color.parseColor("#2CA189"));
//                tvStatus.setTextColor(Color.WHITE);
            } else if (listCustomerDetails.get(position).getStatus() == 3) {
                tvStatus.setText(getString(R.string.needs_modification));
//                tvStatus.setBackgroundColor(Color.parseColor("#564EC0"));
//                tvStatus.setTextColor(Color.WHITE);
            } else if (listCustomerDetails.get(position).getStatus() == 4) {
                tvStatus.setText(getString(R.string.rejected));
//                tvStatus.setBackgroundColor(Color.parseColor("#F22D4E"));
//                tvStatus.setTextColor(Color.WHITE);
            }
        }

        dialog.show();
    }
}