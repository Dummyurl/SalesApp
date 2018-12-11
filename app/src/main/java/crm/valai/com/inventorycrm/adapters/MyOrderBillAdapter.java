package crm.valai.com.inventorycrm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.SetListSize;
import crm.valai.com.inventorycrm.modals.MyOrderResultPOJO;

/**
 * @author by Mohit Arora on 27/3/18.
 */
public class MyOrderBillAdapter extends RecyclerView.Adapter<MyOrderBillAdapter.MyViewHolder> {

    private List<MyOrderResultPOJO> myOrderBillModalList;
    private Context ctx;
    private SetListSize setListSize;
    private Integer inventoryClassificationId;
    private HashMap<Integer, List<MyOrderResultPOJO>> myOrderResultHashMap;
    private DecimalFormat decimalFormat;

    public MyOrderBillAdapter(Context ctx, List<MyOrderResultPOJO> myOrderBillModalList, SetListSize setListSize, HashMap<Integer, List<MyOrderResultPOJO>> myOrderResultHashMap, Integer inventoryClassificationId) {
        this.myOrderBillModalList = myOrderBillModalList;
        this.ctx = ctx;
        this.setListSize = setListSize;
        this.inventoryClassificationId = inventoryClassificationId;
        this.myOrderResultHashMap = myOrderResultHashMap;
        decimalFormat = new DecimalFormat("#.##");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;

        @BindView(R.id.itemName)
        TextView itemName;

        @BindView(R.id.itemQnt)
        TextView itemQnt;

        @BindView(R.id.itemUnit)
        TextView itemUnit;

        @BindView(R.id.itemPrice)
        TextView itemPrice;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_bill_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // holder.btnDelete.setTag(position);
        MyOrderResultPOJO myOrderBillModal = myOrderBillModalList.get(position);
        holder.itemName.setText(myOrderBillModal.getItemName());
        holder.itemQnt.setText(String.valueOf(decimalFormat.format(myOrderBillModal.getQuantity())));
        holder.itemUnit.setText(String.valueOf(myOrderBillModal.getUom()));
        holder.itemPrice.setText(String.valueOf(myOrderBillModal.getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        return myOrderBillModalList.size();
    }

    public void removeItem(int position) {
        myOrderBillModalList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        setListSize.setListSize(myOrderBillModalList.size());
        //myOrderResultHashMap.put(inventoryClassificationId, myOrderBillModalList);
        //setListSize.setMyOrderResultList(myOrderResultHashMap);
        setListSize.setMyOrderResultListNew(myOrderBillModalList);
        notifyItemRemoved(position);
    }

    public void restoreItem(MyOrderResultPOJO item, int position) {
        myOrderBillModalList.add(position, item);
        // notify item added by position
        setListSize.setListSize(myOrderBillModalList.size());
        //myOrderResultHashMap.put(inventoryClassificationId, myOrderBillModalList);
        //setListSize.setMyOrderResultList(myOrderResultHashMap);
        setListSize.setMyOrderResultListNew(myOrderBillModalList);
        notifyItemInserted(position);
    }
}