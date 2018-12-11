package crm.valai.com.inventorycrm.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.activities.AddExpensesActivity;
import crm.valai.com.inventorycrm.activities.CreateClaimExpenseActivity;
import crm.valai.com.inventorycrm.adapters.ExpenseDetailsAdapter;
import crm.valai.com.inventorycrm.adapters.MyExpenseAdapter;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.interfaces.MyExpenseEditInterface;
import crm.valai.com.inventorycrm.interfaces.SetListSize;
import crm.valai.com.inventorycrm.modals.GetExpenseDetailsPOJO;
import crm.valai.com.inventorycrm.modals.GetExpensePOJO;
import crm.valai.com.inventorycrm.modals.GetExpenseTypePOJO;
import crm.valai.com.inventorycrm.modals.LogInPOJO;
import crm.valai.com.inventorycrm.modals.MyOrderResultPOJO;
import crm.valai.com.inventorycrm.network.ApiClient;
import crm.valai.com.inventorycrm.network.JsonClient;
import crm.valai.com.inventorycrm.network.RestClient;
import crm.valai.com.inventorycrm.utils.CommonUtils;
import crm.valai.com.inventorycrm.utils.RecyclerItemClickListener;
import crm.valai.com.inventorycrm.utils.RecyclerItemTouchHelperExpense;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static crm.valai.com.inventorycrm.utils.AppConstants.API_RESPONSE_CODE_PUNCH_IN_OUT;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_EXPENSE_STATUS;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_STAFF_EXPENSE;
import static crm.valai.com.inventorycrm.utils.AppConstants.GET_STAFF_EXPENSE_DETAIL;
import static crm.valai.com.inventorycrm.utils.AppConstants.RESPONSE_CODE;
import static crm.valai.com.inventorycrm.utils.AppConstants.TRUE;

public class MyExpensesFragment extends BaseFragment implements RecyclerItemTouchHelperExpense.RecyclerItemTouchHelperListener, SetListSize, MyExpenseEditInterface {
    public static final String TAG = MyExpensesFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.main_progress)
    ProgressBar progressBar;

    @BindView(R.id.spnrStatus)
    Spinner spnrStatus;

    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @BindView(R.id.tvNoResult)
    TextView tvNoResult;

    @BindView(R.id.btnAdd)
    FloatingActionButton mFloatingActionButton;

    private FragmentListner fragmentListner;
    private MyExpenseAdapter adapter;
    private List<GetExpensePOJO.Datum> myExpenseResultList;
    private List<GetExpenseDetailsPOJO.Datum> listExpenseDetails;
    private List<GetExpenseTypePOJO.Datum> listExpenseType;
    private List<LogInPOJO.Datum> logInResList;
    private HashMap<Integer, List<GetExpensePOJO.Datum>> hashExpenseResultList;
    private HashMap<Integer, List<GetExpenseDetailsPOJO.Datum>> hashExpenseDetails;
    private RecyclerView recyclerView;
    private TextView tvNoResultExpense;
    private LinearLayout llOrderDetails;
    private TextView tvTotalAmount;
    private SetListSize setListSize;
    private MyExpenseEditInterface myExpenseEditInterface;

    public MyExpensesFragment() {
        // Required empty public constructor
    }

    public static MyExpensesFragment newInstance(String param1, String param2) {
        MyExpensesFragment fragment = new MyExpensesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_myexpenses, container, false);
        ButterKnife.bind(this, rootView);
        setListSize = this;
        myExpenseEditInterface = this;
        return rootView;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar.setVisibility(View.GONE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelperExpense(0, ItemTouchHelper.LEFT, this);
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

        setValuesForGetListExpense();
        //setRecycleTouchListener();
    }

    @SuppressLint("UseSparseArrays")
    private void setValuesForGetListExpense() {
        logInResList = new ArrayList<>();
        logInResList = fragmentListner.getSignInResultList();
        listExpenseType = new ArrayList<>();

        myExpenseResultList = new ArrayList<>();
        hashExpenseResultList = new HashMap<>();
        hashExpenseDetails = new HashMap<>();
        setAdapter();

        if (!isNetworkConnected()) {
            listExpenseType = fragmentListner.getExpenseTypeList();
            if (listExpenseType != null && listExpenseType.size() > 0) {
                addStatusSpinner(listExpenseType);
            }
        } else if (logInResList != null && logInResList.size() > 0) {
            getExpenseStatusType();
        }
    }

    private void setAdapter() {
        if (myExpenseResultList != null && myExpenseResultList.size() > 0) {
            tvNoResult.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
            adapter = new MyExpenseAdapter(getContext(), myExpenseResultList, myExpenseEditInterface);
            recycleView.setAdapter(adapter);
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
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

    private void setRecycleTouchListener() {
        recycleView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recycleView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showDialogExpenseDetails(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.btnAdd)
    public void onAddButtonClick() {
        hideKeyboard();
        Intent intent = new Intent(getContext(), CreateClaimExpenseActivity.class);
        intent.putExtra("TAG", "ADD");
        startActivityForResult(intent, 100);
    }

    private void getExpenseStatusType() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getExpenseStatusJson(logInResList.get(0).getCompId(), logInResList.get(0).getLoginId(),
                logInResList.get(0).getToken(), GET_EXPENSE_STATUS, 1);

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
                                fragmentListner.setExpenseTypeList(listExpenseType);
                                addStatusSpinner(listExpenseType);
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

    void addStatusSpinner(final List<GetExpenseTypePOJO.Datum> datumList) {
        List<String> list = new ArrayList<>();
        if (datumList.size() > 0) {
            for (int i = 0; i < datumList.size(); i++) {
                list.add(datumList.get(i).getName());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrStatus.setAdapter(dataAdapter);
        //spnrStatus.setSelection(0, false);
        spnrStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UseSparseArrays")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hideKeyboard();
                hashExpenseResultList = fragmentListner.getExpenseResultList();
                if (hashExpenseResultList == null) {
                    hashExpenseResultList = new HashMap<>();
                }

                if (datumList.size() > 0) {
                    if (!isNetworkConnected()) {
                        if (hashExpenseResultList != null && hashExpenseResultList.size() > 0 && hashExpenseResultList.containsKey(datumList.get(position).getId())) {
                            clearList();
                            myExpenseResultList.addAll(hashExpenseResultList.get(datumList.get(position).getId()));
                            if (myExpenseResultList != null && myExpenseResultList.size() > 0) {
                                setAdapter();
                            } else {
                                setAdapter();
                                showMessage(getString(R.string.internet_not_available));
                            }
                        } else {
                            setAdapter();
                            showMessage(getString(R.string.internet_not_available));
                        }
                    } else {
                        getExpenseList(datumList.get(position).getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MyExpenseAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = myExpenseResultList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final GetExpensePOJO.Datum deletedItem = myExpenseResultList.get(viewHolder.getAdapterPosition());
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

    private void getExpenseList(final Integer status) {
        clearList();
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        JsonClient jsonClient = new JsonClient();
        JSONObject jsonObject = jsonClient.getExpenseStaffJson(logInResList.get(0).getCompId(), logInResList.get(0).getStaffId(),
                0, status, logInResList.get(0).getLoginId(), logInResList.get(0).getToken(), GET_STAFF_EXPENSE);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Call<GetExpensePOJO> call = restClientAPI.getExpense(body);
        call.enqueue(new retrofit2.Callback<GetExpensePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetExpensePOJO> call, @NonNull Response<GetExpensePOJO> response) {
                GetExpensePOJO getExpensePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getExpensePOJO != null) {
                        if (getExpensePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getExpensePOJO.getResponseStatus().equals(TRUE)) {
                                myExpenseResultList.addAll(getExpensePOJO.getData());
                                hashExpenseResultList.put(status, myExpenseResultList);
                                fragmentListner.setExpenseResultList(hashExpenseResultList);
                                setAdapter();
                            } else {
                                hideLoading();
                                setAdapter();
                                showMessage(getExpensePOJO.getResponseMessage());
                            }
                        } else {
                            hideLoading();
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
            public void onFailure(@NonNull Call<GetExpensePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    @SuppressLint({"SetTextI18n", "UseSparseArrays"})
    private void showDialogExpenseDetails(int position) {
        listExpenseDetails = new ArrayList<>();

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.expense_detail_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setCanceledOnTouchOutside(false);

        TextView tvExpenseTitle = dialog.findViewById(R.id.tvExpenseTitle);
        TextView tvStaffName = dialog.findViewById(R.id.tvEmpName);
        TextView tvOrderDate = dialog.findViewById(R.id.tvOrderDate);
        TextView tvStaffRemarks = dialog.findViewById(R.id.tvStaffRemarks);
        tvTotalAmount = dialog.findViewById(R.id.tvTotalAmount);
        tvNoResultExpense = dialog.findViewById(R.id.tvNoResult);
        llOrderDetails = dialog.findViewById(R.id.llOrderDetails);

        ImageButton btnClose = dialog.findViewById(R.id.btnClose);
        recyclerView = dialog.findViewById(R.id.recycleView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recycleView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvExpenseTitle.setText(myExpenseResultList.get(position).getName());
        tvStaffName.setText(myExpenseResultList.get(position).getStaffName());
        //tvTotalAmount.setText(String.valueOf(myExpenseResultList.get(position).getTotalAmount()));
        tvOrderDate.setText(String.valueOf(CommonUtils.convertDateStringFormat(myExpenseResultList.get(position).getSubmittedDate())));

        if (myExpenseResultList.get(position).getStaffRemarks() != null) {
            tvStaffRemarks.setText(String.valueOf(myExpenseResultList.get(position).getStaffRemarks()));
        } else {
            tvStaffRemarks.setText("");
        }

        setAdapterExpenseDetails();
        hashExpenseDetails = fragmentListner.getExpenseDetailsResultList();
        if (hashExpenseDetails == null) {
            hashExpenseDetails = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (hashExpenseDetails != null && hashExpenseDetails.size() > 0 && hashExpenseDetails.containsKey(myExpenseResultList.get(position).getExpenseId())) {
                listExpenseDetails.addAll(hashExpenseDetails.get(myExpenseResultList.get(position).getExpenseId()));
                if (listExpenseDetails != null && listExpenseDetails.size() > 0) {
                    setAdapterExpenseDetails();
                } else {
                    setAdapterExpenseDetails();
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                setAdapterExpenseDetails();
                showMessage(getString(R.string.internet_not_available));
            }
        } else if (myExpenseResultList.size() > 0) {
            getExpenseDetails(myExpenseResultList.get(position).getExpenseId(), myExpenseResultList.get(position).getStatus());
        }

        dialog.show();
    }

    private void setAdapterExpenseDetails() {
        if (listExpenseDetails != null && listExpenseDetails.size() > 0) {
            setTotalAmount();
            tvNoResultExpense.setVisibility(View.GONE);
            llOrderDetails.setVisibility(View.VISIBLE);
            ExpenseDetailsAdapter adapter = new ExpenseDetailsAdapter(getContext(), listExpenseDetails, setListSize);
            recyclerView.setAdapter(adapter);
        } else {
            tvNoResultExpense.setVisibility(View.VISIBLE);
            llOrderDetails.setVisibility(View.GONE);
        }
    }

    public void setTotalAmount() {
        double totalPrice = 0;
        tvTotalAmount.setText(null);
        for (int i = 0; i < listExpenseDetails.size(); i++) {
            totalPrice += listExpenseDetails.get(i).getBillAmount();
        }
        tvTotalAmount.setText(String.valueOf(totalPrice));
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
                        if (getExpensePOJO.getResponseCode().equals(API_RESPONSE_CODE_PUNCH_IN_OUT)) {
                            if (getExpensePOJO.getResponseStatus().equals(TRUE)) {
                                listExpenseDetails.addAll(getExpensePOJO.getData());
                                hashExpenseDetails.put(expenseId, listExpenseDetails);
                                fragmentListner.setExpenseDetailsResultList(hashExpenseDetails);
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
                        setAdapterExpenseDetails();
                    }

                } else {
                    setAdapterExpenseDetails();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetExpenseDetailsPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setAdapterExpenseDetails();
                showMessage(getString(R.string.internet_not_available));
            }
        });
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

    private void clearList() {
        if (myExpenseResultList != null && myExpenseResultList.size() > 0) {
            myExpenseResultList.clear();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setValuesForGetListExpense();
    }

    @Override
    public void showDialogDetails(int position) {
        showDialogExpenseDetails(position);
    }

    @Override
    public void openEditExpenseActivity(int position) {
        if (myExpenseResultList.get(position).getStatus() == 0 || myExpenseResultList.get(position).getStatus() == 3) {
            if (myExpenseResultList != null && myExpenseResultList.size() > 0) {
                Intent intent = new Intent(getContext(), AddExpensesActivity.class);
                intent.putExtra("EXPENSE_TITLE", myExpenseResultList.get(position).getName());
                intent.putExtra("STAFF_EXPENSE_REMARKS", myExpenseResultList.get(position).getStaffRemarks());
                intent.putExtra("EXPENSE_ID", myExpenseResultList.get(position).getExpenseId());
                intent.putExtra("STATUS", myExpenseResultList.get(position).getStatus());
                intent.putExtra("TAG", "EDIT");
                startActivityForResult(intent, 100);
            }
        }
    }
}