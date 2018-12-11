package crm.valai.com.inventorycrm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.modals.GetStaffDailyTrackerPOJO;

/**
 * @author by Mohit Arora on 27/3/18.
 */
public class SalesDetailsListAdapter extends RecyclerView.Adapter<SalesDetailsListAdapter.MyViewHolder> {

    private List<GetStaffDailyTrackerPOJO.Datum> myOrderBillModalList;
    private Context ctx;

    public SalesDetailsListAdapter(Context ctx, List<GetStaffDailyTrackerPOJO.Datum> myOrderBillModalList) {
        this.myOrderBillModalList = myOrderBillModalList;
        this.ctx = ctx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;

        @BindView(R.id.itemMode)
        TextView itemMode;

        @BindView(R.id.itemPlaceVisited)
        TextView itemPlaceVisited;

        @BindView(R.id.itemCustomerName)
        TextView itemCustomerName;

        @BindView(R.id.itemPurposeVisit)
        TextView itemPurposeVisit;

        @BindView(R.id.itemResult)
        TextView itemResult;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_details_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemPlaceVisited.setText(myOrderBillModalList.get(position).getLocationName());
        holder.itemCustomerName.setText(myOrderBillModalList.get(position).getCustomerName());

        if (myOrderBillModalList.get(position).getSales() != 0 && myOrderBillModalList.get(position).getCollectionInRs() != 0) {
            holder.itemResult.setText(String.valueOf(myOrderBillModalList.get(position).getSales()) + ctx.getString(R.string.mt) + "\n" +
                    String.valueOf(myOrderBillModalList.get(position).getCollectionInRs()) + ctx.getString(R.string.rupees));
        } else if (myOrderBillModalList.get(position).getSales() != 0) {
            holder.itemResult.setText(String.valueOf(myOrderBillModalList.get(position).getSales()) + ctx.getString(R.string.mt));
        } else if (myOrderBillModalList.get(position).getCollectionInRs() != 0) {
            holder.itemResult.setText(String.valueOf(myOrderBillModalList.get(position).getCollectionInRs()) + ctx.getString(R.string.rupees));
        } else {
            holder.itemResult.setText(null);
        }

        if (myOrderBillModalList.get(position).getModeOfCommunication() == 1) {
            holder.itemMode.setText("Telephone");
        } else {
            holder.itemMode.setText("Visit");
        }

        if (myOrderBillModalList.get(position).getPurposeOfVisit().equals("1")) {
            holder.itemPurposeVisit.setText("Order Follow Up");
        } else if (myOrderBillModalList.get(position).getPurposeOfVisit().equals("2")) {
            holder.itemPurposeVisit.setText("Payment Follow up");
        } else if (myOrderBillModalList.get(position).getPurposeOfVisit().equals("3")) {
            holder.itemPurposeVisit.setText("New Lead");
        } else if (myOrderBillModalList.get(position).getPurposeOfVisit().equals("4")) {
            holder.itemPurposeVisit.setText("Relationship mgmt");
        } else if (myOrderBillModalList.get(position).getPurposeOfVisit().equals("5")) {
            holder.itemPurposeVisit.setText("Dispute visit - Orders");
        } else if (myOrderBillModalList.get(position).getPurposeOfVisit().equals("6")) {
            holder.itemPurposeVisit.setText("Dispute visit - Payments");
        } else if (myOrderBillModalList.get(position).getPurposeOfVisit().equals("7")) {
            holder.itemPurposeVisit.setText("Others");
        } else {
            holder.itemPurposeVisit.setText(null);
        }
    }

    @Override
    public int getItemCount() {
        return myOrderBillModalList.size();
    }

    public void removeItem(int position) {
        myOrderBillModalList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(GetStaffDailyTrackerPOJO.Datum item, int position) {
        myOrderBillModalList.add(position, item);
        notifyItemInserted(position);
    }
}