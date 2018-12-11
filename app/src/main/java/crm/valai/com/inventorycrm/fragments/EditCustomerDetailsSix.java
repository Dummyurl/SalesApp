package crm.valai.com.inventorycrm.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.CustomerModel;
import crm.valai.com.inventorycrm.modals.GetCustomerDetailsPOJO;
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

import static crm.valai.com.inventorycrm.fragments.EditCustomerDialogue.viewPagerChangeInterface;
import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.INSERT_CUSTOMER;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

/**
 * @author by Mohit Arora on 31/3/18.
 */
public class EditCustomerDetailsSix extends BaseFragment {
    public static final String TAG = EditCustomerDetailsSix.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;

    private String mParam1;
    private String mParam2;

    @BindView(R.id.edtAnnualTurnOver)
    EditText edtAnnualTurnOver;

    @BindView(R.id.edtReferredBy)
    EditText edtReferredBy;

    @BindView(R.id.spnrSalesmen)
    Spinner spnrSalesmen;

    @BindView(R.id.spnrStatus)
    Spinner spnrStatus;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.llSave)
    LinearLayout llSave;

    private CustomerModel customerModel;
    private List<LogInPOJO.Datum> logInResList;
    private GetCustomerDetailsPOJO.Datum dataCustomer;
    private Integer customerId;

    public EditCustomerDetailsSix() {
        // Required empty public constructor
    }

    public static EditCustomerDetailsSix newInstance(CustomerModel customerModel, GetCustomerDetailsPOJO.Datum dataCustomer, Integer customerId) {
        EditCustomerDetailsSix fragment = new EditCustomerDetailsSix();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, customerModel);
        args.putSerializable(ARG_PARAM2, dataCustomer);
        args.putInt(ARG_PARAM3, customerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customerModel = (CustomerModel) getArguments().getSerializable(ARG_PARAM1);
            dataCustomer = (GetCustomerDetailsPOJO.Datum) getArguments().getSerializable(ARG_PARAM2);
            customerId = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.add_customer_details_six, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logInResList = new ArrayList<>();
        logInResList = fragmentListner.getSignInResultList();
        //addSalesMenSpinner();
        //statusSpinner();
        edtAnnualTurnOver.setText(String.valueOf(dataCustomer.getAnnualTurnover()));
        edtReferredBy.setText(dataCustomer.getRefferedBy());
        btnSubmit.setVisibility(View.GONE);
        llSave.setWeightSum(1);
        btnSave.setText(getString(R.string.update_text));
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

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        setValues();
        viewPagerChangeInterface.onLeftMoveClick();
    }

    @OnClick(R.id.btnCancel)
    public void btnCancelClick() {
        viewPagerChangeInterface.onDialogDismiss();
    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmitClick() {
        setValues();
        checkValidations(1);
    }

    @OnClick(R.id.btnSave)
    public void btnSaveClick() {
        setValues();
        checkValidations(0);
    }

    private void checkValidations(Integer statusVal) {
        if (customerModel.getCustomerName() == null || customerModel.getCustomerName().equals("")) {
            showSnackBar(btnSubmit, "Please Enter Customer Name");
            return;
        }

//        if (customerModel.getCustomerPhoneNumber() == null || customerModel.getCustomerPhoneNumber().equals("")) {
//            showSnackBar(btnSubmit, "Please Enter Customer Phone Number");
//            return;
//        }
//
//        if (customerModel.getCustomerEmail() == null || customerModel.getCustomerEmail().equals("")) {
//            showSnackBar(btnSubmit, "Please Enter Customer Email");
//            return;
//        }
//
//        if (customerModel.getCustomerEmail() == null || !CommonUtils.isEmailValid(customerModel.getCustomerEmail())) {
//            showSnackBar(btnSubmit, "Please Enter Valid Customer Email");
//            return;
//        }

        if (customerModel.getContactPersonName() == null || customerModel.getContactPersonName().equals("")) {
            showSnackBar(btnSubmit, "Please Enter Contact Person Name");
            return;
        }

        if (customerModel.getContactPersonNameDesignation() == null || customerModel.getContactPersonNameDesignation().equals("")) {
            showSnackBar(btnSubmit, "Please Enter Contact Person Designation");
            return;
        }

        if (customerModel.getContactPersonNumber() == null || customerModel.getContactPersonNumber().equals("")) {
            showSnackBar(btnSubmit, "Please Enter Contact Person Phone Number");
            return;
        }

        if (customerModel.getContactPersonEmail() == null || customerModel.getContactPersonEmail().equals("")) {
            showSnackBar(btnSubmit, "Please Enter Contact Person Email");
            return;
        }

        if (customerModel.getContactPersonEmail() == null || !CommonUtils.isEmailValid(customerModel.getContactPersonEmail())) {
            showSnackBar(btnSubmit, "Please Enter Valid Contact Person Email");
            return;
        }

        if (customerModel.getCreditLimit() == null || customerModel.getCreditLimit().equals("")) {
            showSnackBar(btnSubmit, "Please Enter Credit Limit");
            return;
        }

        if (customerModel.getCreditDays() == null || customerModel.getCreditDays().equals("")) {
            showSnackBar(btnSubmit, "Please Enter Credit Days");
            return;
        }

        if (customerModel.getPanNumber() == null || customerModel.getPanNumber().equals("")) {
            showSnackBar(btnSubmit, "Please Enter PAN Number");
            return;
        }

//        if (customerModel.getTinNumber() == null || customerModel.getTinNumber().equals("")) {
//            showSnackBar(btnSubmit, "Please Enter TIN Number");
//            return;
//        }

        if (customerModel.getGstNumber() == null || customerModel.getGstNumber().equals("")) {
            showSnackBar(btnSubmit, "Please Enter GST Number");
            return;
        }

        if (Integer.parseInt(customerModel.getCustomerType()) == 2) {
            if (customerModel.getNature() == null || customerModel.getNature().equals("")) {
                showSnackBar(btnSubmit, "Please Enter Nature Of Business");
                return;
            }

            if (customerModel.getStartDateBusiness() == null || customerModel.getStartDateBusiness().equals("")) {
                showSnackBar(btnSubmit, "Please Enter Start Date Of Business");
                return;
            }
        }

        if (customerModel.getAnnualTurnOver() == null || customerModel.getAnnualTurnOver().equals("")) {
            showSnackBar(btnSubmit, "Please Enter Annual Turnover");
            return;
        }

        if (customerModel.getReferredBy() == null || customerModel.getReferredBy().equals("")) {
            showSnackBar(btnSubmit, "Please Enter ReferredBy");
            return;
        }

        addCustomer(statusVal);
    }

    private void setValues() {
        customerModel.setAnnualTurnOver(edtAnnualTurnOver.getText().toString());
        customerModel.setReferredBy(edtReferredBy.getText().toString());
        //customerModel.setSalesmen(spnrSalesmen.getSelectedItem().toString());
        //customerModel.setStatusVal(spnrStatus.getSelectedItem().toString());
    }

    private void addCustomer(final Integer status) {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.insertUpdateCustomerDetailsJson(logInResList.get(0).getCompId(), INSERT_CUSTOMER, logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), customerId, logInResList.get(0).getStaffId(), customerModel.getReferredBy(), customerModel.getContactPersonName(),
                Integer.parseInt(customerModel.getCustomerType()), customerModel.getContactPersonNameDesignation(), customerModel.getCustomerName(),
                customerModel.getCustomerPhoneNumber(), customerModel.getContactPersonNumber(), customerModel.getCustomerEmail(), customerModel.getContactPersonEmail(),
                customerModel.getNature(), customerModel.getAnnualTurnOver(), customerModel.getCreditLimit(), Integer.parseInt(customerModel.getCreditDays()),
                customerModel.getPaymentMode(), "", customerModel.getCurrent_state(), customerModel.getCurrent_city(), customerModel.getCurrent_country(), "",
                Integer.parseInt(customerModel.getCurrent_city()), Integer.parseInt(customerModel.getCurrent_state()), Integer.parseInt(customerModel.getCurrent_country()), "", "",
                customerModel.getPanNumber(), customerModel.getTinNumber(), customerModel.getGstNumber(), status, "", customerModel.getStartDateBusiness());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateCustomer(body);
        call.enqueue(new retrofit2.Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                InsertUpdateItemPOJO getItemPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getItemPOJO != null) {
                        if (getItemPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getItemPOJO.getResponseStatus().equals(TRUE)) {
                                viewPagerChangeInterface.onDialogDismiss();
                                showMessage(getItemPOJO.getResponseMessage());
                                viewPagerChangeInterface.getCustomersCall();
                            } else {
                                showSnackBar(btnSubmit, getItemPOJO.getResponseMessage());
                            }
                        } else {
                            showSnackBar(btnSubmit, getItemPOJO.getResponseMessage());
                        }
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showSnackBar(btnSubmit, getString(R.string.internet_not_available));
            }
        });
    }

    private void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        snackbar.show();
    }
}