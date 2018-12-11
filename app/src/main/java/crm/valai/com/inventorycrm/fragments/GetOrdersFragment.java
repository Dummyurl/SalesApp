package crm.valai.com.inventorycrm.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.adapters.GetOrderAdapter;
import crm.valai.com.inventorycrm.adapters.OrderDetailsAdapter;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.GetOrderDetailPOJO;
import crm.valai.com.inventorycrm.modals.GetOrdersPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.utils.CommonUtils;
import crm.valai.com.inventorycrm.utils.RecyclerItemClickListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.FROM_DATE_TAG;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_ORDER_BY_DATE;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_ORDER_DETAIL;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

public class GetOrdersFragment extends BaseFragment {
    public static final String TAG = GetOrdersFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentListner fragmentListner;

    @BindView(R.id.edtGetDate)
    EditText edtGetDate;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.tvNoResult)
    TextView tvNoResult;

    @BindView(R.id.btnAdd)
    FloatingActionButton mFloatingActionButton;

    private List<LogInPOJO.Datum> logInResList;
    private List<GetOrdersPOJO.Datum> listGetOrder;
    private List<GetOrderDetailPOJO.Datum> listOrderDetails;
    private HashMap<String, List<GetOrdersPOJO.Datum>> hashListGetOrder;
    private String selectedDate;
    private LinearLayout llOrderDetails;
    private RecyclerView recyclerView;
    private TextView tvNoResultOrder;
    private TextView tvTotal;
    private TextView tvTax;
    private TextView tvDiscount;
    private TextView tvPlPlus;
    private TextView tvPlMinus;
    private TextView tvFor;

    public GetOrdersFragment() {
        // Required empty public constructor
    }

    public static GetOrdersFragment newInstance(String param1, String param2) {
        GetOrdersFragment fragment = new GetOrdersFragment();
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
        View rootView = inflater.inflate(R.layout.get_order_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //recycleView.addItemDecoration(new SimpleDividerItemDecoration(this));
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

        setRecycleTouchListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        logInResList = new ArrayList<>();
        logInResList = fragmentListner.getSignInResultList();

        selectedDate = CommonUtils.getCurrentDateForGetOrder();
        edtGetDate.setText(CommonUtils.getCurrentDateNew());

        listGetOrder = new ArrayList<>();
        hashListGetOrder = new HashMap<>();

        if (!isNetworkConnected()) {
            hashListGetOrder = fragmentListner.getOrderList();
            if (hashListGetOrder != null && hashListGetOrder.size() > 0 && hashListGetOrder.containsKey(selectedDate)) {
                listGetOrder = hashListGetOrder.get(selectedDate);
                if (listGetOrder != null && listGetOrder.size() > 0) {
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
            getOrders(selectedDate);
        }
    }

    @OnClick(R.id.edtGetDate)
    public void onSelectFromDateClick() {
        hideKeyboard();
        showDatePickerDialog(FROM_DATE_TAG);
    }

    @OnClick(R.id.btnAdd)
    public void onAddButtonClick() {
        hideKeyboard();
        Intent intent = new Intent(getContext(), PlaceOrdersFragment.class);
        startActivity(intent);
    }

    public void showDatePickerDialog(final String tag) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.TimePickerTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String new_month, new_day;
                        // Set day of month , month and year value in the edit text
                        if (monthOfYear + 1 < 10) {
                            new_month = "0" + String.valueOf(monthOfYear + 1);
                        } else {
                            new_month = String.valueOf(monthOfYear + 1);
                        }

                        if (dayOfMonth < 10) {
                            new_day = "0" + String.valueOf(dayOfMonth);
                        } else {
                            new_day = String.valueOf(dayOfMonth);
                        }

                        selectedDate = (year + "-" + new_month + "-" + new_day);
                        setDateWithTagName(tag, new_month + "/" + new_day + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void setDateWithTagName(String tag, String date) {
        if (tag.equals(FROM_DATE_TAG)) {
            edtGetDate.setText(date);
            listGetOrder = new ArrayList<>();
            hashListGetOrder = new HashMap<>();

            if (!isNetworkConnected()) {
                hashListGetOrder = fragmentListner.getOrderList();
                if (hashListGetOrder != null && hashListGetOrder.size() > 0 && hashListGetOrder.containsKey(selectedDate)) {
                    listGetOrder = hashListGetOrder.get(selectedDate);
                    if (listGetOrder != null && listGetOrder.size() > 0) {
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
                getOrders(selectedDate);
            }
        }
    }

    private void setAdapter() {
        if (listGetOrder != null && listGetOrder.size() > 0) {
            tvNoResult.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
            GetOrderAdapter adapter = new GetOrderAdapter(getContext(), listGetOrder);
            recycleView.setAdapter(adapter);
        } else {
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

    private void getOrders(final String date) {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getOrderJson(logInResList.get(0).getCompId(), logInResList.get(0).getStaffId(),
                date, fragmentListner.getAppPreferenceHelper().getDeviceId(),
                logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), GET_ORDER_BY_DATE);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetOrdersPOJO> call = restClientAPI.getOrders(body);
        call.enqueue(new retrofit2.Callback<GetOrdersPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetOrdersPOJO> call, @NonNull Response<GetOrdersPOJO> response) {
                GetOrdersPOJO getOrdersPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getOrdersPOJO != null) {
                        if (getOrdersPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getOrdersPOJO.getResponseStatus().equals(TRUE)) {
                                clearLastListValue();
                                listGetOrder.addAll(getOrdersPOJO.getData());
                                hashListGetOrder.put(date, listGetOrder);
                                fragmentListner.setGetOrderList(hashListGetOrder);
                                setAdapter();
                            } else {
                                clearLastListValue();
                                hideLoading();
                                setAdapter();
                                showMessage(getOrdersPOJO.getResponseMessage());
                            }
                        } else {
                            clearLastListValue();
                            hideLoading();
                            setAdapter();
                            showMessage(getOrdersPOJO.getResponseMessage());
                        }
                    } else {
                        clearLastListValue();
                        hideLoading();
                        setAdapter();
                    }

                } else {
                    clearLastListValue();
                    hideLoading();
                    setAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetOrdersPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                clearLastListValue();
                hideLoading();
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void clearLastListValue() {
        if (listGetOrder != null && listGetOrder.size() > 0) {
            listGetOrder.clear();
        }
    }

    private void setRecycleTouchListener() {
        recycleView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recycleView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showDialogOrderDetails(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @SuppressLint("SetTextI18n")
    private void showDialogOrderDetails(int position) {
        listOrderDetails = new ArrayList<>();
        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_detail_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setCanceledOnTouchOutside(false);

        TextView tvCustomerName = dialog.findViewById(R.id.tvCustomerName);
        TextView tvOrderRef = dialog.findViewById(R.id.tvOrderRef);
        TextView tvEmpName = dialog.findViewById(R.id.tvEmpName);
        TextView tvTotalAmount = dialog.findViewById(R.id.tvTotalAmount);
        TextView tvStatus = dialog.findViewById(R.id.tvStatus);
        TextView tvAmount = dialog.findViewById(R.id.tvAmount);
        TextView tvTotalTaxAmount = dialog.findViewById(R.id.tvTotalTaxAmount);
        TextView tvOrderDate = dialog.findViewById(R.id.tvOrderDate);
        TextView tvManagerRemarks = dialog.findViewById(R.id.tvManagerRemarks);
        TextView tvStaffRemarks = dialog.findViewById(R.id.tvStaffRemarks);
        ImageButton btnClose = dialog.findViewById(R.id.btnClose);
        llOrderDetails = dialog.findViewById(R.id.llOrderDetails);
        recyclerView = dialog.findViewById(R.id.recycleView);
        tvNoResultOrder = dialog.findViewById(R.id.tvNoResult);
        tvTotal = dialog.findViewById(R.id.tvTotal);
        tvTax = dialog.findViewById(R.id.tvTax);
        tvDiscount = dialog.findViewById(R.id.tvDiscount);
        tvPlPlus = dialog.findViewById(R.id.tvPlPlus);
        tvPlMinus = dialog.findViewById(R.id.tvPlMinus);
        tvFor = dialog.findViewById(R.id.tvFor);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recycleView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvCustomerName.setText(listGetOrder.get(position).getCustomerName());
        tvOrderRef.setText(listGetOrder.get(position).getOrderRefNumber());
        tvEmpName.setText(listGetOrder.get(position).getManager());
        tvTotalAmount.setText(String.valueOf(listGetOrder.get(position).getTotalAmount()) + " " + getString(R.string.rupees));
        tvAmount.setText(String.valueOf(listGetOrder.get(position).getAmount()) + " " + getString(R.string.rupees));
        tvOrderDate.setText(String.valueOf(CommonUtils.convertDateStringFormat(listGetOrder.get(position).getOrderDate())));
        tvTotalTaxAmount.setText(String.valueOf(listGetOrder.get(position).getTotalTaxAmount()) + " " + getString(R.string.rupees));
        if (listGetOrder.get(position).getManagerRemarks() != null) {
            tvManagerRemarks.setText(String.valueOf(listGetOrder.get(position).getManagerRemarks()));
        } else {
            tvManagerRemarks.setText("");
        }

        if (listGetOrder.get(position).getStaffRemarks() != null) {
            tvStaffRemarks.setText(String.valueOf(listGetOrder.get(position).getStaffRemarks()));
        } else {
            tvStaffRemarks.setText("");
        }

        if (listGetOrder.get(position).getStatus() == 1) {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setText(getString(R.string.order_submitted_for_approval));
            tvStatus.setBackgroundColor(Color.parseColor("#FFB822"));
            tvStatus.setTextColor(Color.BLACK);
        } else if (listGetOrder.get(position).getStatus() == 2) {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setText(getString(R.string.manager_approved));
            tvStatus.setBackgroundColor(Color.parseColor("#2CA189"));
            tvStatus.setTextColor(Color.WHITE);
        } else if (listGetOrder.get(position).getStatus() == 3) {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setText(getString(R.string.accounts_approved));
            tvStatus.setBackgroundColor(Color.parseColor("#2CA189"));
            tvStatus.setTextColor(Color.WHITE);
        } else if (listGetOrder.get(position).getStatus() == 4) {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setText(getString(R.string.order_closed));
            tvStatus.setBackgroundColor(Color.parseColor("#F22D4E"));
            tvStatus.setTextColor(Color.WHITE);
        } else if (listGetOrder.get(position).getStatus() == -2) {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setText(getString(R.string.manager_rejected));
            tvStatus.setBackgroundColor(Color.parseColor("#F22D4E"));
            tvStatus.setTextColor(Color.WHITE);
        } else if (listGetOrder.get(position).getStatus() == -3) {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setText(getString(R.string.accounts_rejected));
            tvStatus.setBackgroundColor(Color.parseColor("#F22D4E"));
            tvStatus.setTextColor(Color.WHITE);
        } else {
            tvStatus.setVisibility(View.INVISIBLE);
        }

        setAdapterOrderDetails();
        getOrderDetails(listGetOrder.get(position).getOrderId());
        dialog.show();
    }

    private void getOrderDetails(Integer orderId) {
        hideKeyboard();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getOrderDetailJson(logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), logInResList.get(0).getCompId(), orderId, GET_ORDER_DETAIL);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetOrderDetailPOJO> call = restClientAPI.getOrderDetail(body);
        call.enqueue(new retrofit2.Callback<GetOrderDetailPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetOrderDetailPOJO> call, @NonNull Response<GetOrderDetailPOJO> response) {
                GetOrderDetailPOJO getOrderDetailPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getOrderDetailPOJO != null) {
                        if (getOrderDetailPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getOrderDetailPOJO.getResponseStatus().equals(TRUE)) {
                                listOrderDetails.addAll(getOrderDetailPOJO.getData());
                                setAdapterOrderDetails();
                            } else {
                                setAdapterOrderDetails();
                                showMessage(getOrderDetailPOJO.getResponseMessage());
                            }
                        } else {
                            setAdapterOrderDetails();
                            showMessage(getOrderDetailPOJO.getResponseMessage());
                        }
                    } else {
                        setAdapterOrderDetails();
                    }

                } else {
                    setAdapterOrderDetails();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetOrderDetailPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setAdapterOrderDetails();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setAdapterOrderDetails() {
        if (listOrderDetails != null && listOrderDetails.size() > 0) {
            setTotalPrice();
            tvNoResultOrder.setVisibility(View.GONE);
            llOrderDetails.setVisibility(View.VISIBLE);
            OrderDetailsAdapter adapter = new OrderDetailsAdapter(getContext(), listOrderDetails);
            recyclerView.setAdapter(adapter);
        } else {
            tvNoResultOrder.setVisibility(View.VISIBLE);
            llOrderDetails.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setTotalPrice() {
        double totalPrice = 0;
        double discount = 0;
        double plPlus = 0;
        double plMinus = 0;
        double mFor = 0;
        tvTax.setText(null);
        tvTotal.setText(null);
        for (int i = 0; i < listOrderDetails.size(); i++) {
            totalPrice += listOrderDetails.get(i).getPrice();
            discount += listOrderDetails.get(i).getDiscount();
            plPlus += listOrderDetails.get(i).getPl_Postive_Discount();
            plMinus += listOrderDetails.get(i).getPl_Negative_discount();
            mFor += listOrderDetails.get(i).getFor_Discount();
        }
        tvDiscount.setText(String.valueOf(discount));
        tvPlPlus.setText(String.valueOf(plPlus));
        tvPlMinus.setText(String.valueOf(plMinus));
        tvFor.setText(String.valueOf(mFor));
        double tax = (totalPrice * 24 / 100);
        tvTax.setText(String.valueOf(tax));
        double new_price = (totalPrice + tax);
        tvTotal.setText(String.valueOf(new_price));
    }
}