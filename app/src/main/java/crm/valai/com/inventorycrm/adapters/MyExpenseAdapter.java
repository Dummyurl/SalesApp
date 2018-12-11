package crm.valai.com.inventorycrm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.interfaces.MyExpenseEditInterface;
import crm.valai.com.inventorycrm.modals.GetExpensePOJO;
import crm.valai.com.inventorycrm.modals.MyOrderResultPOJO;
import crm.valai.com.inventorycrm.utils.CommonUtils;

/**
 * @author by Mohit Arora on 27/3/18.
 */
public class MyExpenseAdapter extends RecyclerView.Adapter<MyExpenseAdapter.MyViewHolder> {

    private List<GetExpensePOJO.Datum> myExpenseResultList;
    private Context ctx;
    private MyExpenseEditInterface myExpenseEditInterface;

    public MyExpenseAdapter(Context ctx, List<GetExpensePOJO.Datum> myExpenseResultList, MyExpenseEditInterface myExpenseEditInterface) {
        this.myExpenseResultList = myExpenseResultList;
        this.ctx = ctx;
        this.myExpenseEditInterface = myExpenseEditInterface;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;

        @BindView(R.id.tvExpenseTitle)
        TextView tvExpenseTitle;

        @BindView(R.id.tvTotalAmount)
        TextView tvTotalAmount;

        @BindView(R.id.tvSubmittedDate)
        TextView tvSubmittedDate;

        @BindView(R.id.tvStatus)
        TextView tvStatus;

        @BindView(R.id.imgEdit)
        ImageButton imgEdit;

        @BindView(R.id.rlEdit)
        RelativeLayout rlEdit;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            viewForeground = view.findViewById(R.id.view_foreground);
        }

        @OnClick(R.id.tvExpenseTitle)
        void onExpenseTitleClickForEdit(View view) {
            myExpenseEditInterface.openEditExpenseActivity(Integer.parseInt(view.getTag().toString()));
        }

        @OnClick(R.id.imgEdit)
        void onExpenseImageButtonClickForEdit(View view) {
            myExpenseEditInterface.openEditExpenseActivity(Integer.parseInt(view.getTag().toString()));
        }

        @OnClick(R.id.rlEdit)
        void onRelativeClickForEdit(View view) {
            myExpenseEditInterface.openEditExpenseActivity(Integer.parseInt(view.getTag().toString()));
        }

        @OnClick(R.id.view_foreground)
        void onViewEditClick(View view) {
            myExpenseEditInterface.showDialogDetails(Integer.parseInt(view.getTag().toString()));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_expense_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvExpenseTitle.setTag(position);
        holder.tvTotalAmount.setTag(position);
        holder.tvSubmittedDate.setTag(position);
        holder.tvStatus.setTag(position);
        holder.viewForeground.setTag(position);
        holder.imgEdit.setTag(position);
        holder.rlEdit.setTag(position);
        holder.tvExpenseTitle.setText(myExpenseResultList.get(position).getName());
        holder.tvTotalAmount.setText(String.valueOf(myExpenseResultList.get(position).getTotalAmount()));
        holder.tvSubmittedDate.setText(CommonUtils.convertDateStringFormat2(myExpenseResultList.get(position).getSubmittedDate()));

        if (myExpenseResultList.get(position).getStatus() == 0) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.expense_created));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#840AD9"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.VISIBLE);
            holder.tvExpenseTitle.setClickable(true);
            holder.tvExpenseTitle.setFocusable(true);
            holder.imgEdit.setClickable(true);
            holder.imgEdit.setFocusable(true);
            holder.rlEdit.setClickable(true);
            holder.rlEdit.setFocusable(true);
        } else if (myExpenseResultList.get(position).getStatus() == 1) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.order_submitted_for_approval));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFB822"));
            holder.tvStatus.setTextColor(Color.BLACK);
            holder.imgEdit.setVisibility(View.GONE);
            holder.tvExpenseTitle.setClickable(false);
            holder.tvExpenseTitle.setFocusable(false);
            holder.imgEdit.setClickable(false);
            holder.imgEdit.setFocusable(false);
            holder.rlEdit.setClickable(false);
            holder.rlEdit.setFocusable(false);
        } else if (myExpenseResultList.get(position).getStatus() == 2) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.approved));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#2CA189"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.GONE);
            holder.tvExpenseTitle.setClickable(false);
            holder.tvExpenseTitle.setFocusable(false);
            holder.imgEdit.setClickable(false);
            holder.imgEdit.setFocusable(false);
            holder.rlEdit.setClickable(false);
            holder.rlEdit.setFocusable(false);
        } else if (myExpenseResultList.get(position).getStatus() == 3) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.needs_modification));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#564EC0"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.VISIBLE);
            holder.tvExpenseTitle.setClickable(true);
            holder.tvExpenseTitle.setFocusable(true);
            holder.imgEdit.setClickable(true);
            holder.imgEdit.setFocusable(true);
            holder.rlEdit.setClickable(true);
            holder.rlEdit.setFocusable(true);
        } else if (myExpenseResultList.get(position).getStatus() == 4) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.rejected));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#F22D4E"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.GONE);
            holder.tvExpenseTitle.setClickable(false);
            holder.tvExpenseTitle.setFocusable(false);
            holder.imgEdit.setClickable(false);
            holder.imgEdit.setFocusable(false);
            holder.rlEdit.setClickable(false);
            holder.rlEdit.setFocusable(false);
        } else {
            holder.tvStatus.setVisibility(View.INVISIBLE);
            holder.imgEdit.setVisibility(View.GONE);
            holder.tvExpenseTitle.setClickable(false);
            holder.tvExpenseTitle.setFocusable(false);
            holder.imgEdit.setClickable(false);
            holder.imgEdit.setFocusable(false);
            holder.rlEdit.setClickable(false);
            holder.rlEdit.setFocusable(false);
        }
    }

    @Override
    public int getItemCount() {
        return myExpenseResultList.size();
    }

    public void removeItem(int position) {
        myExpenseResultList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(GetExpensePOJO.Datum item, int position) {
        myExpenseResultList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}