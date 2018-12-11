package crm.valai.com.inventorycrm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.modals.GetOrderDetailPOJO;

/**
 * @author by Mohit Arora on 27/3/18.
 */
public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {

    private List<GetOrderDetailPOJO.Datum> getOrderDetailList;
    private Context ctx;

    public OrderDetailsAdapter(Context ctx, List<GetOrderDetailPOJO.Datum> getOrderDetailList) {
        this.getOrderDetailList = getOrderDetailList;
        this.ctx = ctx;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvItemName)
        TextView tvItemName;

        @BindView(R.id.tvQuantity)
        TextView tvQuantity;

        @BindView(R.id.tvUom)
        TextView tvUom;

        @BindView(R.id.tvPrice)
        TextView tvPrice;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_details_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvItemName.setTag(position);
        holder.tvQuantity.setTag(position);
        holder.tvUom.setTag(position);
        holder.tvPrice.setTag(position);
        holder.tvItemName.setText(getOrderDetailList.get(position).getItemName());
        holder.tvQuantity.setText(String.valueOf(getOrderDetailList.get(position).getQuantity()));
        holder.tvUom.setText(String.valueOf(getOrderDetailList.get(position).getUom()));
        holder.tvPrice.setText(String.valueOf(getOrderDetailList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return getOrderDetailList.size();
    }
}