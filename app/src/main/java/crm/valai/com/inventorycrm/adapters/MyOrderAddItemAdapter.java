package crm.valai.com.inventorycrm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.MyOrderSubList;
import crm.valai.com.inventorycrm.modals.GetItemPOJO;
import crm.valai.com.inventorycrm.modals.GetQuantityAndRatePOJO;
import crm.valai.com.inventorycrm.modals.MyOrderResultPOJO;


public class MyOrderAddItemAdapter extends BaseExpandableListAdapter {

    private Context context;
    private MyOrderSubList myOrderSubList;
    private List<GetItemPOJO.Datum> _listDataHeader;
    private HashMap<String, List<GetQuantityAndRatePOJO.Datum>> _listDataChild;
    private String branchName;
    private String customerName;
    private String clusterName;
    private String category;
    private EditText edQtyText;

    public MyOrderAddItemAdapter(Context context, List<GetItemPOJO.Datum> listDataHeaderItem, HashMap<String,
            List<GetQuantityAndRatePOJO.Datum>> listDataChildItem, MyOrderSubList myOrderSubList, String branchName,
                                 String customerName, String clusterName, String category) {
        this.context = context;
        this._listDataHeader = listDataHeaderItem;
        this._listDataChild = listDataChildItem;
        this.myOrderSubList = myOrderSubList;
        this.branchName = branchName;
        this.customerName = customerName;
        this.clusterName = clusterName;
        this.category = category;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getItemName()).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final GetQuantityAndRatePOJO.Datum child = (GetQuantityAndRatePOJO.Datum) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            if (mLayoutInflater != null) {
                convertView = mLayoutInflater.inflate(R.layout.order_add_item, null);
            }
        }

        final Spinner spinner = convertView.findViewById(R.id.spinnerMtPCS);
        myOrderSubList.addMtPcsSpinner(context, spinner);

        TextView tvdBPriceInMt = convertView.findViewById(R.id.DBpriceMT);
        TextView tvdBPriceInPcs = convertView.findViewById(R.id.DBpricePCS);
        TextView tvdBQtyInMt = convertView.findViewById(R.id.DBQtyMT);
        TextView tvdBQtyInPcs = convertView.findViewById(R.id.DBQtyPcs);
        final EditText edQtyText = convertView.findViewById(R.id.edtxtaddorderqty);

        tvdBPriceInMt.setText(String.valueOf(child.getPriceMT()) + " " + context.getString(R.string.rupees));
        tvdBPriceInPcs.setText(String.valueOf(child.getPricePCS()) + " " + context.getString(R.string.rupees));
        tvdBQtyInMt.setText(String.valueOf(child.getQuantityMT()) + " " + context.getString(R.string.kgs));
        tvdBQtyInPcs.setText(String.valueOf(child.getQuantityPCS() + " " + context.getString(R.string.pcs_text)));
        ImageButton buttonAdd = convertView.findViewById(R.id.btnAddItemOrder);

//        if (child.getQuantity() != null) {
//            edQtyText.setText(String.valueOf(child.getQuantity()));
//        } else {
//            edQtyText.setText(null);
//        }

        edQtyText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                try {
                    if (spinner.getSelectedItem().equals(context.getString(R.string.mt))) {
                        if (s.length() > 0) {
                            child.setQuantity(Double.parseDouble(s.toString()));
                            Double totalAmount = Double.parseDouble(s.toString()) * child.getPriceMT();
                            child.setTotalAmount(totalAmount);
                        }
                    } else {
                        if (s.length() > 0) {
                            child.setQuantity(Double.parseDouble(s.toString()));
                            Double totalAmount = Double.parseDouble(s.toString()) * child.getPricePCS();
                            child.setTotalAmount(totalAmount);
                        }
                    }
                } catch (NumberFormatException ex) { // handle your exception
                    ex.getMessage();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (edQtyText.getText().toString().equals("")) {
                    myOrderSubList.hideKeyBoards();
                    myOrderSubList.showMessages(context.getString(R.string.select_quantity));
                    return;
                }

                if (Double.parseDouble(edQtyText.getText().toString()) <= 0) {
                    myOrderSubList.hideKeyBoards();
                    myOrderSubList.showMessages(context.getString(R.string.select_quantity));
                    return;
                }

                if (spinner.getSelectedItem().equals(context.getString(R.string.mt))) {
                    if (child.getQuantity() > child.getQuantityMT()) {
                        myOrderSubList.hideKeyBoards();
                        myOrderSubList.showMessages(context.getString(R.string.ordered_quantity_mt_not_available));
                        return;
                    }
                }

                if (spinner.getSelectedItem().equals(context.getString(R.string.pcs))) {
                    if (child.getQuantity() > child.getQuantityPCS()) {
                        myOrderSubList.hideKeyBoards();
                        myOrderSubList.showMessages(context.getString(R.string.ordered_quantity_pcs_not_available));
                        return;
                    }
                }

                myOrderSubList.hideKeyBoards();
                MyOrderResultPOJO resultPOJO = new MyOrderResultPOJO();
                resultPOJO.setBranchName(branchName);
                resultPOJO.setCustomerName(customerName);
                resultPOJO.setClusterName(clusterName);
                resultPOJO.setCategory(category);
                resultPOJO.setItemName(_listDataHeader.get(groupPosition).getItemName());
                resultPOJO.setUom(spinner.getSelectedItem().toString());
                resultPOJO.setQuantityInMt(child.getQuantityMT());
                resultPOJO.setRateInMt(child.getPriceMT());
                resultPOJO.setQuantityInPcs(child.getQuantityPCS());
                resultPOJO.setRateInPcs(child.getPricePCS());
                resultPOJO.setQuantity(child.getQuantity());
                resultPOJO.setTotalAmount(child.getTotalAmount());
                resultPOJO.setItemId(_listDataHeader.get(groupPosition).getItemId());
                myOrderSubList.saveResultList(resultPOJO);
                edQtyText.setText(null);
                child.setQuantity(null);
                notifyDataSetChanged();
                myOrderSubList.showMessages(context.getString(R.string.total_amount) + String.valueOf(child.getTotalAmount()));

            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getItemName()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getItemName();
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);

            if (inf != null) {
                convertView = inf.inflate(R.layout.order_selecteditemgroup, null);
            }
        }
        TextView tv = null;
        if (convertView != null) {
            tv = convertView.findViewById(R.id.lblListItemAsgroupheaders);
        }
        if (tv != null) {
            tv.setText(headerTitle);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}