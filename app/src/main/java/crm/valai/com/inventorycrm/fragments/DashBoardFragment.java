package crm.valai.com.inventorycrm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;

public class DashBoardFragment extends BaseFragment {
    public static final String TAG = DashBoardFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentListner fragmentListner;

    @BindView(R.id.chart)
    HorizontalBarChart barChart;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBarChartData();
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
    }


    public void setBarChartData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 0));
        entries.add(new BarEntry(240.9f, 1));
        entries.add(new BarEntry(800f, 2));
        BarDataSet dataset = new BarDataSet(entries, "My Sales vs Target in MT");
        dataset.setValueTextSize(12f);
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("");
        labels.add("Sales");
        labels.add("Target");
        BarData data = new BarData(labels, dataset);

        barChart.setData(data);
        barChart.setScaleMinima(0f, 0f);
        barChart.setDescription("Month of December");
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawLimitLinesBehindData(false);
        barChart.animateY(5000);
    }

    @OnClick(R.id.customers)
    public void onMyCustomersClick() {
        fragmentListner.setOnLoadHome(1, MyCustomersFragment.TAG);
    }

    @OnClick(R.id.orders)
    public void onMyOrdersClick() {
        fragmentListner.setOnLoadHome(2, PlaceOrdersFragment.TAG);
        //fragmentListner.setOnLoadHome(2, GetOrdersFragment.TAG);
    }

    @OnClick(R.id.sales)
    public void onMySalesClick() {
        fragmentListner.setOnLoadHome(3, MySalesFragment.TAG);
    }

    @OnClick(R.id.leave)
    public void onMyLeavesClick() {
        fragmentListner.setOnLoadHome(4, MyLeavesFragment.TAG);
    }

    @OnClick(R.id.expense)
    public void onMyExpensesClick() {
        fragmentListner.setOnLoadHome(5, MyExpensesFragment.TAG);
    }
}