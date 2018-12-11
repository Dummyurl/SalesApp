package crm.valai.com.inventorycrm.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.GetCustomerTargetPOJO;
import crm.valai.com.inventorycrm.modals.InsertUpdateItemPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.fragments.AdditionalDetailsFragment.viewPagerChangeInterface;
import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

public class TargetFragment extends BaseFragment {
    public static final String TAG = TargetFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentListner fragmentListner;
    private OnFragmentInteractionListener mListener;
    private Integer customerId;
    private List<LogInPOJO.Datum> logInResList;
    private List<GetCustomerTargetPOJO.Datum> listGetCustomerTarget;
    private int pos = 0;
    private int totalSize = 1;
    private Double targetQuantity;

    @BindView(R.id.edtStructuralSteal)
    EditText edtStructuralSteal;

    @BindView(R.id.edtSteelPipe)
    EditText edtSteelPipe;

    @BindView(R.id.edtCoilSheet)
    EditText edtCoilSheet;

    @BindView(R.id.edtDurastrongWiremesh)
    EditText edtDurastrongWiremesh;

    @BindView(R.id.edtDurastrogElectrodes)
    EditText edtDurastrogElectrodes;

    @BindView(R.id.edtGCSHEET)
    EditText edtGCSHEET;

    @BindView(R.id.edtSteel)
    EditText edtSteel;

    @BindView(R.id.tILStructuralSteal)
    TextInputLayout tILStructuralSteal;

    @BindView(R.id.tILSteelPipe)
    TextInputLayout tILSteelPipe;

    @BindView(R.id.tILCoilSheet)
    TextInputLayout tILCoilSheet;

    @BindView(R.id.tILDurastrongWiremesh)
    TextInputLayout tILDurastrongWiremesh;

    @BindView(R.id.tILDurastrogElectrodes)
    TextInputLayout tILDurastrogElectrodes;

    @BindView(R.id.tILGCSHEET)
    TextInputLayout tILGCSHEET;

    @BindView(R.id.tILSteel)
    TextInputLayout tILSteel;

    public TargetFragment() {
        // Required empty public constructor
    }

    public static TargetFragment newInstance(String param1, String param2) {
        TargetFragment fragment = new TargetFragment();
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
        View rootView = inflater.inflate(R.layout.target_customer_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listGetCustomerTarget = new ArrayList<>();
        customerId = fragmentListner.getAppPreferenceHelper().getCustomerId();
        logInResList = new ArrayList<>();
        logInResList = fragmentListner.getSignInResultList();
        if (logInResList != null && logInResList.size() > 0) {
            getCustomerTarget();
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @OnClick(R.id.btnNext)
    public void onNextClick() {
        viewPagerChangeInterface.onRightMoveClick();
    }

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        viewPagerChangeInterface.onLeftMoveClick();
    }

    @OnClick(R.id.btnSave)
    public void onSaveClick() {
        hideKeyboard();
        if (edtStructuralSteal.getText().length() == 0 && edtSteelPipe.getText().length() == 0 &&
                edtCoilSheet.getText().length() == 0 && edtDurastrongWiremesh.getText().length() == 0 &&
                edtDurastrogElectrodes.getText().length() == 0 &&
                edtGCSHEET.getText().length() == 0 && edtSteel.getText().length() == 0) {
            showMessage(getString(R.string.enter_details));
            return;
        }

        showLoading();
        insertCustomerTarget();
    }

    private void getCustomerTarget() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getCustomerTargetJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(),
                logInResList.get(0).getCompId(), customerId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetCustomerTargetPOJO> call = restClientAPI.getCustomerTarget(body);
        call.enqueue(new retrofit2.Callback<GetCustomerTargetPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetCustomerTargetPOJO> call, @NonNull Response<GetCustomerTargetPOJO> response) {
                GetCustomerTargetPOJO getSalesmanCustomerPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getSalesmanCustomerPOJO != null) {
                        if (getSalesmanCustomerPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getSalesmanCustomerPOJO.getResponseStatus().equals(TRUE)) {
                                listGetCustomerTarget.addAll(getSalesmanCustomerPOJO.getData());
                                totalSize = listGetCustomerTarget.size();
                                setHint(getSalesmanCustomerPOJO.getData());
                                if (getSalesmanCustomerPOJO.getData().size() == 1) {
                                    edtStructuralSteal.setVisibility(View.VISIBLE);
                                    edtSteelPipe.setVisibility(View.GONE);
                                    edtCoilSheet.setVisibility(View.GONE);
                                    edtDurastrongWiremesh.setVisibility(View.GONE);
                                    edtDurastrogElectrodes.setVisibility(View.GONE);
                                    edtGCSHEET.setVisibility(View.GONE);
                                    edtSteel.setVisibility(View.GONE);
                                    edtStructuralSteal.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(0).getTargetQuantity()));
                                } else if (getSalesmanCustomerPOJO.getData().size() == 2) {
                                    edtStructuralSteal.setVisibility(View.VISIBLE);
                                    edtSteelPipe.setVisibility(View.VISIBLE);
                                    edtCoilSheet.setVisibility(View.GONE);
                                    edtDurastrongWiremesh.setVisibility(View.GONE);
                                    edtDurastrogElectrodes.setVisibility(View.GONE);
                                    edtGCSHEET.setVisibility(View.GONE);
                                    edtSteel.setVisibility(View.GONE);
                                    edtStructuralSteal.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(0).getTargetQuantity()));
                                    edtSteelPipe.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(1).getTargetQuantity()));
                                } else if (getSalesmanCustomerPOJO.getData().size() == 3) {
                                    edtStructuralSteal.setVisibility(View.VISIBLE);
                                    edtSteelPipe.setVisibility(View.VISIBLE);
                                    edtCoilSheet.setVisibility(View.VISIBLE);
                                    edtDurastrongWiremesh.setVisibility(View.GONE);
                                    edtDurastrogElectrodes.setVisibility(View.GONE);
                                    edtGCSHEET.setVisibility(View.GONE);
                                    edtSteel.setVisibility(View.GONE);
                                    edtStructuralSteal.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(0).getTargetQuantity()));
                                    edtSteelPipe.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(1).getTargetQuantity()));
                                    edtCoilSheet.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(2).getTargetQuantity()));
                                } else if (getSalesmanCustomerPOJO.getData().size() == 4) {
                                    edtStructuralSteal.setVisibility(View.VISIBLE);
                                    edtSteelPipe.setVisibility(View.VISIBLE);
                                    edtCoilSheet.setVisibility(View.VISIBLE);
                                    edtDurastrongWiremesh.setVisibility(View.VISIBLE);
                                    edtDurastrogElectrodes.setVisibility(View.GONE);
                                    edtGCSHEET.setVisibility(View.GONE);
                                    edtSteel.setVisibility(View.GONE);
                                    edtStructuralSteal.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(0).getTargetQuantity()));
                                    edtSteelPipe.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(1).getTargetQuantity()));
                                    edtCoilSheet.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(2).getTargetQuantity()));
                                    edtDurastrongWiremesh.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(3).getTargetQuantity()));
                                } else if (getSalesmanCustomerPOJO.getData().size() == 5) {
                                    edtStructuralSteal.setVisibility(View.VISIBLE);
                                    edtSteelPipe.setVisibility(View.VISIBLE);
                                    edtCoilSheet.setVisibility(View.VISIBLE);
                                    edtDurastrongWiremesh.setVisibility(View.VISIBLE);
                                    edtDurastrogElectrodes.setVisibility(View.VISIBLE);
                                    edtGCSHEET.setVisibility(View.GONE);
                                    edtSteel.setVisibility(View.GONE);
                                    edtStructuralSteal.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(0).getTargetQuantity()));
                                    edtSteelPipe.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(1).getTargetQuantity()));
                                    edtCoilSheet.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(2).getTargetQuantity()));
                                    edtDurastrongWiremesh.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(3).getTargetQuantity()));
                                    edtDurastrogElectrodes.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(4).getTargetQuantity()));
                                } else if (getSalesmanCustomerPOJO.getData().size() == 6) {
                                    edtStructuralSteal.setVisibility(View.VISIBLE);
                                    edtSteelPipe.setVisibility(View.VISIBLE);
                                    edtCoilSheet.setVisibility(View.VISIBLE);
                                    edtDurastrongWiremesh.setVisibility(View.VISIBLE);
                                    edtDurastrogElectrodes.setVisibility(View.VISIBLE);
                                    edtGCSHEET.setVisibility(View.VISIBLE);
                                    edtSteel.setVisibility(View.GONE);
                                    edtStructuralSteal.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(0).getTargetQuantity()));
                                    edtSteelPipe.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(1).getTargetQuantity()));
                                    edtCoilSheet.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(2).getTargetQuantity()));
                                    edtDurastrongWiremesh.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(3).getTargetQuantity()));
                                    edtDurastrogElectrodes.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(4).getTargetQuantity()));
                                    edtGCSHEET.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(5).getTargetQuantity()));
                                } else if (getSalesmanCustomerPOJO.getData().size() == 7) {
                                    edtStructuralSteal.setVisibility(View.VISIBLE);
                                    edtSteelPipe.setVisibility(View.VISIBLE);
                                    edtCoilSheet.setVisibility(View.VISIBLE);
                                    edtDurastrongWiremesh.setVisibility(View.VISIBLE);
                                    edtDurastrogElectrodes.setVisibility(View.VISIBLE);
                                    edtGCSHEET.setVisibility(View.VISIBLE);
                                    edtSteel.setVisibility(View.VISIBLE);
                                    edtStructuralSteal.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(0).getTargetQuantity()));
                                    edtSteelPipe.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(1).getTargetQuantity()));
                                    edtCoilSheet.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(2).getTargetQuantity()));
                                    edtDurastrongWiremesh.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(3).getTargetQuantity()));
                                    edtDurastrogElectrodes.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(4).getTargetQuantity()));
                                    edtGCSHEET.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(5).getTargetQuantity()));
                                    edtSteel.setText(String.valueOf(getSalesmanCustomerPOJO.getData().get(6).getTargetQuantity()));
                                }
                            } else {
                                showMessage(getSalesmanCustomerPOJO.getResponseMessage());
                                edtStructuralSteal.setText(null);
                                edtSteelPipe.setText(null);
                                edtCoilSheet.setText(null);
                                edtDurastrongWiremesh.setText(null);
                                edtDurastrogElectrodes.setText(null);
                                edtGCSHEET.setText(null);
                                edtSteel.setText(null);
                            }
                        } else {
                            showMessage(getSalesmanCustomerPOJO.getResponseMessage());
                            edtStructuralSteal.setText(null);
                            edtSteelPipe.setText(null);
                            edtCoilSheet.setText(null);
                            edtDurastrongWiremesh.setText(null);
                            edtDurastrogElectrodes.setText(null);
                            edtGCSHEET.setText(null);
                            edtSteel.setText(null);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCustomerTargetPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                edtStructuralSteal.setText(null);
                edtSteelPipe.setText(null);
                edtCoilSheet.setText(null);
                edtDurastrongWiremesh.setText(null);
                edtDurastrogElectrodes.setText(null);
                edtGCSHEET.setText(null);
                edtSteel.setText(null);
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setHint(List<GetCustomerTargetPOJO.Datum> list) {
        if (list != null && list.size() > 0) {
            if (list.size() == 1) {
                tILStructuralSteal.setVisibility(View.VISIBLE);
                tILSteelPipe.setVisibility(View.GONE);
                tILCoilSheet.setVisibility(View.GONE);
                tILDurastrongWiremesh.setVisibility(View.GONE);
                tILDurastrogElectrodes.setVisibility(View.GONE);
                tILGCSHEET.setVisibility(View.GONE);
                tILSteel.setVisibility(View.GONE);
                tILStructuralSteal.setHint(list.get(0).getClusterName());
            } else if (list.size() == 2) {
                tILStructuralSteal.setVisibility(View.VISIBLE);
                tILSteelPipe.setVisibility(View.VISIBLE);
                tILCoilSheet.setVisibility(View.GONE);
                tILDurastrongWiremesh.setVisibility(View.GONE);
                tILDurastrogElectrodes.setVisibility(View.GONE);
                tILGCSHEET.setVisibility(View.GONE);
                tILSteel.setVisibility(View.GONE);
                tILStructuralSteal.setHint(list.get(0).getClusterName());
                tILSteelPipe.setHint(list.get(1).getClusterName());
            } else if (list.size() == 3) {
                tILStructuralSteal.setVisibility(View.VISIBLE);
                tILSteelPipe.setVisibility(View.VISIBLE);
                tILCoilSheet.setVisibility(View.VISIBLE);
                tILDurastrongWiremesh.setVisibility(View.GONE);
                tILDurastrogElectrodes.setVisibility(View.GONE);
                tILGCSHEET.setVisibility(View.GONE);
                tILSteel.setVisibility(View.GONE);
                tILStructuralSteal.setHint(list.get(0).getClusterName());
                tILSteelPipe.setHint(list.get(1).getClusterName());
                tILCoilSheet.setHint(list.get(2).getClusterName());
            } else if (list.size() == 4) {
                tILStructuralSteal.setVisibility(View.VISIBLE);
                tILSteelPipe.setVisibility(View.VISIBLE);
                tILCoilSheet.setVisibility(View.VISIBLE);
                tILDurastrongWiremesh.setVisibility(View.VISIBLE);
                tILDurastrogElectrodes.setVisibility(View.GONE);
                tILGCSHEET.setVisibility(View.GONE);
                tILSteel.setVisibility(View.GONE);
                tILStructuralSteal.setHint(list.get(0).getClusterName());
                tILSteelPipe.setHint(list.get(1).getClusterName());
                tILCoilSheet.setHint(list.get(2).getClusterName());
                tILDurastrongWiremesh.setHint(list.get(3).getClusterName());
            } else if (list.size() == 5) {
                tILStructuralSteal.setVisibility(View.VISIBLE);
                tILSteelPipe.setVisibility(View.VISIBLE);
                tILCoilSheet.setVisibility(View.VISIBLE);
                tILDurastrongWiremesh.setVisibility(View.VISIBLE);
                tILDurastrogElectrodes.setVisibility(View.VISIBLE);
                tILGCSHEET.setVisibility(View.GONE);
                tILSteel.setVisibility(View.GONE);
                tILStructuralSteal.setHint(list.get(0).getClusterName());
                tILSteelPipe.setHint(list.get(1).getClusterName());
                tILCoilSheet.setHint(list.get(2).getClusterName());
                tILDurastrongWiremesh.setHint(list.get(3).getClusterName());
                tILDurastrogElectrodes.setHint(list.get(4).getClusterName());
            } else if (list.size() == 6) {
                tILStructuralSteal.setVisibility(View.VISIBLE);
                tILSteelPipe.setVisibility(View.VISIBLE);
                tILCoilSheet.setVisibility(View.VISIBLE);
                tILDurastrongWiremesh.setVisibility(View.VISIBLE);
                tILDurastrogElectrodes.setVisibility(View.VISIBLE);
                tILGCSHEET.setVisibility(View.VISIBLE);
                tILSteel.setVisibility(View.GONE);
                tILStructuralSteal.setHint(list.get(0).getClusterName());
                tILSteelPipe.setHint(list.get(1).getClusterName());
                tILCoilSheet.setHint(list.get(2).getClusterName());
                tILDurastrongWiremesh.setHint(list.get(3).getClusterName());
                tILDurastrogElectrodes.setHint(list.get(4).getClusterName());
                tILGCSHEET.setHint(list.get(5).getClusterName());
            } else if (list.size() == 7) {
                tILStructuralSteal.setVisibility(View.VISIBLE);
                tILSteelPipe.setVisibility(View.VISIBLE);
                tILCoilSheet.setVisibility(View.VISIBLE);
                tILDurastrongWiremesh.setVisibility(View.VISIBLE);
                tILDurastrogElectrodes.setVisibility(View.VISIBLE);
                tILGCSHEET.setVisibility(View.VISIBLE);
                tILSteel.setVisibility(View.VISIBLE);
                tILStructuralSteal.setHint(list.get(0).getClusterName());
                tILSteelPipe.setHint(list.get(1).getClusterName());
                tILCoilSheet.setHint(list.get(2).getClusterName());
                tILDurastrongWiremesh.setHint(list.get(3).getClusterName());
                tILDurastrogElectrodes.setHint(list.get(4).getClusterName());
                tILGCSHEET.setHint(list.get(5).getClusterName());
                tILSteel.setHint(list.get(6).getClusterName());
            }
        }
    }

    private void insertCustomerTarget() {
        Integer clusterId;
        if (listGetCustomerTarget != null && listGetCustomerTarget.size() > 0) {
            clusterId = listGetCustomerTarget.get(pos).getClusterId();
        } else {
            totalSize = 7;
            clusterId = 0;
        }

        if (pos == 0) {
            if (!edtStructuralSteal.getText().toString().equals("")) {
                targetQuantity = Double.parseDouble(edtStructuralSteal.getText().toString());
            } else {
                targetQuantity = 0.0;
            }
        } else if (pos == 1) {
            if (!edtSteelPipe.getText().toString().equals("")) {
                targetQuantity = Double.parseDouble(edtSteelPipe.getText().toString());
            } else {
                targetQuantity = 0.0;
            }
        } else if (pos == 2) {
            if (!edtCoilSheet.getText().toString().equals("")) {
                targetQuantity = Double.parseDouble(edtCoilSheet.getText().toString());
            } else {
                targetQuantity = 0.0;
            }
        } else if (pos == 3) {
            if (!edtDurastrongWiremesh.getText().toString().equals("")) {
                targetQuantity = Double.parseDouble(edtDurastrongWiremesh.getText().toString());
            } else {
                targetQuantity = 0.0;
            }
        } else if (pos == 4) {
            if (!edtDurastrogElectrodes.getText().toString().equals("")) {
                targetQuantity = Double.parseDouble(edtDurastrogElectrodes.getText().toString());
            } else {
                targetQuantity = 0.0;
            }
        } else if (pos == 5) {
            if (!edtGCSHEET.getText().toString().equals("")) {
                targetQuantity = Double.parseDouble(edtGCSHEET.getText().toString());
            } else {
                targetQuantity = 0.0;
            }
        } else if (pos == 6) {
            if (!edtSteel.getText().toString().equals("")) {
                targetQuantity = Double.parseDouble(edtSteel.getText().toString());
            } else {
                targetQuantity = 0.0;
            }
        }

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.insertUpdateCustomerTargetJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(),
                logInResList.get(0).getCompId(), customerId, clusterId, targetQuantity, fragmentListner.getAppPreferenceHelper().getUserName(),
                fragmentListner.getAppPreferenceHelper().getIpAddress(), fragmentListner.getAppPreferenceHelper().getDeviceId());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateCustomerTarget(body);
        call.enqueue(new retrofit2.Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                InsertUpdateItemPOJO getSalesmanCustomerPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getSalesmanCustomerPOJO != null) {
                        if (getSalesmanCustomerPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getSalesmanCustomerPOJO.getResponseStatus().equals(TRUE)) {
                                if (pos == totalSize - 1) {
                                    hideLoading();
                                    pos = 0;
                                    totalSize = 1;
                                    showMessage(getSalesmanCustomerPOJO.getResponseMessage());
                                } else {
                                    pos = pos + 1;
                                    insertCustomerTarget();
                                }
                            } else {
                                hideLoading();
                                showMessage(getSalesmanCustomerPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showMessage(getSalesmanCustomerPOJO.getResponseMessage());
                        }
                    }
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
}