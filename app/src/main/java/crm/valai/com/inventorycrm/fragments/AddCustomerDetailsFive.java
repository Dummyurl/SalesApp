package crm.valai.com.inventorycrm.fragments;

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
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.CustomerModel;
import crm.valai.com.inventorycrm.modals.GetCityPOJO;
import crm.valai.com.inventorycrm.modals.GetCountryPOJO;
import crm.valai.com.inventorycrm.modals.GetStatePOJO;
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
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

/**
 * @author by Mohit Arora on 31/3/18.
 */
public class AddCustomerDetailsFive extends BaseFragment {
    public static final String TAG = AddCustomerDetailsFive.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;

    private String mParam1;
    private String mParam2;

    @BindView(R.id.spnrCountry)
    Spinner spnrCountry;

    @BindView(R.id.spnrState)
    Spinner spnrState;

    @BindView(R.id.spnrCity)
    Spinner spnrCity;

    private CustomerModel customerModel;
    private List<LogInPOJO.Datum> logInResList;
    private List<GetCountryPOJO.Datum> countryList;
    private List<GetStatePOJO.Datum> stateList;
    private List<GetCityPOJO.Datum> cityList;
    private String countryId = "0";
    private String stateId = "0";
    private String cityId = "0";

    public AddCustomerDetailsFive() {
        // Required empty public constructor
    }

    public static AddCustomerDetailsFive newInstance(CustomerModel customerModel) {
        AddCustomerDetailsFive fragment = new AddCustomerDetailsFive();
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
        View rootView = inflater.inflate(R.layout.add_customer_details_five, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logInResList = new ArrayList<>();
        logInResList = fragmentListner.getSignInResultList();

        countryList = new ArrayList<>();
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();
        if (logInResList.size() > 0) {
            getCountry();
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
        viewPagerChangeInterface.onRightMoveClick();
    }

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        viewPagerChangeInterface.onLeftMoveClick();
    }

    private void getCountry() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getCountryJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetCountryPOJO> call = restClientAPI.getCountry(body);
        call.enqueue(new retrofit2.Callback<GetCountryPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetCountryPOJO> call, @NonNull Response<GetCountryPOJO> response) {
                GetCountryPOJO getCountryPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getCountryPOJO != null) {
                        if (getCountryPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getCountryPOJO.getResponseStatus().equals(TRUE)) {
                                clearCountryList();
                                countryList.addAll(getCountryPOJO.getData());
                                addCountrySpinner();
                            } else {
                                clearCountryList();
                                addCountrySpinner();
                                clearStateList();
                                addStateSpinner(0);
                                clearCityList();
                                addCitySpinner();
                                //showMessage(getCountryPOJO.getResponseMessage());
                            }
                        } else {
                            clearCountryList();
                            addCountrySpinner();
                            clearStateList();
                            addStateSpinner(0);
                            clearCityList();
                            addCitySpinner();
                            //showMessage(getCountryPOJO.getResponseMessage());
                        }
                    } else {
                        clearCountryList();
                        addCountrySpinner();
                        clearStateList();
                        addStateSpinner(0);
                        clearCityList();
                        addCitySpinner();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCountryPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                clearCountryList();
                addCountrySpinner();
                clearStateList();
                addStateSpinner(0);
                clearCityList();
                addCitySpinner();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    void clearCountryList() {
        if (countryList.size() > 0) {
            countryList.clear();
        }
    }

    void addCountrySpinner() {
        try {
            countryId = "0";
            stateId = "0";
            cityId = "0";

            customerModel.setCurrent_country(countryId);
            customerModel.setCurrent_state(stateId);
            customerModel.setCurrent_city(cityId);

            List<String> list = new ArrayList<>();
            if (countryList != null && countryList.size() > 0) {
                for (int i = 0; i < countryList.size(); i++) {
                    list.add(countryList.get(i).getCountryName());
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnrCountry.setAdapter(dataAdapter);
            //spnrCustomerType.setSelection(0, false);
            spnrCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    countryId = String.valueOf(countryList.get(position).getCountryId());
                    customerModel.setCurrent_country(countryId);
                    getState(countryList.get(position).getCountryId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getState(final Integer countryId) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getStateJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), countryId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetStatePOJO> call = restClientAPI.getState(body);
        call.enqueue(new retrofit2.Callback<GetStatePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetStatePOJO> call, @NonNull Response<GetStatePOJO> response) {
                GetStatePOJO getStatePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getStatePOJO != null) {
                        if (getStatePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getStatePOJO.getResponseStatus().equals(TRUE)) {
                                clearStateList();
                                stateList.addAll(getStatePOJO.getData());
                                addStateSpinner(countryId);
                            } else {
                                clearStateList();
                                addStateSpinner(countryId);
                                clearCityList();
                                addCitySpinner();
                                //showMessage(getStatePOJO.getResponseMessage());
                            }
                        } else {
                            clearStateList();
                            addStateSpinner(countryId);
                            clearCityList();
                            addCitySpinner();
                            //showMessage(getStatePOJO.getResponseMessage());
                        }
                    } else {
                        clearStateList();
                        addStateSpinner(countryId);
                        clearCityList();
                        addCitySpinner();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetStatePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                clearStateList();
                addStateSpinner(countryId);
                clearCityList();
                addCitySpinner();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    void clearStateList() {
        if (stateList.size() > 0) {
            stateList.clear();
        }
    }

    void addStateSpinner(final Integer countryId) {
        try {
            stateId = "0";
            cityId = "0";

            customerModel.setCurrent_state(stateId);
            customerModel.setCurrent_city(cityId);

            List<String> list = new ArrayList<>();
            if (stateList.size() > 0) {
                for (int i = 0; i < stateList.size(); i++) {
                    list.add(stateList.get(i).getStateName());
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnrState.setAdapter(dataAdapter);
            //spnrModePayment.setSelection(0, false);
            spnrState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    stateId = String.valueOf(stateList.get(position).getStateId());
                    customerModel.setCurrent_state(stateId);
                    getCity(countryId, stateList.get(position).getStateId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCity(final Integer countryId, final Integer stateId) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getCityJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), countryId, stateId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetCityPOJO> call = restClientAPI.getCity(body);
        call.enqueue(new retrofit2.Callback<GetCityPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetCityPOJO> call, @NonNull Response<GetCityPOJO> response) {
                GetCityPOJO getCityPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getCityPOJO != null) {
                        if (getCityPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getCityPOJO.getResponseStatus().equals(TRUE)) {
                                clearCityList();
                                cityList.addAll(getCityPOJO.getData());
                                addCitySpinner();
                            } else {
                                clearCityList();
                                addCitySpinner();
                                //showMessage(getCityPOJO.getResponseMessage());
                            }
                        } else {
                            clearCityList();
                            addCitySpinner();
                            //showMessage(getCityPOJO.getResponseMessage());
                        }
                    } else {
                        clearCityList();
                        addCitySpinner();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCityPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                clearCityList();
                addCitySpinner();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    void clearCityList() {
        if (cityList.size() > 0) {
            cityList.clear();
        }
    }

    void addCitySpinner() {
        try {
            cityId = "0";
            customerModel.setCurrent_city(cityId);

            List<String> list = new ArrayList<>();
            if (cityList.size() > 0) {
                for (int i = 0; i < cityList.size(); i++) {
                    list.add(cityList.get(i).getCityName());
                }
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnrCity.setAdapter(dataAdapter);
            //spnrModePayment.setSelection(0, false);
            spnrCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cityId = String.valueOf(cityList.get(position).getCityId());
                    customerModel.setCurrent_city(cityId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}