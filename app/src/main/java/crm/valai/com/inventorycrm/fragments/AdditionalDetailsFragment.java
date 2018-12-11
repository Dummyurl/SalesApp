package crm.valai.com.inventorycrm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.interfaces.ViewPagerChangeInterface;

/**
 * @author by Mohit Arora on 31/3/18.
 */
public class AdditionalDetailsFragment extends BaseFragment implements ViewPagerChangeInterface {
    public static final String TAG = AdditionalDetailsFragment.class.getSimpleName();

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.tvNoResult)
    TextView tvNoResult;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentListner fragmentListner;
    public static ViewPagerChangeInterface viewPagerChangeInterface;
    private int pagerCount;
    private Integer customerId;

    public AdditionalDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    customerId = fragmentListner.getAppPreferenceHelper().getCustomerId();
                    if (customerId != -1) {
                        viewpager.setOffscreenPageLimit(3);
                        setupViewPager(viewpager);
                        tabs.setupWithViewPager(viewpager);
                    } else {
                        viewpager.setVisibility(View.GONE);
                        tvNoResult.setVisibility(View.VISIBLE);
                    }
                }
            }, 500);
        }
    }

    public static AdditionalDetailsFragment newInstance(String param1, String param2) {
        AdditionalDetailsFragment fragment = new AdditionalDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewPagerChangeInterface = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_target, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        adapter.addFragment(new AddressCustomersFragment(), getString(R.string.address_text));
        adapter.addFragment(new TargetFragment(), getString(R.string.target_text));
        adapter.addFragment(new DocumentsFragment(), getString(R.string.documents_text));
        viewPager.setAdapter(adapter);
        pagerCount = adapter.getCount();
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
    }

    @Override
    public void getCustomersCall() {
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
    public void onDetach() {
        super.onDetach();
    }

}