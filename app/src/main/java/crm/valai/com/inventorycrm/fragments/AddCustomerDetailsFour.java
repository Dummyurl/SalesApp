package crm.valai.com.inventorycrm.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.CustomerModel;
import crm.valai.com.inventorycrm.modals.GetExpenseTypePOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.fragments.AddCustomerDialogue.viewPagerChangeInterface;
import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.FROM_DATE_TAG;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_CUSTOMER_TYPE;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_MODE_OF_PAYMENT;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

/**
 * @author by Mohit Arora on 31/3/18.
 */
public class AddCustomerDetailsFour extends BaseFragment {
    public static final String TAG = AddCustomerDetailsFour.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;

    private String mParam1;
    private String mParam2;

    @BindView(R.id.edtNature)
    EditText edtNature;

    @BindView(R.id.edtStartDateBusiness)
    EditText edtStartDateBusiness;

    @BindView(R.id.spnrCustomerType)
    Spinner spnrCustomerType;

    @BindView(R.id.spnrModePayment)
    Spinner spnrModePayment;

    private CustomerModel customerModel;
    private List<LogInPOJO.Datum> logInResList;

    public AddCustomerDetailsFour() {
        // Required empty public constructor
    }

    public static AddCustomerDetailsFour newInstance(CustomerModel customerModel) {
        AddCustomerDetailsFour fragment = new AddCustomerDetailsFour();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, customerModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customerModel = (CustomerModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.add_customer_details_four, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logInResList = new ArrayList<>();
        logInResList = fragmentListner.getSignInResultList();
        if (logInResList.size() > 0) {
            getCustomerType();
            getPaymentMode();
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @OnClick(R.id.btnNext)
    public void onNextClick() {
        setValues();
        viewPagerChangeInterface.onRightMoveClick();
    }

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        setValues();
        viewPagerChangeInterface.onLeftMoveClick();
    }

    private void setValues() {
        customerModel.setNature(edtNature.getText().toString());
        customerModel.setStartDateBusiness(edtStartDateBusiness.getText().toString());
        customerModel.setPaymentMode(spnrModePayment.getSelectedItem().toString());
    }

    @OnClick(R.id.edtStartDateBusiness)
    public void onSelectFromDateClick() {
        showDatePickerDialog(FROM_DATE_TAG);
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

                        setDateWithTagName(tag, year + "-"
                                + new_month + "-" + new_day);

                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void setDateWithTagName(String tag, String date) {
        if (tag.equals(FROM_DATE_TAG)) {
            edtStartDateBusiness.setText(date);
        }
    }

    private void getCustomerType() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getCustomerTypeJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), GET_CUSTOMER_TYPE);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetExpenseTypePOJO> call = restClientAPI.getExpenseType(body);
        call.enqueue(new retrofit2.Callback<GetExpenseTypePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetExpenseTypePOJO> call, @NonNull Response<GetExpenseTypePOJO> response) {
                GetExpenseTypePOJO getExpenseTypePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getExpenseTypePOJO != null) {
                        if (getExpenseTypePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getExpenseTypePOJO.getResponseStatus().equals(TRUE)) {
                                addCustomerTypeSpinner(getExpenseTypePOJO.getData());
                            } else {
                                showMessage(getExpenseTypePOJO.getResponseMessage());
                            }
                        } else {
                            showMessage(getExpenseTypePOJO.getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetExpenseTypePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void getPaymentMode() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getCustomerTypeJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), GET_MODE_OF_PAYMENT);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetExpenseTypePOJO> call = restClientAPI.getExpenseType(body);
        call.enqueue(new retrofit2.Callback<GetExpenseTypePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetExpenseTypePOJO> call, @NonNull Response<GetExpenseTypePOJO> response) {
                GetExpenseTypePOJO getExpenseTypePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getExpenseTypePOJO != null) {
                        if (getExpenseTypePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getExpenseTypePOJO.getResponseStatus().equals(TRUE)) {
                                paymentModeSpinner(getExpenseTypePOJO.getData());
                            } else {
                                showMessage(getExpenseTypePOJO.getResponseMessage());
                            }
                        } else {
                            showMessage(getExpenseTypePOJO.getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetExpenseTypePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    void addCustomerTypeSpinner(final List<GetExpenseTypePOJO.Datum> customerTypeList) {
        try{
            List<String> list = new ArrayList<>();
            if (customerTypeList.size() > 0) {
                for (int i = 0; i < customerTypeList.size(); i++) {
                    list.add(customerTypeList.get(i).getName());
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnrCustomerType.setAdapter(dataAdapter);
            //spnrCustomerType.setSelection(0, false);
            spnrCustomerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    customerModel.setCustomerType(String.valueOf(customerTypeList.get(position).getId()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void paymentModeSpinner(final List<GetExpenseTypePOJO.Datum> paymentModeList) {
        try{
            List<String> list = new ArrayList<>();
            if (paymentModeList.size() > 0) {
                for (int i = 0; i < paymentModeList.size(); i++) {
                    list.add(paymentModeList.get(i).getName());
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnrModePayment.setAdapter(dataAdapter);
            //spnrModePayment.setSelection(0, false);
            spnrModePayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}