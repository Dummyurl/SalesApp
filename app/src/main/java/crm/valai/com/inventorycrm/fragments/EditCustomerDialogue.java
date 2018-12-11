package crm.valai.com.inventorycrm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.activities.BaseDialog;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.interfaces.ViewPagerChangeInterface;
import crm.valai.com.inventorycrm.modals.CustomerModel;
import crm.valai.com.inventorycrm.modals.GetCustomerDetailsPOJO;

import static crm.valai.com.inventorycrm.fragments.ManageCustomersFragment.getCustomers;

/**
 * @author by Mohit Arora on 31/3/18.
 */

public class EditCustomerDialogue extends BaseDialog implements ViewPagerChangeInterface {
    public static final String TAG = EditCustomerDialogue.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar_selected_customers)
    TextView toolbar_selected_customers;

    private FragmentListner fragmentListner;
    private int pagerCount;
    public static ViewPagerChangeInterface viewPagerChangeInterface;
    private CustomerModel customerModel;
    private GetCustomerDetailsPOJO.Datum dataCustomer;
    private Integer customerId;

    public static EditCustomerDialogue newInstance(GetCustomerDetailsPOJO.Datum dataCustomer, Integer customerId) {
        EditCustomerDialogue fragment = new EditCustomerDialogue();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM2, dataCustomer);
        args.putInt(ARG_PARAM3, customerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataCustomer = (GetCustomerDetailsPOJO.Datum) getArguments().getSerializable(ARG_PARAM2);
            customerId = getArguments().getInt(ARG_PARAM3);
        }
        viewPagerChangeInterface = this;
        customerModel = new CustomerModel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_customer_dialogue, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void setUp(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar_title.setText(getString(R.string.edit_customer));
        toolbar_selected_customers.setText(fragmentListner.getTopCustomerName());
        viewpager.setOffscreenPageLimit(5);
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
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

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(EditCustomerDetailsOne.newInstance(customerModel, dataCustomer), getString(R.string.add_text_one_by_four));
        //adapter.addFragment(EditCustomerDetailsTwo.newInstance(customerModel, dataCustomer), getString(R.string.add_text_two_by_four));
        adapter.addFragment(EditCustomerDetailsThree.newInstance(customerModel, dataCustomer), getString(R.string.add_text_two_by_four));
        adapter.addFragment(EditCustomerDetailsFour.newInstance(customerModel, dataCustomer), getString(R.string.add_text_three_by_four));
        adapter.addFragment(EditCustomerDetailsFive.newInstance(customerModel, dataCustomer), getString(R.string.add_text_four_by_four));
        adapter.addFragment(EditCustomerDetailsSix.newInstance(customerModel, dataCustomer, customerId), getString(R.string.add_text_five_by_four));
        viewPager.setAdapter(adapter);
        pagerCount = adapter.getCount();
    }

    private class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onLeftMoveClick() {
        viewpager.setCurrentItem((viewpager.getCurrentItem() < pagerCount)
                ? viewpager.getCurrentItem() - 1 : 0);
    }

    @Override
    public void onRightMoveClick() {
        viewpager.setCurrentItem((viewpager.getCurrentItem() < pagerCount)
                ? viewpager.getCurrentItem() + 1 : 0);
    }

    @Override
    public void onDialogDismiss() {
        dismissDialog(TAG);
    }

    @Override
    public void getCustomersCall() {
        getCustomers.getCustomers();
    }

    @OnClick(R.id.btnBack)
    public void onBackClick() {
        dismissDialog(TAG);
    }
}