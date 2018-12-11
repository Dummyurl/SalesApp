package crm.valai.com.inventorycrm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;
import crm.valai.com.inventorycrm.utils.AppConstants;

/**
 * @author by Mohit Arora on 26/3/18.
 */
public class SplashActivity extends BaseActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();
    private int logInId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);

//        String compareTime = CommonUtils.compareDates(CommonUtils.getCurrentDate(), appPreferencesHelper.getPunchDate());
//        Log.e("compareTime","compareTime??"+compareTime);
//        if (compareTime != null && compareTime.equals("1")) {
//            appPreferencesHelper.logOutFromPref();
//        }

        logInId = appPreferencesHelper.getLogInId();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openActivities();
            }
        }, AppConstants.SPLASH_TIME_OUT);
    }

    private void openActivities() {
        if (logInId != 0) {
            openDashBoardActivity();
        } else {
            openLogInActivity();
        }
    }

    public void openLogInActivity() {
        Intent intent = LogInActivity.getStartIntent(SplashActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void openDashBoardActivity() {
        Intent intent = DashBoardActivity.getStartIntent(SplashActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}