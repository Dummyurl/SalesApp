package crm.valai.com.inventorycrm.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.modals.GetExpenseDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetExpenseTypePOJO;
import crm.valai.com.inventorycrm.modals.InsertUpdateItemPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;
import crm.valai.com.inventorycrm.utils.AlbumStorageDirFactory;
import crm.valai.com.inventorycrm.utils.AppConstants;
import crm.valai.com.inventorycrm.utils.BaseAlbumDirFactory;
import crm.valai.com.inventorycrm.utils.CommonUtils;
import crm.valai.com.inventorycrm.utils.FroyoAlbumDirFactory;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.FROM_DATE_TAG;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_EXPENSETYPE;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_STAFF_EXPENSE_DETAIL;
import static crm.valai.com.inventorycrm.utils.AppConstants.INSERT_EXPENSE_DETAIL;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_CAMERA;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_CHOOSER;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_DOC_FILE;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_PERMISSION_CODE_CAMERA;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_PERMISSION_CODE_STORAGE;
import static crm.valai.com.inventorycrm.utils.AppConstants.REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TO_DATE_TAG;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;
import static crm.valai.com.inventorycrm.utils.CommonUtils.prepareFilePart;
import static crm.valai.com.inventorycrm.utils.CommonUtils.prepareStringPart;

/**
 * @author by Mohit Arora on 31/3/18.
 */

public class AddExpensesActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    public static final String TAG = AddExpensesActivity.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar_selected_customers)
    TextView toolbar_selected_customers;

    @BindView(R.id.edtSelectFromDate)
    EditText edtSelectFromDate;

    @BindView(R.id.edtSelectToDate)
    EditText edtSelectToDate;

    @BindView(R.id.edtStartLocation)
    EditText edtStartLocation;

    @BindView(R.id.edtEndLocation)
    EditText edtEndLocation;

    @BindView(R.id.edtAmount)
    EditText edtAmount;

    @BindView(R.id.edtRemarks)
    EditText edtRemarks;

    @BindView(R.id.imgView)
    ImageView imgView;

    @BindView(R.id.tvBrowse)
    TextView tvBrowse;

    @BindView(R.id.btnAddExpense)
    Button btnAddExpense;

    @BindView(R.id.spnrExpenseType)
    Spinner spnrExpenseType;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private String imagePath = "";
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private Integer expenseId;
    private Integer status;
    private List<LogInPOJO.Datum> logInResList;
    private AppPreferencesHelper appPreferencesHelper;
    private String title;
    private String staffRemarks;
    private String tag;
    private Integer expenseType;
    private File outputFile;
    private List<GetExpenseTypePOJO.Datum> listExpenseType;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense_dialogue);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        edtSelectFromDate.setText(CommonUtils.getCurrentDateNew());
        edtSelectToDate.setText(CommonUtils.getCurrentDateNew());

        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        toolbar_selected_customers.setText(appPreferencesHelper.getBranch());

        expenseId = getIntent().getIntExtra("EXPENSE_ID", 0);
        status = getIntent().getIntExtra("STATUS", -1);
        title = getIntent().getStringExtra("EXPENSE_TITLE");
        tag = getIntent().getStringExtra("TAG");
        toolbar_title.setText(title);
        staffRemarks = getIntent().getStringExtra("STAFF_EXPENSE_REMARKS");

        Gson gson = new Gson();
        Type type = new TypeToken<List<LogInPOJO.Datum>>() {
        }.getType();
        logInResList = new ArrayList<>();
        logInResList = gson.fromJson(appPreferencesHelper.getLogInResponse(), type);

        listExpenseType = new ArrayList<>();
        if (!isNetworkConnected()) {
            listExpenseType = getExpenseTypeList();
            if (listExpenseType != null && listExpenseType.size() > 0) {
                addSpinnerItems(listExpenseType);
            }
        } else if (logInResList != null && logInResList.size() > 0) {
            getExpenseType();
        }
    }

    @OnTextChanged(value = R.id.edtStartLocation, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void edtStartLocationInput(Editable editable) {
        switch (spnrExpenseType.getSelectedItem().toString()) {
            case "TRAVEL":
                if (editable.length() > 0 && edtEndLocation.getText().length() > 0) {
                    btnAddExpense.setVisibility(View.VISIBLE);
                } else {
                    btnAddExpense.setVisibility(View.GONE);
                }
                break;
            case "BOARDING":
                if (editable.length() > 0) {
                    btnAddExpense.setVisibility(View.VISIBLE);
                } else {
                    btnAddExpense.setVisibility(View.GONE);
                }
                break;
            case "LODGING":
                if (editable.length() > 0 && edtAmount.getText().length() > 0) {
                    btnAddExpense.setVisibility(View.VISIBLE);
                } else {
                    btnAddExpense.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    @OnTextChanged(value = R.id.edtEndLocation, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void edtEndLocationInput(Editable editable) {
        switch (spnrExpenseType.getSelectedItem().toString()) {
            case "TRAVEL":
                if (editable.length() > 0 && edtStartLocation.getText().length() > 0) {
                    btnAddExpense.setVisibility(View.VISIBLE);
                } else {
                    btnAddExpense.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    @OnTextChanged(value = R.id.edtAmount, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void edtAmountInput(Editable editable) {
        switch (spnrExpenseType.getSelectedItem().toString()) {
            case "LODGING":
                if (editable.length() > 0 && edtStartLocation.getText().length() > 0) {
                    btnAddExpense.setVisibility(View.VISIBLE);
                } else {
                    btnAddExpense.setVisibility(View.GONE);
                }
                break;
            case "OTHERS":
                if (editable.length() > 0) {
                    btnAddExpense.setVisibility(View.VISIBLE);
                } else {
                    btnAddExpense.setVisibility(View.GONE);
                }
                break;
            case "HOSPITAL":
                if (editable.length() > 0) {
                    btnAddExpense.setVisibility(View.VISIBLE);
                } else {
                    btnAddExpense.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }

    }

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        hideKeyboard();
        finish();
    }

    @OnClick(R.id.edtSelectFromDate)
    public void onSelectFromDateClick() {
        hideKeyboard();
        showDatePickerDialog(FROM_DATE_TAG);
    }

    @OnClick(R.id.edtSelectToDate)
    public void onSelectToDateClick() {
        hideKeyboard();
        showDatePickerDialog(TO_DATE_TAG);
    }

    @OnClick(R.id.tvBrowseText)
    public void onBrowseButtonClick() {
        hideKeyboard();
        selectImageDialog();
    }

    @OnClick(R.id.btnAddExpense)
    public void onAddExpenseClick() {
        hideKeyboard();
        addExpenseDetails();
        if (outputFile != null && !outputFile.getPath().equals("")) {
            addImage();
        }
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        hideKeyboard();
        //edtSelectFromDate.setText(CommonUtils.getCurrentDateNew());
        //edtSelectToDate.setText(CommonUtils.getCurrentDateNew());
        edtStartLocation.requestFocus();
        edtStartLocation.setText(null);
        edtEndLocation.setText(null);
        edtAmount.setText(null);
        edtRemarks.setText(null);

        Intent intent = new Intent(this, ExpenseDetailsActivity.class);
        intent.putExtra("EXPENSE_ID", expenseId);
        intent.putExtra("EXPENSE_TITLE", title);
        intent.putExtra("STAFF_EXPENSE_REMARKS", staffRemarks);
        startActivityForResult(intent, 100);
    }

    void addSpinnerItems(final List<GetExpenseTypePOJO.Datum> datumList) {
        List<String> list = new ArrayList<>();
        if (datumList.size() > 0) {
            for (int i = 0; i < datumList.size(); i++) {
                list.add(datumList.get(i).getName());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrExpenseType.setAdapter(dataAdapter);
        //spnrExpenseType.setSelection(0, false);
        spnrExpenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hideKeyboard();
                if (datumList.size() > 0) {
                    expenseType = datumList.get(position).getId();
                }

                edtStartLocation.requestFocus();
                edtStartLocation.setText(null);
                edtEndLocation.setText(null);
                edtAmount.setText(null);
                edtRemarks.setText(null);
                setEditTextAccToExpenseTypeSelections(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }

    private void getExpenseType() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getLeaveTypeJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), GET_EXPENSETYPE);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetExpenseTypePOJO> call = restClientAPI.getExpenseType(body);
        call.enqueue(new retrofit2.Callback<GetExpenseTypePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetExpenseTypePOJO> call, @NonNull Response<GetExpenseTypePOJO> response) {
                GetExpenseTypePOJO getExpenseTypePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getExpenseTypePOJO != null) {
                        if (getExpenseTypePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getExpenseTypePOJO.getResponseStatus().equals(TRUE)) {
                                listExpenseType.addAll(getExpenseTypePOJO.getData());
                                setExpenseTypeList(listExpenseType);
                                addSpinnerItems(listExpenseType);
//                                if (tag.equals("EDIT")) {
//                                    Log.e("expenseId", "expenseId>>" + expenseId);
//                                    Log.e("status", "status>>" + status);
//                                    getExpenseDetails(expenseId, status);
//                                }else{
//                                    hideLoading();
//                                }
                            } else {
                                hideLoading();
                                showMessage(getExpenseTypePOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showMessage(getExpenseTypePOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetExpenseTypePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void getExpenseDetails(final Integer expenseId, final Integer status) {
        hideKeyboard();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getExpenseStaffDetailJson(logInResList.get(0).getCompId(), logInResList.get(0).getStaffId(),
                expenseId, status, logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), GET_STAFF_EXPENSE_DETAIL, 0);

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

                                setEditTextAccToExpenseTypeSelections(getExpensePOJO.getData().get(0).getExpenseCategoryName());
                                edtSelectFromDate.setText(getExpensePOJO.getData().get(0).getFromDate());
                                edtSelectToDate.setText(getExpensePOJO.getData().get(0).getToDate());
                                edtStartLocation.setText(getExpensePOJO.getData().get(0).getFromLocation());
                                edtEndLocation.setText(getExpensePOJO.getData().get(0).getToLocation());
                                edtAmount.setText(String.valueOf(getExpensePOJO.getData().get(0).getBillAmount()));
                                edtRemarks.setText(getExpensePOJO.getData().get(0).getRemarks());

                            } else {
                                hideLoading();
                                showMessage(getExpensePOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showMessage(getExpensePOJO.getResponseMessage());
                        }
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetExpenseDetailsPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void addExpenseDetails() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.insertExpenseDetailsJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), INSERT_EXPENSE_DETAIL, logInResList.get(0).getStaffId(),
                expenseId, logInResList.get(0).getStaffId(), title, appPreferencesHelper.getUserName(),
                appPreferencesHelper.getIpAddress(), appPreferencesHelper.getDeviceId(), staffRemarks, "0", edtSelectFromDate.getText().toString(),
                edtSelectToDate.getText().toString(), edtStartLocation.getText().toString(), edtEndLocation.getText().toString(),
                edtAmount.getText().toString(), edtRemarks.getText().toString(), expenseType);

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

    private void addImage() {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        MultipartBody.Part fileBody = prepareFilePart("file", outputFile);
        MultipartBody.Part fileBody2 = prepareStringPart("compId", String.valueOf(logInResList.get(0).getCompId()));
        MultipartBody.Part fileBody3 = prepareStringPart("expenseId", String.valueOf(expenseId));
        MultipartBody.Part fileBody4 = prepareStringPart("expenseDetailId", String.valueOf(0));
        MultipartBody.Part fileBody5 = prepareStringPart("loginid", String.valueOf(logInResList.get(0).getLoginId()));
        MultipartBody.Part fileBody6 = prepareStringPart("token", String.valueOf(logInResList.get(0).getToken()));

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertImage(fileBody, fileBody2, fileBody3, fileBody4, fileBody5, fileBody6);

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
                                //showMessage(insertUpdateItemPOJO.getResponseMessage());
                            }
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

    public void showDatePickerDialog(final String tag) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.TimePickerTheme,
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

                        setDateWithTagName(tag, new_month + "/"
                                + new_day + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void setDateWithTagName(String tag, String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        if (tag.equals(FROM_DATE_TAG)) {
            try {
                Date date1 = sdf.parse(date);
                Date date2 = sdf.parse(edtSelectToDate.getText().toString());
                if (date1.compareTo(date2) > 0) {
                    showMessage(getString(R.string.invalid_date));
                } else if (date1.compareTo(date2) <= 0) {
                    edtSelectFromDate.setText(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (tag.equals(TO_DATE_TAG)) {
            try {
                Date date1 = sdf.parse(edtSelectFromDate.getText().toString());
                Date date2 = sdf.parse(date);
                if (date1.compareTo(date2) > 0) {
                    showMessage(getString(R.string.invalid_date));
                } else if (date1.compareTo(date2) <= 0) {
                    edtSelectToDate.setText(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void selectImageDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Attach File",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @SuppressLint("InlinedApi")
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    pickFromGallery();

                } else if (items[item].equals("Attach File")) {
                    pickFromFile();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void pickFromFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS);
        } else {
            new MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(REQUEST_DOC_FILE)
                    .withHiddenFiles(true)
                    .withTitle("Sample title")
                    .start();
        }
    }

    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_STORAGE);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_CHOOSER);
        }
    }

    public void cameraIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_CAMERA);
        } else {
            if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                showMessage(getString(R.string.camera_not_support));
                return;
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = null;
            try {
                file = new File(setUpPhotoFile().getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        }
    }

    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();
        imagePath = f.getAbsolutePath();
        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String JPEG_FILE_PREFIX = "IMG_";
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        String JPEG_FILE_SUFFIX = ".jpg";
        return File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
    }

    @Nullable
    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            showMessage("External storage is not mounted READ/WRITE.");
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    public String getAlbumName() {
        return getString(R.string.app_name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSER:
                    final Uri uri = data.getData();
                    if (uri != null) {
                        //btnAddExpense.setVisibility(View.VISIBLE);
                        imgView.setVisibility(View.VISIBLE);
                        imgView.setImageResource(0);
                        imagePath = FileUtils.getPath(this, uri);
                        outputFile = new File(imagePath);
                        String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                        tvBrowse.setText(null);
                        tvBrowse.setText(fileName);
                        Glide.with(this)
                                .load(uri)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                .into(imgView);
                    }
                    break;

                case REQUEST_CAMERA:
                    //btnAddExpense.setVisibility(View.VISIBLE);
                    imgView.setVisibility(View.VISIBLE);
                    imgView.setImageResource(0);
                    outputFile = new File(imagePath);
                    String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                    tvBrowse.setText(null);
                    tvBrowse.setText(fileName);
                    Glide.with(this)
                            .load(imagePath)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(imgView);
                    break;

                case REQUEST_DOC_FILE:
                    imagePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                    if (imagePath != null) {
                        //btnAddExpense.setVisibility(View.VISIBLE);
                        imgView.setVisibility(View.GONE);
                        imgView.setImageResource(0);
                        outputFile = new File(imagePath);
                        fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                        tvBrowse.setText(null);
                        tvBrowse.setText(fileName);
                    }
                    break;

                case 100:
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();//finishing activity
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_storage_permission));
                }

                break;
            }

            case REQUEST_PERMISSION_CODE_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_storage_permission));
                }

                break;
            }

            case REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromFile();
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_storage_permission));
                }

                break;
            }
        }
    }

    private void setEditTextAccToExpenseTypeSelections(String s) {
        switch (s) {
            case "TRAVEL":
                edtSelectFromDate.setVisibility(View.VISIBLE);
                edtSelectToDate.setVisibility(View.VISIBLE);
                edtStartLocation.setVisibility(View.VISIBLE);
                edtStartLocation.setHint(getString(R.string.start_location));
                edtEndLocation.setVisibility(View.VISIBLE);
                edtAmount.setVisibility(View.VISIBLE);
                edtRemarks.setVisibility(View.VISIBLE);
                edtSelectFromDate.setText(CommonUtils.getCurrentDateNew());
                edtSelectToDate.setText(CommonUtils.getCurrentDateNew());
                break;
            case "BOARDING":
                edtSelectFromDate.setVisibility(View.VISIBLE);
                edtSelectToDate.setVisibility(View.GONE);
                edtStartLocation.setVisibility(View.VISIBLE);
                edtStartLocation.setHint(getString(R.string.location));
                edtEndLocation.setVisibility(View.GONE);
                edtAmount.setVisibility(View.VISIBLE);
                edtRemarks.setVisibility(View.VISIBLE);
                edtSelectFromDate.setText(CommonUtils.getCurrentDateNew());
                edtSelectToDate.setText(null);
                break;
            case "LODGING":
                edtSelectFromDate.setVisibility(View.VISIBLE);
                edtSelectToDate.setVisibility(View.VISIBLE);
                edtStartLocation.setVisibility(View.VISIBLE);
                edtStartLocation.setHint(getString(R.string.location));
                edtEndLocation.setVisibility(View.GONE);
                edtAmount.setVisibility(View.VISIBLE);
                edtRemarks.setVisibility(View.VISIBLE);
                edtSelectFromDate.setText(CommonUtils.getCurrentDateNew());
                edtSelectToDate.setText(CommonUtils.getCurrentDateNew());
                break;
            case "OTHERS":
                edtSelectFromDate.setVisibility(View.GONE);
                edtSelectToDate.setVisibility(View.GONE);
                edtStartLocation.setVisibility(View.GONE);
                edtEndLocation.setVisibility(View.GONE);
                edtAmount.setVisibility(View.VISIBLE);
                edtRemarks.setVisibility(View.VISIBLE);
                edtSelectFromDate.setText(null);
                edtSelectToDate.setText(null);
                break;
            case "HOSPITAL":
                edtSelectFromDate.setVisibility(View.GONE);
                edtSelectToDate.setVisibility(View.GONE);
                edtStartLocation.setVisibility(View.GONE);
                edtEndLocation.setVisibility(View.GONE);
                edtAmount.setVisibility(View.VISIBLE);
                edtRemarks.setVisibility(View.VISIBLE);
                edtSelectFromDate.setText(null);
                edtSelectToDate.setText(null);
                break;
            default:
                edtSelectFromDate.setVisibility(View.VISIBLE);
                edtSelectToDate.setVisibility(View.VISIBLE);
                edtStartLocation.setVisibility(View.VISIBLE);
                edtEndLocation.setVisibility(View.VISIBLE);
                edtAmount.setVisibility(View.VISIBLE);
                edtRemarks.setVisibility(View.VISIBLE);
                edtSelectFromDate.setText(CommonUtils.getCurrentDateNew());
                edtSelectToDate.setText(CommonUtils.getCurrentDateNew());
                break;
        }
    }

    public List<GetExpenseTypePOJO.Datum> getExpenseTypeList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetExpenseTypePOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getExpensesTypeListResponse(), type);
    }

    public void setExpenseTypeList(List<GetExpenseTypePOJO.Datum> expenseTypeList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetExpenseTypePOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(expenseTypeList, type);
        this.appPreferencesHelper.setExpensesTypeListResponse(json);
    }
}