package crm.valai.com.inventorycrm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import crm.valai.com.inventorycrm.adapters.ExpenseDetailsListAdapter;
import crm.valai.com.inventorycrm.interfaces.SetListSize;
import crm.valai.com.inventorycrm.modals.GetExpenseDetailsPOJO;
import crm.valai.com.inventorycrm.modals.InsertUpdateItemPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.modals.MyOrderResultPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;
import crm.valai.com.inventorycrm.utils.AppConstants;
import crm.valai.com.inventorycrm.utils.RecyclerItemTouchHelperListExpense;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.DELETE_EXPENSE_DETAIL;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_STAFF_EXPENSE_DETAIL;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.SEND_FOR_APPROVAL;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

public class ExpenseDetailsActivity extends BaseActivity implements SetListSize, RecyclerItemTouchHelperListExpense.RecyclerItemTouchHelperListener {
    public static final String TAG = MyOrderAddSelectedItemActivity.class.getSimpleName();

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

    @BindView(R.id.btnSubmitForApproval)
    Button btnSubmitForApproval;

    private ExpenseDetailsListAdapter adapter;
    private SetListSize setListSize;
    private List<LogInPOJO.Datum> logInResList;
    private List<GetExpenseDetailsPOJO.Datum> listExpenseDetails;
    private AppPreferencesHelper appPreferencesHelper;
    private Integer expenseId;

    @SuppressLint({"SetTextI18n", "UseSparseArrays"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);
        ButterKnife.bind(this);
        setListSize = this;
        setSupportActionBar(toolbar);
        expenseId = getIntent().getIntExtra("EXPENSE_ID", 0);
        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        toolbar_selected_customers.setText(appPreferencesHelper.getBranch());
        toolbar_title.setText(getIntent().getStringExtra("EXPENSE_TITLE"));
        Gson gson = new Gson();
        Type type = new TypeToken<List<LogInPOJO.Datum>>() {
        }.getType();
        logInResList = new ArrayList<>();
        logInResList = gson.fromJson(appPreferencesHelper.getLogInResponse(), type);

        listExpenseDetails = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //recycleView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperListExpense(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycleView);

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

        setAdapterExpenseDetails();
        getExpenseDetails(expenseId);
    }

    private void setAdapterExpenseDetails() {
        if (listExpenseDetails != null && listExpenseDetails.size() > 0) {
            setListSize(listExpenseDetails.size());
            tvNoResult.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
            if (adapter == null) {
                adapter = new ExpenseDetailsListAdapter(this, listExpenseDetails, setListSize);
                recycleView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
            setListSize(0);
        }
    }

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        hideKeyboard();
        finish();
    }

    @OnClick(R.id.floating_action_button)
    public void onFabButtonClick() {
        hideKeyboard();
        finish();
    }

    @OnClick(R.id.btnSubmitForApproval)
    public void onSubmitForApprovalClick() {
        hideKeyboard();
        submitForApproval();
    }

    @Override
    public void setListSize(int size) {
        if (size == 0) {
            mFloatingActionButton.hide();
            floatingTextView.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
            btnSubmitForApproval.setVisibility(View.GONE);
            return;
        } else {
            mFloatingActionButton.show();
            floatingTextView.setVisibility(View.VISIBLE);
            tvNoResult.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
            btnSubmitForApproval.setVisibility(View.VISIBLE);
        }
        floatingTextView.setText(String.valueOf(size));
    }

    @Override
    public void setMyOrderResultListNew(List<MyOrderResultPOJO> list) {
    }

    @Override
    public List<MyOrderResultPOJO> getMyOrderResultListNew() {
        return null;
    }

    private void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        snackbar.show();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ExpenseDetailsListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = listExpenseDetails.get(viewHolder.getAdapterPosition()).getExpenseCategoryName();

            // backup of removed item for undo purpose
            final GetExpenseDetailsPOJO.Datum deletedItem = listExpenseDetails.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());
            deleteExpenseDetails(deletedItem.getExpenseDetailId());
        }
    }

    private void getExpenseDetails(Integer expenseId) {
        showLoading();
        hideKeyboard();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getExpenseStaffDetailJson(logInResList.get(0).getCompId(), logInResList.get(0).getStaffId(),
                expenseId, 1, logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), GET_STAFF_EXPENSE_DETAIL, 0);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetExpenseDetailsPOJO> call = restClientAPI.getExpenseDetails(body);
        call.enqueue(new retrofit2.Callback<GetExpenseDetailsPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetExpenseDetailsPOJO> call, @NonNull Response<GetExpenseDetailsPOJO> response) {
                GetExpenseDetailsPOJO getExpensePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getExpensePOJO != null) {
                        hideLoading();
                        if (getExpensePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getExpensePOJO.getResponseStatus().equals(TRUE)) {
                                listExpenseDetails.addAll(getExpensePOJO.getData());
                                setAdapterExpenseDetails();
                            } else {
                                setAdapterExpenseDetails();
                                showMessage(getExpensePOJO.getResponseMessage());
                            }
                        } else {
                            setAdapterExpenseDetails();
                            showMessage(getExpensePOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        setAdapterExpenseDetails();
                    }

                } else {
                    hideLoading();
                    setAdapterExpenseDetails();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetExpenseDetailsPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                setAdapterExpenseDetails();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void submitForApproval() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getSubmitForApprovalJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), SEND_FOR_APPROVAL, appPreferencesHelper.getUserName(), appPreferencesHelper.getIpAddress(),
                appPreferencesHelper.getDeviceId(), expenseId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateExpense(body);
        call.enqueue(new retrofit2.Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                InsertUpdateItemPOJO insertUpdateItemPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (insertUpdateItemPOJO != null) {
                        if (insertUpdateItemPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (insertUpdateItemPOJO.getResponseStatus().equals(TRUE)) {
                                showMessage(insertUpdateItemPOJO.getResponseMessage());

                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent();
                                        setResult(RESULT_OK, intent);
                                        finish();//finishing activity
                                    }
                                }, 2000);

                            } else {
                                hideLoading();
                                showMessage(insertUpdateItemPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showMessage(insertUpdateItemPOJO.getResponseMessage());
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

    private void deleteExpenseDetails(Integer expenseDetailId) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.deleteExpenseDetailsJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), DELETE_EXPENSE_DETAIL, appPreferencesHelper.getUserName(), appPreferencesHelper.getIpAddress(),
                appPreferencesHelper.getDeviceId(), expenseId, expenseDetailId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateExpense(body);
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
}