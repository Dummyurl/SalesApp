package crm.valai.com.inventorycrm.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.adapters.LeaveStatusListAdapter;
import crm.valai.com.inventorycrm.interfaces.SetListSize;
import crm.valai.com.inventorycrm.modals.GetLeaveStatusPOJO;
import crm.valai.com.inventorycrm.modals.InsertUpdateItemPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.modals.MyOrderResultPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;
import crm.valai.com.inventorycrm.utils.AppConstants;
import crm.valai.com.inventorycrm.utils.CommonUtils;
import crm.valai.com.inventorycrm.utils.RecyclerItemTouchHelperLeaves;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.GETLEAVESANPENDIS;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

public class LeaveStatusActivity extends BaseActivity implements SetListSize, RecyclerItemTouchHelperLeaves.RecyclerItemTouchHelperListener {
    public static final String TAG = LeaveStatusActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar_selected_customers)
    TextView toolbar_selected_customers;

    @BindView(R.id.floatingTextView)
    TextView floatingTextView;

    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.floating_action_button)
    FloatingActionButton mFloatingActionButton;

    @BindView(R.id.tvNoResult)
    TextView tvNoResult;

    @BindView(R.id.tvTextOne)
    TextView tvTextOne;

    @BindView(R.id.tvTextThree)
    TextView tvTextThree;

    @BindView(R.id.tvTextFour)
    TextView tvTextFour;

    @BindView(R.id.btnSubmitForApproval)
    Button btnSubmitForApproval;

    private LeaveStatusListAdapter adapter;
    private SetListSize setListSize;
    private List<LogInPOJO.Datum> logInResList;
    private List<GetLeaveStatusPOJO.Datum> listLeaveStatus;
    private Integer yearId;

    @SuppressLint({"SetTextI18n", "UseSparseArrays"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_status_details);
        ButterKnife.bind(this);
        setListSize = this;
        setSupportActionBar(toolbar);
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        toolbar_selected_customers.setText(appPreferencesHelper.getBranch());
        toolbar_title.setText(getString(R.string.leave_status));
        Gson gson = new Gson();
        Type type = new TypeToken<List<LogInPOJO.Datum>>() {
        }.getType();
        logInResList = new ArrayList<>();
        logInResList = gson.fromJson(appPreferencesHelper.getLogInResponse(), type);

        listLeaveStatus = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //recycleView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperLeaves(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycleView);

        tvTextOne.setText(getString(R.string.leave_type));
        tvTextThree.setText(getString(R.string.leave_count));
        tvTextFour.setText(getString(R.string.remarks_text));
        yearId = Integer.parseInt(CommonUtils.getCurrentYear());
        setAdapter();
        mFloatingActionButton.setVisibility(View.GONE);
        btnSubmitForApproval.setVisibility(View.GONE);
        if (logInResList != null && logInResList.size() > 0) {
            getLeaveStatus();
        }
    }

    private void setAdapter() {
        if (listLeaveStatus != null && listLeaveStatus.size() > 0) {
            tvNoResult.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
            if (adapter == null) {
                adapter = new LeaveStatusListAdapter(this, listLeaveStatus, setListSize);
                recycleView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        hideKeyboard();
        finish();
    }

    @Override
    public void setListSize(int size) {
    }

    @Override
    public void setMyOrderResultListNew(List<MyOrderResultPOJO> list) {
    }

    @Override
    public List<MyOrderResultPOJO> getMyOrderResultListNew() {
        return null;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof LeaveStatusListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = listLeaveStatus.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final GetLeaveStatusPOJO.Datum deletedItem = listLeaveStatus.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());
            deleteLeaveDetails(deletedItem.getApplicationId());
        }
    }

    private void getLeaveStatus() {
        showLoading();
        hideKeyboard();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getLeaveStatusJson(logInResList.get(0).getCompId(), yearId,
                -1, logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), logInResList.get(0).getStaffId(), 0,
                GETLEAVESANPENDIS);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetLeaveStatusPOJO> call = restClientAPI.getLeaveStatus(body);
        call.enqueue(new retrofit2.Callback<GetLeaveStatusPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetLeaveStatusPOJO> call, @NonNull Response<GetLeaveStatusPOJO> response) {
                GetLeaveStatusPOJO getExpensePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getExpensePOJO != null) {
                        hideLoading();
                        if (getExpensePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getExpensePOJO.getResponseStatus().equals(TRUE)) {
                                listLeaveStatus.addAll(getExpensePOJO.getData());
                                setAdapter();
                            } else {
                                setAdapter();
                                showMessage(getExpensePOJO.getResponseMessage());
                            }
                        } else {
                            setAdapter();
                            showMessage(getExpensePOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        setAdapter();
                    }

                } else {
                    hideLoading();
                    setAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetLeaveStatusPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void deleteLeaveDetails(Integer applicationId) {
        hideKeyboard();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getLeaveDeleteJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(), logInResList.get(0).getToken(),
                applicationId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.getLeaveDelete(body);
        call.enqueue(new retrofit2.Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                InsertUpdateItemPOJO getLeaveDelete = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getLeaveDelete != null) {
                        hideLoading();
                        if (getLeaveDelete.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getLeaveDelete.getResponseStatus().equals(TRUE)) {
                                showMessage(getLeaveDelete.getResponseMessage());
                            } else {
                                showMessage(getLeaveDelete.getResponseMessage());
                            }
                        } else {
                            showMessage(getLeaveDelete.getResponseMessage());
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
}