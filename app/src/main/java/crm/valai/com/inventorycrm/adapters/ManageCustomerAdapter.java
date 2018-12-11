package crm.valai.com.inventorycrm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.GetCustomers;
import crm.valai.com.inventorycrm.modals.GetCustomerDetailsPOJO;

/**
 * @author by Mohit Arora on 27/3/18.
 */

public class ManageCustomerAdapter extends RecyclerView.Adapter<ManageCustomerAdapter.MyViewHolder> {

    private List<GetCustomerDetailsPOJO.Datum> listCustomerDetails;
    private Context ctx;
    private GetCustomers getCustomers;

    public ManageCustomerAdapter(Context ctx, List<GetCustomerDetailsPOJO.Datum> listCustomerDetails, GetCustomers getCustomers) {
        this.listCustomerDetails = listCustomerDetails;
        this.ctx = ctx;
        this.getCustomers = getCustomers;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCustomerName)
        TextView tvCustomerName;

        @BindView(R.id.tvContactPerson)
        TextView tvContactPerson;

        @BindView(R.id.tvMobileNumber)
        TextView tvMobileNumber;

        @BindView(R.id.tvStatus)
        TextView tvStatus;

        @BindView(R.id.imgEdit)
        ImageButton imgEdit;

        @BindView(R.id.rlEdit)
        RelativeLayout rlEdit;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.tvCustomerName)
        void onCustomerNameClickForEdit(View view) {
            getCustomers.openEditCustomerActivity(Integer.parseInt(view.getTag().toString()));
        }

        @OnClick(R.id.imgEdit)
        void onCustomerImageButtonClickForEdit(View view) {
            getCustomers.openEditCustomerActivity(Integer.parseInt(view.getTag().toString()));
        }

        @OnClick(R.id.rlEdit)
        void onRelativeClickForEdit(View view) {
            getCustomers.openEditCustomerActivity(Integer.parseInt(view.getTag().toString()));
        }

        @OnClick(R.id.tvContactPerson)
        void onContactPersonClick(View view) {
            getCustomers.showDialogDetails(Integer.parseInt(view.getTag().toString()));
        }

        @OnClick(R.id.tvMobileNumber)
        void onMobileNumberClick(View view) {
            getCustomers.showDialogDetails(Integer.parseInt(view.getTag().toString()));
        }

        @OnClick(R.id.tvStatus)
        void onStatusClick(View view) {
            getCustomers.showDialogDetails(Integer.parseInt(view.getTag().toString()));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_customer_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvCustomerName.setTag(position);
        holder.tvContactPerson.setTag(position);
        holder.tvMobileNumber.setTag(position);
        holder.tvStatus.setTag(position);
        holder.imgEdit.setTag(position);
        holder.rlEdit.setTag(position);

        holder.tvCustomerName.setText(listCustomerDetails.get(position).getCustomerName());
        holder.tvContactPerson.setText(listCustomerDetails.get(position).getContactPersonName());
        holder.tvMobileNumber.setText(listCustomerDetails.get(position).getContactPersonMobileNumber());

        if (listCustomerDetails.get(position).getStatus() == 0) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.customer_created));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#840AD9"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.VISIBLE);
            holder.tvCustomerName.setClickable(true);
            holder.tvCustomerName.setFocusable(true);
            holder.imgEdit.setClickable(true);
            holder.imgEdit.setFocusable(true);
            holder.rlEdit.setClickable(true);
            holder.rlEdit.setFocusable(true);
        } else if (listCustomerDetails.get(position).getStatus() == 1) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.order_submitted_for_approval));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFB822"));
            holder.tvStatus.setTextColor(Color.BLACK);
            holder.imgEdit.setVisibility(View.GONE);
            holder.tvCustomerName.setClickable(false);
            holder.tvCustomerName.setFocusable(false);
            holder.imgEdit.setClickable(false);
            holder.imgEdit.setFocusable(false);
            holder.rlEdit.setClickable(false);
            holder.rlEdit.setFocusable(false);
        } else if (listCustomerDetails.get(position).getStatus() == 2) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.approved));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#2CA189"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.GONE);
            holder.tvCustomerName.setClickable(false);
            holder.tvCustomerName.setFocusable(false);
            holder.imgEdit.setClickable(false);
            holder.imgEdit.setFocusable(false);
            holder.rlEdit.setClickable(false);
            holder.rlEdit.setFocusable(false);
        } else if (listCustomerDetails.get(position).getStatus() == 3) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.needs_modification));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#564EC0"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.VISIBLE);
            holder.tvCustomerName.setClickable(true);
            holder.tvCustomerName.setFocusable(true);
            holder.imgEdit.setClickable(true);
            holder.imgEdit.setFocusable(true);
            holder.rlEdit.setClickable(true);
            holder.rlEdit.setFocusable(true);
        } else if (listCustomerDetails.get(position).getStatus() == 4) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.rejected));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#F22D4E"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.GONE);
            holder.tvCustomerName.setClickable(false);
            holder.tvCustomerName.setFocusable(false);
            holder.imgEdit.setClickable(false);
            holder.imgEdit.setFocusable(false);
            holder.rlEdit.setClickable(false);
            holder.rlEdit.setFocusable(false);
        } else {
            holder.tvStatus.setVisibility(View.INVISIBLE);
            holder.imgEdit.setVisibility(View.GONE);
            holder.tvCustomerName.setClickable(false);
            holder.tvCustomerName.setFocusable(false);
            holder.imgEdit.setClickable(false);
            holder.imgEdit.setFocusable(false);
            holder.rlEdit.setClickable(false);
            holder.rlEdit.setFocusable(false);
        }
    }

    @Override
    public int getItemCount() {
        return listCustomerDetails.size();
    }

    public void removeItem(int position) {
        listCustomerDetails.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(GetCustomerDetailsPOJO.Datum item, int position) {
        listCustomerDetails.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}