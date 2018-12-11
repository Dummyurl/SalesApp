package crm.valai.com.inventorycrm.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.FragmentListner;
import crm.valai.com.inventorycrm.modals.CustomerModel;
import crm.valai.com.inventorycrm.modals.GetCustomerDetailsPOJO;

import static crm.valai.com.inventorycrm.fragments.EditCustomerDialogue.viewPagerChangeInterface;

/**
 * @author by Mohit Arora on 31/3/18.
 */
public class EditCustomerDetailsOne extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;
    private FragmentListner fragmentListner;

    private String mParam1;
    private String mParam2;

    @BindView(R.id.edtCustomerName)
    EditText edtCustomerName;

    @BindView(R.id.edtCustomerPhoneNo)
    EditText edtCustomerPhoneNo;

    @BindView(R.id.edtCustomerEmail)
    EditText edtCustomerEmail;

    @BindView(R.id.edtContactPersonName)
    EditText edtContactPersonName;

    @BindView(R.id.edtContactPersonNameDesignation)
    EditText edtContactPersonNameDesignation;

    @BindView(R.id.edtContactPersonNumber)
    EditText edtContactPersonNumber;

    @BindView(R.id.edtContactPersonEmail)
    EditText edtContactPersonEmail;

    private CustomerModel customerModel;
    private GetCustomerDetailsPOJO.Datum dataCustomer;

    public EditCustomerDetailsOne() {
        // Required empty public constructor
    }

    public static EditCustomerDetailsOne newInstance(CustomerModel customerModel, GetCustomerDetailsPOJO.Datum dataCustomer) {
        EditCustomerDetailsOne fragment = new EditCustomerDetailsOne();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, customerModel);
        args.putSerializable(ARG_PARAM2, dataCustomer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customerModel = (CustomerModel) getArguments().getSerializable(ARG_PARAM1);
            dataCustomer = (GetCustomerDetailsPOJO.Datum) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.add_customer_details_one, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtCustomerName.setText(dataCustomer.getCustomerName());
        edtCustomerPhoneNo.setText(dataCustomer.getCustomerPhoneNumber());
        edtCustomerEmail.setText(dataCustomer.getCustomerEmail());
        edtContactPersonName.setText(dataCustomer.getContactPersonName());
        edtContactPersonNameDesignation.setText(dataCustomer.getContactPersonDesignation());
        edtContactPersonNumber.setText(dataCustomer.getContactPersonMobileNumber());
        edtContactPersonEmail.setText(dataCustomer.getContactPersonEmail());
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
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @OnClick(R.id.btnNext)
    public void onNextClick() {
        customerModel.setCustomerName(edtCustomerName.getText().toString());
        customerModel.setCustomerPhoneNumber(edtCustomerPhoneNo.getText().toString());
        customerModel.setCustomerEmail(edtCustomerEmail.getText().toString());
        customerModel.setContactPersonName(edtContactPersonName.getText().toString());
        customerModel.setContactPersonNameDesignation(edtContactPersonNameDesignation.getText().toString());
        customerModel.setContactPersonNumber(edtContactPersonNumber.getText().toString());
        customerModel.setContactPersonEmail(edtContactPersonEmail.getText().toString());
        viewPagerChangeInterface.onRightMoveClick();
    }
}