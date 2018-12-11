package crm.valai.com.inventorycrm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.GetSalesDailySummary;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.utils.CommonUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.fragments.MySalesFragment.viewPagerChangeInterface;
import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

/**
 * @author by Mohit Arora on 31/3/18.
 */
public class SalesTrackerTabOneFragment extends BaseFragment {
    public static final String TAG = SalesTrackerTabOneFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentListner fragmentListner;
    private List<LogInPOJO.Datum> logInResList;
    private Boolean isFirstTimeRun = true;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.tvSalesTargetMonthly)
    TextView tvSalesTargetMonthly;

    @BindView(R.id.tvTillDateSales)
    TextView tvTillDateSales;

    @BindView(R.id.tvTodaySales)
    TextView tvTodaySales;

    @BindView(R.id.tvPendingOrders)
    TextView tvPendingOrders;

    @BindView(R.id.tvCollectionTillDate)
    TextView tvCollectionTillDate;

    @BindView(R.id.tvTodayCollection)
    TextView tvTodayCollection;

    public SalesTrackerTabOneFragment() {
        // Required empty public constructor
    }

    public static SalesTrackerTabOneFragment newInstance(String param1, String param2) {
        SalesTrackerTabOneFragment fragment = new SalesTrackerTabOneFragment();
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
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (logInResList != null && logInResList.size() > 0) {
                        if (!isFirstTimeRun) {
                            showLoading();
                        }
                        isFirstTimeRun = false;
                        getStaffDailySummary();
                    }
                }
            }, 500);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sales_tracker_tab_one, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logInResList = fragmentListner.getSignInResultList();
        tvDate.setText(CommonUtils.getCurrentDateNewOne());
        showLoading();
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

    @OnClick(R.id.btnNext)
    public void onNextClick() {
        viewPagerChangeInterface.onRightMoveClick();
    }

    private void getStaffDailySummary() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getStaffDailySummaryJson(logInResList.get(0).getLoginId(), logInResList.get(0).getToken(),
                logInResList.get(0).getCompId(), logInResList.get(0).getStaffId(), CommonUtils.getCurrentDateForGetOrder());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetSalesDailySummary> call = restClientAPI.getStaffDailyTrackerSummary(body);
        call.enqueue(new retrofit2.Callback<GetSalesDailySummary>() {
            @Override
            public void onResponse(@NonNull Call<GetSalesDailySummary> call, @NonNull Response<GetSalesDailySummary> response) {
                GetSalesDailySummary getSalesDailySummary = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getSalesDailySummary != null) {
                        if (getSalesDailySummary.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getSalesDailySummary.getResponseStatus().equals(TRUE)) {
                                tvSalesTargetMonthly.setText(String.valueOf(getSalesDailySummary.getData().get(0).getSalesTarget()));
                                tvTillDateSales.setText(String.valueOf(getSalesDailySummary.getData().get(0).getTillDateSales()));
                                tvTodaySales.setText(String.valueOf(getSalesDailySummary.getData().get(0).getTodaysSales()));
                                tvPendingOrders.setText(String.valueOf(getSalesDailySummary.getData().get(0).getPendingOrders()));
                                tvCollectionTillDate.setText(String.valueOf(getSalesDailySummary.getData().get(0).getCollectionTillDate()));
                                tvTodayCollection.setText(String.valueOf(getSalesDailySummary.getData().get(0).getTodaysCollection()));
                            } else {
                                showMessage(getSalesDailySummary.getResponseMessage());
                            }
                        }
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetSalesDailySummary> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}