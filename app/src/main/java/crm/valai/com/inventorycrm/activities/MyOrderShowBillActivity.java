package crm.valai.com.inventorycrm.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.adapters.MyOrderBillAdapter;
import crm.valai.com.inventorycrm.interfaces.SetListSize;
import crm.valai.com.inventorycrm.modals.InsertUpdateItemPOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.modals.MyOrderResultPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.prefs.AppPreferencesHelper;
import crm.valai.com.inventorycrm.utils.AppConstants;
import crm.valai.com.inventorycrm.utils.CommonUtils;
import crm.valai.com.inventorycrm.utils.RecyclerItemTouchHelper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.INSERT_ORDER;
import static crm.valai.com.inventorycrm.utils.AppConstants.INSERT_ORDER_DETAIL;
import static crm.valai.com.inventorycrm.utils.AppConstants.INSERT_ORDER_TAX;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.SLSS;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;
import static crm.valai.com.inventorycrm.utils.AppConstants.taxIdGST;
import static crm.valai.com.inventorycrm.utils.AppConstants.taxIdSGST;
import static crm.valai.com.inventorycrm.utils.AppConstants.uomMt;
import static crm.valai.com.inventorycrm.utils.AppConstants.uomPcs;

public class MyOrderShowBillActivity extends BaseActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, SetListSize {
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

    private String branchName;
    private MyOrderBillAdapter adapter;
    private SetListSize setListSize;
    private Integer parentId;
    private Integer inventoryClassificationId;
    private HashMap<Integer, List<MyOrderResultPOJO>> myOrderResultHashMap;
    private List<MyOrderResultPOJO> myOrderResultList;
    private AppPreferencesHelper appPreferencesHelper;
    private List<LogInPOJO.Datum> logInResList;
    private DecimalFormat decimalFormat, decimalFormatUpTo;
    private String customerName;
    private Integer customerId;
    private Boolean isRun = false;
    private int pos = 0;

    @SuppressLint({"SetTextI18n", "UseSparseArrays"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_show_bill);
        ButterKnife.bind(this);
        setListSize = this;
        setSupportActionBar(toolbar);
        decimalFormat = new DecimalFormat("#");
        decimalFormatUpTo = new DecimalFormat("#.##");

        Intent intent = getIntent();
        parentId = intent.getIntExtra("PARENT_ID", 0);
        inventoryClassificationId = intent.getIntExtra("I_CLASSIFICATION_ID", 0);
        branchName = intent.getStringExtra("BRANCH_NAME");
        customerName = intent.getStringExtra("CUSTOMER_NAME");
        customerId = intent.getIntExtra("CUSTOMER_ID", 0);

        toolbar_selected_customers.setText(branchName);
        toolbar_title.setText(intent.getStringExtra("SELECTEDMAINCATEGORY") + "-" + intent.getStringExtra("SELECTEDSUBCATEGORY"));

        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        Gson gson = new Gson();
        Type type = new TypeToken<List<LogInPOJO.Datum>>() {
        }.getType();
        logInResList = new ArrayList<>();
        logInResList = gson.fromJson(appPreferencesHelper.getLogInResponse(), type);

        myOrderResultList = new ArrayList<>();
//        myOrderResultHashMap = getMyOrderResultList();
//        if (myOrderResultHashMap == null) {
//            myOrderResultHashMap = new HashMap<>();
//            myOrderResultList = new ArrayList<>();
//        } else {
//            if (myOrderResultHashMap.size() > 0 && myOrderResultHashMap.containsKey(inventoryClassificationId)) {
//                myOrderResultList = myOrderResultHashMap.get(inventoryClassificationId);
//            }
//        }

        myOrderResultList = getMyOrderResultListNew();
        if (myOrderResultList == null) {
            myOrderResultList = new ArrayList<>();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        //recycleView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
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

        setAdapter();
    }

    private void setAdapter() {
        if (myOrderResultList != null && myOrderResultList.size() > 0) {
            setListSize(myOrderResultList.size());
            tvNoResult.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
            if (adapter == null) {
                adapter = new MyOrderBillAdapter(this, myOrderResultList, setListSize, myOrderResultHashMap, inventoryClassificationId);
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
        if (myOrderResultList != null && myOrderResultList.size() > 0) {
            showDialogBilling();
        } else {
            showMessage(getString(R.string.no_information_text));
        }
    }

    private void showDialogBilling() {
        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.billing_dialogue);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Button btnSaveDraft = dialog.findViewById(R.id.btnSaveDraft);
        Button btnSaveOnline = dialog.findViewById(R.id.btnSaveOnline);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        final TextView tvItemTotal = dialog.findViewById(R.id.tvItemTotal);
        Double itemTotal = 0.0;
        for (int i = 0; i < myOrderResultList.size(); i++) {
            itemTotal = itemTotal + myOrderResultList.get(i).getTotalAmount();
        }

        tvItemTotal.setText(String.valueOf(decimalFormat.format(itemTotal)));
        final TextView tvSubTotal = dialog.findViewById(R.id.tvSubTotal);
        tvSubTotal.setText(String.valueOf(decimalFormat.format(itemTotal)));

        final TextView tvCgst = dialog.findViewById(R.id.tvCgst);
        tvCgst.setText(String.valueOf(Double.parseDouble(tvSubTotal.getText().toString()) * 12 / 100));
        final TextView tvSgst = dialog.findViewById(R.id.tvSgst);
        tvSgst.setText(String.valueOf(Double.parseDouble(tvSubTotal.getText().toString()) * 12 / 100));
        final TextView tvTotal = dialog.findViewById(R.id.tvTotal);
        tvTotal.setText(String.valueOf(decimalFormatUpTo.format(Double.parseDouble(tvSubTotal.getText().toString())
                + Double.parseDouble(tvCgst.getText().toString())
                + Double.parseDouble(tvSgst.getText().toString()))));

        final EditText editDiscount = dialog.findViewById(R.id.editDiscount);
        final EditText editPLPlus = dialog.findViewById(R.id.editPLPlus);
        final EditText editPLMinus = dialog.findViewById(R.id.editPLMinus);
        final EditText editFOR = dialog.findViewById(R.id.editFOR);
        final EditText editRemarks = dialog.findViewById(R.id.editRemarks);

        btnSaveDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage(getString(R.string.save_draft_text));
                dialog.dismiss();
            }
        });

        btnSaveOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNetworkConnected()) {
                    showSnackBar(dialog.getCurrentFocus(), getString(R.string.internet_not_available));
                    return;
                }

                Double forDouble = 0.0;
                if (editFOR.getText().toString() != null && !editFOR.getText().toString().equals("")) {
                    forDouble = Double.parseDouble(editFOR.getText().toString());
                }

                Double plMinusDouble = 0.0;
                if (editPLMinus.getText().toString() != null && !editPLMinus.getText().toString().equals("")) {
                    plMinusDouble = Double.parseDouble(editPLMinus.getText().toString());
                }

                Double plPlusDouble = 0.0;
                if (editPLPlus.getText().toString() != null && !editPLPlus.getText().toString().equals("")) {
                    plPlusDouble = Double.parseDouble(editPLPlus.getText().toString());
                }

                Double discountDouble = 0.0;
                if (editDiscount.getText().toString() != null && !editDiscount.getText().toString().equals("")) {
                    discountDouble = Double.parseDouble(editDiscount.getText().toString());
                }

                String remarksStr = "";
                if (editRemarks.getText().toString() != null && !editRemarks.getText().toString().equals("")) {
                    remarksStr = editRemarks.getText().toString();
                }

                insertOrder(Double.parseDouble(tvTotal.getText().toString()), forDouble,
                        plMinusDouble, plPlusDouble, discountDouble, remarksStr, dialog);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        editDiscount.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Double discountText = 0.0;
                if (editDiscount.getText().toString().length() > 0) {
                    discountText = Double.parseDouble(editDiscount.getText().toString());
                }

                Double plMinusText = 0.0;
                if (editPLMinus.getText().toString().length() > 0) {
                    plMinusText = Double.parseDouble(editPLMinus.getText().toString());
                }

                Double plPlusText = 0.0;
                if (editPLPlus.getText().toString().length() > 0) {
                    plPlusText = Double.parseDouble(editPLPlus.getText().toString());
                }

                Double forText = 0.0;
                if (editFOR.getText().toString().length() > 0) {
                    forText = Double.parseDouble(editFOR.getText().toString());
                }

                Double newTextA = discountText + plMinusText;
                Double newTextB = Double.parseDouble(tvItemTotal.getText().toString()) - newTextA;
                Double discount = plPlusText + forText + newTextB;

                tvSubTotal.setText(String.valueOf(decimalFormat.format(discount)));
                tvCgst.setText(String.valueOf(Double.parseDouble(tvSubTotal.getText().toString()) * 12 / 100));
                tvSgst.setText(String.valueOf(Double.parseDouble(tvSubTotal.getText().toString()) * 12 / 100));
                tvTotal.setText(String.valueOf(decimalFormatUpTo.format(Double.parseDouble(tvSubTotal.getText().toString())
                        + Double.parseDouble(tvCgst.getText().toString())
                        + Double.parseDouble(tvSgst.getText().toString()))));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editPLPlus.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Double discountText = 0.0;
                if (editDiscount.getText().toString().length() > 0) {
                    discountText = Double.parseDouble(editDiscount.getText().toString());
                }

                Double plMinusText = 0.0;
                if (editPLMinus.getText().toString().length() > 0) {
                    plMinusText = Double.parseDouble(editPLMinus.getText().toString());
                }

                Double plPlusText = 0.0;
                if (editPLPlus.getText().toString().length() > 0) {
                    plPlusText = Double.parseDouble(editPLPlus.getText().toString());
                }

                Double forText = 0.0;
                if (editFOR.getText().toString().length() > 0) {
                    forText = Double.parseDouble(editFOR.getText().toString());
                }

                Double newTextA = discountText + plMinusText;
                Double newTextB = Double.parseDouble(tvItemTotal.getText().toString()) - newTextA;
                Double discount = plPlusText + forText + newTextB;

                tvSubTotal.setText(String.valueOf(decimalFormat.format(discount)));
                tvCgst.setText(String.valueOf(Double.parseDouble(tvSubTotal.getText().toString()) * 12 / 100));
                tvSgst.setText(String.valueOf(Double.parseDouble(tvSubTotal.getText().toString()) * 12 / 100));
                tvTotal.setText(String.valueOf(decimalFormatUpTo.format(Double.parseDouble(tvSubTotal.getText().toString())
                        + Double.parseDouble(tvCgst.getText().toString())
                        + Double.parseDouble(tvSgst.getText().toString()))));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editPLMinus.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Double discountText = 0.0;
                if (editDiscount.getText().toString().length() > 0) {
                    discountText = Double.parseDouble(editDiscount.getText().toString());
                }

                Double plMinusText = 0.0;
                if (editPLMinus.getText().toString().length() > 0) {
                    plMinusText = Double.parseDouble(editPLMinus.getText().toString());
                }

                Double plPlusText = 0.0;
                if (editPLPlus.getText().toString().length() > 0) {
                    plPlusText = Double.parseDouble(editPLPlus.getText().toString());
                }

                Double forText = 0.0;
                if (editFOR.getText().toString().length() > 0) {
                    forText = Double.parseDouble(editFOR.getText().toString());
                }

                Double newTextA = discountText + plMinusText;
                Double newTextB = Double.parseDouble(tvItemTotal.getText().toString()) - newTextA;
                Double discount = plPlusText + forText + newTextB;

                tvSubTotal.setText(String.valueOf(decimalFormat.format(discount)));
                tvCgst.setText(String.valueOf(Double.parseDouble(tvSubTotal.getText().toString()) * 12 / 100));
                tvSgst.setText(String.valueOf(Double.parseDouble(tvSubTotal.getText().toString()) * 12 / 100));
                tvTotal.setText(String.valueOf(decimalFormatUpTo.format(Double.parseDouble(tvSubTotal.getText().toString())
                        + Double.parseDouble(tvCgst.getText().toString())
                        + Double.parseDouble(tvSgst.getText().toString()))));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editFOR.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Double discountText = 0.0;
                if (editDiscount.getText().toString().length() > 0) {
                    discountText = Double.parseDouble(editDiscount.getText().toString());
                }

                Double plMinusText = 0.0;
                if (editPLMinus.getText().toString().length() > 0) {
                    plMinusText = Double.parseDouble(editPLMinus.getText().toString());
                }

                Double plPlusText = 0.0;
                if (editPLPlus.getText().toString().length() > 0) {
                    plPlusText = Double.parseDouble(editPLPlus.getText().toString());
                }

                Double forText = 0.0;
                if (editFOR.getText().toString().length() > 0) {
                    forText = Double.parseDouble(editFOR.getText().toString());
                }

                Double newTextA = discountText + plMinusText;
                Double newTextB = Double.parseDouble(tvItemTotal.getText().toString()) - newTextA;
                Double discount = plPlusText + forText + newTextB;

                tvSubTotal.setText(String.valueOf(decimalFormat.format(discount)));
                tvCgst.setText(String.valueOf(Double.parseDouble(tvSubTotal.getText().toString()) * 12 / 100));
                tvSgst.setText(String.valueOf(Double.parseDouble(tvSubTotal.getText().toString()) * 12 / 100));
                tvTotal.setText(String.valueOf(decimalFormatUpTo.format(Double.parseDouble(tvSubTotal.getText().toString())
                        + Double.parseDouble(tvCgst.getText().toString())
                        + Double.parseDouble(tvSgst.getText().toString()))));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MyOrderBillAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = myOrderResultList.get(viewHolder.getAdapterPosition()).getItemName();

            // backup of removed item for undo purpose
            final MyOrderResultPOJO deletedItem = myOrderResultList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar.make(viewHolder.itemView, name + " removed", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    @Override
    public void setListSize(int size) {
        if (size == 0) {
            mFloatingActionButton.hide();
            floatingTextView.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
            return;
        } else {
            mFloatingActionButton.show();
            floatingTextView.setVisibility(View.VISIBLE);
            tvNoResult.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
        }
        floatingTextView.setText(String.valueOf(size));
    }

//    @Override
//    public void setMyOrderResultList(HashMap<Integer, List<MyOrderResultPOJO>> list) {
//        Gson gson = new Gson();
//        Type type = new TypeToken<HashMap<Integer, List<MyOrderResultPOJO>>>() {
//        }.getType();
//        String json = gson.toJson(list, type);
//        this.appPreferencesHelper.setMyOrderResultListResponse(json);
//    }
//
//    @Override
//    public HashMap<Integer, List<MyOrderResultPOJO>> getMyOrderResultList() {
//        Gson gson = new Gson();
//        Type type = new TypeToken<HashMap<Integer, List<MyOrderResultPOJO>>>() {
//        }.getType();
//        return gson.fromJson(appPreferencesHelper.getMyOrderResultListListResponse(), type);
//    }

    @Override
    public void setMyOrderResultListNew(List<MyOrderResultPOJO> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MyOrderResultPOJO>>() {
        }.getType();
        String json = gson.toJson(list, type);
        this.appPreferencesHelper.setMyOrderResultListResponse(json);
    }

    @Override
    public List<MyOrderResultPOJO> getMyOrderResultListNew() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MyOrderResultPOJO>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getMyOrderResultListListResponse(), type);
    }

    private void insertOrder(final Double totalAmount, final Double forDiscount, final Double plNegativeDiscount,
                             final Double plPositiveDiscount, final Double discount, final String staffRemarks, final Dialog dialog) {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getInsertOrderJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(), logInResList.get(0).getToken(),
                appPreferencesHelper.getBranchId(), customerId, logInResList.get(0).getStaffId(),
                String.valueOf(SLSS + logInResList.get(0).getStaffId()) + CommonUtils.getCurrentDateForOrder(),
                staffRemarks, appPreferencesHelper.getDeviceId(), INSERT_ORDER);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateItem(body);
        call.enqueue(new Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                InsertUpdateItemPOJO insertUpdateItemPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    //hideLoading();
                    if (insertUpdateItemPOJO != null) {
                        if (insertUpdateItemPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (insertUpdateItemPOJO.getResponseStatus().equals(TRUE)) {
                                insertOrderTax(insertUpdateItemPOJO.getData().get(0).getId(),
                                        totalAmount, forDiscount, plNegativeDiscount,
                                        plPositiveDiscount, discount, staffRemarks, taxIdGST, dialog);
                            } else {
                                hideLoading();
                                showSnackBar(dialog.getCurrentFocus(), insertUpdateItemPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showSnackBar(dialog.getCurrentFocus(), insertUpdateItemPOJO.getResponseMessage());
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
                showSnackBar(dialog.getCurrentFocus(), getString(R.string.internet_not_available));
            }
        });
    }

    private void insertOrderTax(final Integer orderId, final Double totalAmount, final Double forDiscount, final Double plNegativeDiscount,
                                final Double plPositiveDiscount, final Double discount, final String discountReason,
                                final Integer taxId, final Dialog dialog) {
        hideKeyboard();
        //showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getInsertOrderTaxJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), INSERT_ORDER_TAX, orderId, taxId, totalAmount,
                appPreferencesHelper.getDeviceId(), appPreferencesHelper.getUserName(), appPreferencesHelper.getIpAddress(),
                forDiscount, plNegativeDiscount, plPositiveDiscount, discount, discountReason);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateItem(body);
        call.enqueue(new Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                InsertUpdateItemPOJO insertUpdateItemPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (insertUpdateItemPOJO != null) {
                        if (insertUpdateItemPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (insertUpdateItemPOJO.getResponseStatus().equals(TRUE)) {
                                if (!isRun) {
                                    isRun = true;
                                    insertOrderTax(orderId, totalAmount, forDiscount, plNegativeDiscount,
                                            plPositiveDiscount, discount, discountReason, taxIdSGST, dialog);
                                } else {
                                    if (myOrderResultList != null && myOrderResultList.size() > 0) {
                                        insertOrderDetail(orderId, myOrderResultList.get(pos).getItemId(), myOrderResultList.get(pos).getQuantity(),
                                                myOrderResultList.get(pos).getUom(), myOrderResultList.get(pos).getTotalAmount(), dialog);
                                    } else {
                                        hideLoading();
                                    }
                                }
                            } else {
                                hideLoading();
                                showSnackBar(dialog.getCurrentFocus(), insertUpdateItemPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showSnackBar(dialog.getCurrentFocus(), insertUpdateItemPOJO.getResponseMessage());
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
                showSnackBar(dialog.getCurrentFocus(), getString(R.string.internet_not_available));
            }
        });
    }

    private void insertOrderDetail(final Integer orderId, final Integer itemId, final Double quantity, final String uom, final Double price,
                                   final Dialog dialog) {
        hideKeyboard();
        //showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();
        int uomInt;
        if (uom.equals(getString(R.string.mt))) {
            uomInt = uomMt;
        } else {
            uomInt = uomPcs;
        }

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getInsertOrderDetailJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(), logInResList.get(0).getToken(),
                INSERT_ORDER_DETAIL, orderId, itemId, quantity, uomInt, price, appPreferencesHelper.getDeviceId(), appPreferencesHelper.getUserName(),
                appPreferencesHelper.getIpAddress());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<InsertUpdateItemPOJO> call = restClientAPI.insertUpdateItem(body);
        call.enqueue(new Callback<InsertUpdateItemPOJO>() {
            @Override
            public void onResponse(@NonNull Call<InsertUpdateItemPOJO> call, @NonNull Response<InsertUpdateItemPOJO> response) {
                InsertUpdateItemPOJO insertUpdateItemPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (insertUpdateItemPOJO != null) {
                        if (insertUpdateItemPOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (insertUpdateItemPOJO.getResponseStatus().equals(TRUE)) {
                                if (pos == myOrderResultList.size() - 1) {
                                    pos = 0;
                                    hideLoading();
                                    dialog.dismiss();
                                    showMessage(insertUpdateItemPOJO.getResponseMessage());
                                    Intent intent = new Intent();
                                    setResult(RESULT_OK, intent);
                                    finish();//finishing activity
                                } else {
                                    pos = pos + 1;
                                    insertOrderDetail(orderId, myOrderResultList.get(pos).getItemId(), myOrderResultList.get(pos).getQuantity(),
                                            myOrderResultList.get(pos).getUom(), myOrderResultList.get(pos).getTotalAmount(), dialog);
                                }
                            } else {
                                hideLoading();
                                showSnackBar(dialog.getCurrentFocus(), insertUpdateItemPOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
                            showSnackBar(dialog.getCurrentFocus(), insertUpdateItemPOJO.getResponseMessage());
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
                showSnackBar(dialog.getCurrentFocus(), getString(R.string.internet_not_available));
            }
        });
    }

    private void showSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        snackbar.show();
    }
}