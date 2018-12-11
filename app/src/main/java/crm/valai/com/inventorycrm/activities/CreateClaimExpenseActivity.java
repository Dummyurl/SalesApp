package crm.valai.com.inventorycrm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
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
import crm.valai.com.inventorycrm.modals.InsertUpdateItemPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;
import crm.valai.com.inventorycrm.utils.AppConstants;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.INSERT_EXPENSE;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

/**
 * @author by Mohit Arora on 30/4/18.
 */
public class CreateClaimExpenseActivity extends BaseActivity {
    public static final String TAG = CreateClaimExpenseActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar_selected_customers)
    TextView toolbar_selected_customers;

    @BindView(R.id.edtExpenseTitle)
    EditText edtExpenseTitle;

    @BindView(R.id.edtRemarks)
    EditText edtRemarks;

    @BindView(R.id.spnrExpenseType)
    Spinner spnrExpenseType;

    private List<LogInPOJO.Datum> logInResList;
    private AppPreferencesHelper appPreferencesHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_claim_expenses);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        toolbar_selected_customers.setText(appPreferencesHelper.getBranch());
        toolbar_title.setText(getString(R.string.create_claim_expense_text));

        Gson gson = new Gson();
        Type type = new TypeToken<List<LogInPOJO.Datum>>() {
        }.getType();
        logInResList = new ArrayList<>();
        logInResList = gson.fromJson(appPreferencesHelper.getLogInResponse(), type);
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitButtonClick() {
        hideKeyboard();
        if (edtExpenseTitle.getText().toString().equals("")) {
            showMessage(getString(R.string.add_expense_title));
            return;
        }

        addExpense();
    }

    private void addExpense() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.insertUpdateExpenseJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), INSERT_EXPENSE, logInResList.get(0).getStaffId(),
                0, logInResList.get(0).getStaffId(), edtExpenseTitle.getText().toString(), appPreferencesHelper.getUserName(),
                appPreferencesHelper.getIpAddress(), appPreferencesHelper.getDeviceId(), edtRemarks.getText().toString());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateExpense(body);
        call.enqueue(new retrofit2.Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                final InsertUpdateItemPOJO insertUpdateItemPOJO = response.body();
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
                                        Intent intent = new Intent(CreateClaimExpenseActivity.this, AddExpensesActivity.class);
                                        intent.putExtra("EXPENSE_TITLE", edtExpenseTitle.getText().toString());
                                        intent.putExtra("STAFF_EXPENSE_REMARKS", edtRemarks.getText().toString());
                                        intent.putExtra("EXPENSE_ID", insertUpdateItemPOJO.getData().get(0).getId());
                                        intent.putExtra("TAG", getIntent().getStringExtra("TAG"));
                                        intent.putExtra("STATUS", getIntent().getIntExtra("STATUS",-1));
                                        startActivityForResult(intent, 100);
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

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        hideKeyboard();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();//finishing activity
        }
    }
}