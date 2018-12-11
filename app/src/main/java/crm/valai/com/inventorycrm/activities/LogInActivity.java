package crm.valai.com.inventorycrm.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.modals.PunchInOutPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;
import crm.valai.com.inventorycrm.utils.AppConstants;
import crm.valai.com.inventorycrm.utils.CommonUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_PERMISSION_CODE_READ_PHONE_STATE;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;
import static crm.valai.com.inventorycrm.utils.AppConstants.punchInType;

/**
 * @author by Mohit Arora on 26/3/18.
 */
public class LogInActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    public static final String TAG = LogInActivity.class.getSimpleName();

    @BindView(R.id.edtUserName)
    EditText edtUserName;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    private AppPreferencesHelper appPreferencesHelper;
    private TelephonyManager telephonyManager;
    private String deviceId, ipAddress;
    private LogInPOJO.Datum logInList;

    @NonNull
    public static Intent getStartIntent(Context context) {
        return new Intent(context, LogInActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
        }
    }

    @OnClick(R.id.btnSignIn)
    public void onSignInBtnClick() {
        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }

        if (edtUserName.getText().length() == 0) {
            showMessage(getString(R.string.enter_user_name));
            return;
        }

        if (edtPassword.getText().length() == 0) {
            showMessage(getString(R.string.enter_password));
            return;
        }

        logInRequestCall();
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    void showPunchInDialogue(final LogInPOJO.Datum logInList) {
        this.logInList = logInList;

        if (!isPermissionsGranted(Manifest.permission.READ_PHONE_STATE)) {
            requestPermission(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_CODE_READ_PHONE_STATE);
            return;
        }
        deviceId = telephonyManager.getDeviceId();
        ipAddress = CommonUtils.getIpAddress(this);
        final Dialog dialog = new Dialog(this);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        // Include dialog.xml file
        dialog.setContentView(R.layout.custom_dialogue);
        // Set dialog title
        dialog.setTitle(null);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        ImageButton punchInBtn = dialog.findViewById(R.id.punchInBtn);
        // if decline button is clicked, close the custom dialog
        punchInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVibration();
                punchInRequestCall(logInList, dialog);
            }
        });
    }

    // Open DashBoard Activity
    private void openDashBoardActivity() {
        Intent intent = DashBoardActivity.getStartIntent(LogInActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void startVibration() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 100 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (v != null) {
                v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        } else {
            //deprecated in API 26
            if (v != null) {
                v.vibrate(100);
            }
        }
    }

    // Request Rest Client API Call For LogIn
    public void logInRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getLogInJson(edtUserName.getText().toString(), edtPassword.getText().toString());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<LogInPOJO> call = restClientAPI.logIn(body);
        call.enqueue(new Callback<LogInPOJO>() {
            @Override
            public void onResponse(@NonNull Call<LogInPOJO> call, @NonNull Response<LogInPOJO> response) {
                LogInPOJO logInPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (logInPOJO != null) {
                        if (logInPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                            if (logInPOJO.getResponseStatus().equals(TRUE)) {
                                if (logInPOJO.getData().get(0).getLoginId() != 0) {
                                    //logInPOJO.getData().get(0).setStaffId(1);
                                    Gson gson = new Gson();
                                    Type type = new TypeToken<List<LogInPOJO.Datum>>() {
                                    }.getType();
                                    String json = gson.toJson(logInPOJO.getData(), type);
                                    appPreferencesHelper.setLogInResponse(json);
                                    showPunchInDialogue(logInPOJO.getData().get(0));
                                } else {
                                    hideLoading();
                                    showMessage(logInPOJO.getResponseMessage());
                                }
                            } else {
                                hideLoading();
                                showMessage(logInPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showMessage(logInPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LogInPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    // Request Rest Client API Call For Punch In
    public void punchInRequestCall(final LogInPOJO.Datum logInList, final Dialog dialog) {
        hideKeyboard();
        //showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getPunchInOutJson(logInList.getCompId(), logInList.getStaffId(), punchInType,
                edtUserName.getText().toString(), ipAddress, deviceId, logInList.getLoginId(), logInList.getToken());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<PunchInOutPOJO> call = restClientAPI.punchInOut(body);
        call.enqueue(new Callback<PunchInOutPOJO>() {
            @Override
            public void onResponse(@NonNull Call<PunchInOutPOJO> call, @NonNull Response<PunchInOutPOJO> response) {
                PunchInOutPOJO punchInOutPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    //hideLoading();
                    if (punchInOutPOJO != null) {
                        if (punchInOutPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (punchInOutPOJO.getResponseStatus().equals(TRUE)) {
                                dialog.dismiss();
                                showMessage(punchInOutPOJO.getResponseMessage());
                                appPreferencesHelper.setLogInId(logInList.getLoginId());
                                appPreferencesHelper.setPunchDate(CommonUtils.getCurrentDate());
                                appPreferencesHelper.setDeviceId(deviceId);
                                appPreferencesHelper.setIpAddress(ipAddress);
                                appPreferencesHelper.setUserName(edtUserName.getText().toString());
                                appPreferencesHelper.setPunchValue(getString(R.string.nav_punch_in));
                                openDashBoardActivity();
                            } else {
                                //hideLoading();
                                showMessage(punchInOutPOJO.getResponseMessage());
                            }
                        } else {
                            //hideLoading();
                            showMessage(punchInOutPOJO.getResponseMessage());
                        }
                    } else {
                        //hideLoading();
                    }

                } else {
                    //hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PunchInOutPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                //hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE_READ_PHONE_STATE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPunchInDialogue(logInList);
                    break;
                } else {
                    showMessage(getString(R.string.give_permission_text));
                }
            }
        }
    }
}