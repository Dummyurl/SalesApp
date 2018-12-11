package crm.valai.com.inventorycrm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.modals.GetOrdersPOJO;

/**
 * @author by Mohit Arora on 27/3/18.
 */
public class GetOrderAdapter extends RecyclerView.Adapter<GetOrderAdapter.MyViewHolder> {

    private List<GetOrdersPOJO.Datum> getOrderList;
    private Context ctx;

    public GetOrderAdapter(Context ctx, List<GetOrdersPOJO.Datum> getOrderList) {
        this.getOrderList = getOrderList;
        this.ctx = ctx;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCustomerName)
        TextView tvCustomerName;

        @BindView(R.id.tvOrderRef)
        TextView tvOrderRef;

        @BindView(R.id.tvEmpName)
        TextView tvEmpName;

        @BindView(R.id.tvTotalAmount)
        TextView tvTotalAmount;

        @BindView(R.id.tvStatus)
        TextView tvStatus;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.get_order_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvStatus.setTag(position);
        holder.tvCustomerName.setText(getOrderList.get(position).getCustomerName());
        holder.tvOrderRef.setText(getOrderList.get(position).getOrderRefNumber());
        holder.tvEmpName.setText(getOrderList.get(position).getManager());
        if (getOrderList.get(position).getTotalAmount() != null) {
            holder.tvTotalAmount.setText(String.valueOf(getOrderList.get(position).getTotalAmount()));
        } else {
            holder.tvTotalAmount.setText("");
        }

        if (getOrderList.get(position).getStatus() == 1) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.order_submitted_for_approval));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFB822"));
            holder.tvStatus.setTextColor(Color.BLACK);
        } else if (getOrderList.get(position).getStatus() == 2) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.manager_approved));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#2CA189"));
            holder.tvStatus.setTextColor(Color.WHITE);
        } else if (getOrderList.get(position).getStatus() == 3) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.accounts_approved));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#2CA189"));
            holder.tvStatus.setTextColor(Color.WHITE);
        } else if (getOrderList.get(position).getStatus() == 4) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.order_closed));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#F22D4E"));
            holder.tvStatus.setTextColor(Color.WHITE);
        } else if (getOrderList.get(position).getStatus() == -2) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.manager_rejected));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#F22D4E"));
            holder.tvStatus.setTextColor(Color.WHITE);
        } else if (getOrderList.get(position).getStatus() == -3) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.accounts_rejected));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#F22D4E"));
            holder.tvStatus.setTextColor(Color.WHITE);
        } else {
            holder.tvStatus.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return getOrderList.size();
    }
}