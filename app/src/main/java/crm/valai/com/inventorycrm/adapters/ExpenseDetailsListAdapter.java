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
import crm.valai.com.inventorycrm.interfaces.SetListSize;
import crm.valai.com.inventorycrm.modals.GetExpenseDetailsPOJO;

/**
 * @author by Mohit Arora on 27/3/18.
 */
public class ExpenseDetailsListAdapter extends RecyclerView.Adapter<ExpenseDetailsListAdapter.MyViewHolder> {

    private List<GetExpenseDetailsPOJO.Datum> getExpenseDetailList;
    private Context ctx;
    private SetListSize setListSize;

    public ExpenseDetailsListAdapter(Context ctx, List<GetExpenseDetailsPOJO.Datum> getExpenseDetailList, SetListSize setListSize) {
        this.getExpenseDetailList = getExpenseDetailList;
        this.ctx = ctx;
        this.setListSize = setListSize;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;

        @BindView(R.id.tvExpenseType)
        TextView tvExpenseType;

        @BindView(R.id.tvDate)
        TextView tvDate;

        @BindView(R.id.tvLocation)
        TextView tvLocation;

        @BindView(R.id.tvAmount)
        TextView tvAmount;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_details_list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvExpenseType.setTag(position);
        holder.tvDate.setTag(position);
        holder.tvLocation.setTag(position);
        holder.tvAmount.setTag(position);
        holder.tvExpenseType.setText(String.valueOf(getExpenseDetailList.get(position).getExpenseCategoryName()));
        if (!getExpenseDetailList.get(position).getFromDate().equals("") && !getExpenseDetailList.get(position).getToDate().equals("")) {

            if(getExpenseDetailList.get(position).getToDate().equals("01/01/1900") && getExpenseDetailList.get(position).getFromDate().equals("01/01/1900")){
                holder.tvDate.setText(null);
            }else if(getExpenseDetailList.get(position).getToDate().equals("01/01/1900")){
                holder.tvDate.setText(String.valueOf(getExpenseDetailList.get(position).getFromDate()));
            }else if(getExpenseDetailList.get(position).getFromDate().equals("01/01/1900")){
                holder.tvDate.setText(null);
            }else{
                holder.tvDate.setText(String.valueOf(getExpenseDetailList.get(position).getFromDate()) + "\n" + "To" + "\n" +
                        String.valueOf(getExpenseDetailList.get(position).getToDate()));
            }

        } else if (!getExpenseDetailList.get(position).getFromDate().equals("")) {
            if(getExpenseDetailList.get(position).getFromDate().equals("01/01/1900")){
                holder.tvDate.setText(null);
            }else{
                holder.tvDate.setText(String.valueOf(getExpenseDetailList.get(position).getFromDate()));
            }
        } else {
            holder.tvDate.setText(null);
        }

        if (!getExpenseDetailList.get(position).getFromLocation().equals("") && !getExpenseDetailList.get(position).getToLocation().equals("")) {
            holder.tvLocation.setText(String.valueOf(getExpenseDetailList.get(position).getFromLocation()) + "\n" + "To" + "\n" +
                    String.valueOf(getExpenseDetailList.get(position).getToLocation()));
        } else if (!getExpenseDetailList.get(position).getFromLocation().equals("")) {
            holder.tvLocation.setText(String.valueOf(getExpenseDetailList.get(position).getFromLocation()));
        } else {
            holder.tvLocation.setText(null);
        }

        holder.tvAmount.setText(String.valueOf(getExpenseDetailList.get(position).getBillAmount()));
    }

    @Override
    public int getItemCount() {
        return getExpenseDetailList.size();
    }

    public void removeItem(int position) {
        getExpenseDetailList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        setListSize.setListSize(getExpenseDetailList.size());
        notifyItemRemoved(position);
    }

    public void restoreItem(GetExpenseDetailsPOJO.Datum item, int position) {
        getExpenseDetailList.add(position, item);
        // notify item added by position
        setListSize.setListSize(getExpenseDetailList.size());
        notifyItemInserted(position);
    }
}