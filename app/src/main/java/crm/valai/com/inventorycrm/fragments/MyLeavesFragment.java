package crm.valai.com.inventorycrm.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.activities.LeaveStatusActivity;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.GetLeaveTypePOJO;
import crm.valai.com.inventorycrm.modals.InsertUpdateItemPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.utils.CommonUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.FROM_DATE_TAG;
import static crm.valai.com.inventorycrm.utils.AppConstants.GETLEAVEDRP;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.SAVELEAVE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TO_DATE_TAG;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

public class MyLeavesFragment extends BaseFragment {
    public static final String TAG = MyLeavesFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentListner fragmentListner;

    @BindView(R.id.edtLeaveTillDate)
    EditText edtLeaveTillDate;

    @BindView(R.id.edtLeaveFromDate)
    EditText edtLeaveFromDate;

    @BindView(R.id.edtRemarks)
    EditText edtRemarks;

    @BindView(R.id.spnrStatus)
    Spinner spnrStatus;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<LogInPOJO.Datum> logInResList;
    private List<GetLeaveTypePOJO.Datum> leaveTypeList;
    private Integer leaveId;
    private Integer yearId;
    private Integer diff;

    public MyLeavesFragment() {
        // Required empty public constructor
    }

    public static MyLeavesFragment newInstance(String param1, String param2) {
        MyLeavesFragment fragment = new MyLeavesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_myleaves, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtLeaveFromDate.setText(CommonUtils.getCurrentDateNew());
        edtLeaveTillDate.setText(CommonUtils.getCurrentDateNew());
        yearId = Integer.parseInt(CommonUtils.getCurrentYear());
        diff = Integer.parseInt(String.valueOf(CommonUtils.daysBetween(edtLeaveFromDate.getText().toString(),
                edtLeaveTillDate.getText().toString()))) + 1;
        logInResList = new ArrayList<>();
        logInResList = fragmentListner.getSignInResultList();
        leaveTypeList = new ArrayList<>();

        if (logInResList != null && logInResList.size() > 0) {
            getLeaveType();
        }
    }

    private void getLeaveType() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getLeaveTypeJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), GETLEAVEDRP);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetLeaveTypePOJO> call = restClientAPI.getLeaveType(body);
        call.enqueue(new retrofit2.Callback<GetLeaveTypePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetLeaveTypePOJO> call, @NonNull Response<GetLeaveTypePOJO> response) {
                GetLeaveTypePOJO getLeaveTypePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getLeaveTypePOJO != null) {
                        if (getLeaveTypePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getLeaveTypePOJO.getResponseStatus().equals(TRUE)) {
                                leaveTypeList.addAll(getLeaveTypePOJO.getData());
                                addStatusSpinner(leaveTypeList);
                            } else {
                                hideLoading();
                                showMessage(getLeaveTypePOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showMessage(getLeaveTypePOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetLeaveTypePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void insertLeave() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.insertLeaveJson(logInResList.get(0).getCompId(), yearId,
                leaveId, logInResList.get(0).getStaffId(), diff, logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), diff, diff, 0, edtLeaveFromDate.getText().toString(),
                edtLeaveTillDate.getText().toString(), 0, edtRemarks.getText().toString(), edtRemarks.getText().toString(),
                fragmentListner.getAppPreferenceHelper().getDeviceId(), fragmentListner.getAppPreferenceHelper().getIpAddress(),
                fragmentListner.getAppPreferenceHelper().getUserName(), SAVELEAVE);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateLeaveApplication(body);
        call.enqueue(new retrofit2.Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                InsertUpdateItemPOJO getLeaveTypePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getLeaveTypePOJO != null) {
                        if (getLeaveTypePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getLeaveTypePOJO.getResponseStatus().equals(TRUE)) {
                                addStatusSpinner(leaveTypeList);
                                edtLeaveFromDate.setText(CommonUtils.getCurrentDateNew());
                                edtLeaveTillDate.setText(CommonUtils.getCurrentDateNew());
                                yearId = Integer.parseInt(CommonUtils.getCurrentYear());
                                diff = Integer.parseInt(String.valueOf(CommonUtils.daysBetween(edtLeaveFromDate.getText().toString(),
                                        edtLeaveTillDate.getText().toString()))) + 1;
                                edtRemarks.setText(null);
                                showMessage(getLeaveTypePOJO.getResponseMessage());
                            } else {
                                hideLoading();
                                showMessage(getLeaveTypePOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showMessage(getLeaveTypePOJO.getResponseMessage());
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

    void addStatusSpinner(final List<GetLeaveTypePOJO.Datum> datumList) {
        List<String> list = new ArrayList<>();
        if (datumList.size() > 0) {
            for (int i = 0; i < datumList.size(); i++) {
                list.add(datumList.get(i).getLeaveName());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrStatus.setAdapter(dataAdapter);
        //spnrStatus.setSelection(0, false);
        spnrStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hideKeyboard();
                if (datumList.size() > 0) {
                    leaveId = datumList.get(position).getLeaveId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.edtLeaveFromDate)
    public void onLeaveFromDateClick() {
        showDatePickerDialog(FROM_DATE_TAG);
    }

    @OnClick(R.id.edtLeaveTillDate)
    public void onLeaveTillDateClick() {
        showDatePickerDialog(TO_DATE_TAG);
    }

    @OnClick(R.id.btnApply)
    public void onApplyLeaveClick() {
        if (edtLeaveFromDate.getText().toString().equals("")) {
            showMessage(getString(R.string.select_date_from_text));
            return;
        }

        if (edtLeaveTillDate.getText().toString().equals("")) {
            showMessage(getString(R.string.select_date_to_text));
            return;
        }

        if (edtRemarks.getText().toString().equals("")) {
            showMessage(getString(R.string.select_remarks_text));
            return;
        }

        insertLeave();
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        hideKeyboard();
        Intent intent = new Intent(getContext(), LeaveStatusActivity.class);
        startActivityForResult(intent, 100);
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

                        setDateWithTagName(tag, new_month + "/"
                                + new_day + "/" + year);
                        //yearId = year;

                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void setDateWithTagName(String tag, String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        if (tag.equals(FROM_DATE_TAG)) {
            try {
                Date date1 = sdf.parse(date);
                Date date2 = sdf.parse(edtLeaveTillDate.getText().toString());
                if (date1.compareTo(date2) > 0) {
                    showMessage(getString(R.string.invalid_date));
                } else if (date1.compareTo(date2) <= 0) {
                    edtLeaveFromDate.setText(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (tag.equals(TO_DATE_TAG)) {
            try {
                Date date1 = sdf.parse(edtLeaveFromDate.getText().toString());
                Date date2 = sdf.parse(date);
                if (date1.compareTo(date2) > 0) {
                    showMessage(getString(R.string.invalid_date));
                } else if (date1.compareTo(date2) <= 0) {
                    edtLeaveTillDate.setText(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        diff = Integer.parseInt(String.valueOf(CommonUtils.daysBetween(edtLeaveFromDate.getText().toString(),
                edtLeaveTillDate.getText().toString()))) + 1;
    }
}