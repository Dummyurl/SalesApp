package crm.valai.com.inventorycrm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
import crm.valai.com.inventorycrm.interfaces.SetListSize;
import crm.valai.com.inventorycrm.modals.GetLeaveStatusPOJO;
import crm.valai.com.inventorycrm.utils.CommonUtils;

/**
 * @author by Mohit Arora on 27/3/18.
 */
public class LeaveStatusListAdapter extends RecyclerView.Adapter<LeaveStatusListAdapter.MyViewHolder> {

    private List<GetLeaveStatusPOJO.Datum> getLeaveStatus;
    private Context ctx;
    private SetListSize setListSize;

    public LeaveStatusListAdapter(Context ctx, List<GetLeaveStatusPOJO.Datum> getLeaveStatus, SetListSize setListSize) {
        this.getLeaveStatus = getLeaveStatus;
        this.ctx = ctx;
        this.setListSize = setListSize;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewForeground;

        @BindView(R.id.tvExpenseType)
        TextView tvLeaveType;

        @BindView(R.id.tvDate)
        TextView tvDate;

        @BindView(R.id.tvLocation)
        TextView tvLeaveCount;

        @BindView(R.id.tvAmount)
        TextView tvRemarks;

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

        @OnClick(R.id.tvExpenseType)
        void onLeaveTypeClickForEdit(View view) {
            Log.e("Edit", "Edit");
        }

        @OnClick(R.id.imgEdit)
        void onLeaveImageButtonClickForEdit(View view) {

        }

        @OnClick(R.id.rlEdit)
        void onRelativeClickForEdit(View view) {

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvLeaveType.setTag(position);
        holder.tvDate.setTag(position);
        holder.tvLeaveCount.setTag(position);
        holder.tvRemarks.setTag(position);
        holder.tvStatus.setTag(position);
        holder.imgEdit.setTag(position);
        holder.rlEdit.setTag(position);

        holder.tvLeaveType.setText(String.valueOf(getLeaveStatus.get(position).getLeaveName()));

        if (!getLeaveStatus.get(position).getFromDate().equals("") && !getLeaveStatus.get(position).getToDate().equals("")) {

            holder.tvDate.setText(String.valueOf(CommonUtils.convertDateStringFormat(getLeaveStatus.get(position).getFromDate())) + "\n" + "To" + "\n" +
                    String.valueOf(CommonUtils.convertDateStringFormat(getLeaveStatus.get(position).getToDate())));
        } else {
            holder.tvDate.setText(null);
        }

        holder.tvLeaveCount.setText(String.valueOf(getLeaveStatus.get(position).getDayCount()));
        holder.tvRemarks.setText(String.valueOf(getLeaveStatus.get(position).getRemarks()));

        if (getLeaveStatus.get(position).getStatusOfLeave() == 0) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.leave_created));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#840AD9"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.GONE);
        } else if (getLeaveStatus.get(position).getStatusOfLeave() == 1) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.order_submitted_for_approval));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFB822"));
            holder.tvStatus.setTextColor(Color.BLACK);
            holder.imgEdit.setVisibility(View.GONE);
        } else if (getLeaveStatus.get(position).getStatusOfLeave() == 2) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.approved));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#2CA189"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.GONE);
        } else if (getLeaveStatus.get(position).getStatusOfLeave() == 3) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.needs_modification));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#564EC0"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.GONE);
        } else if (getLeaveStatus.get(position).getStatusOfLeave() == 4) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(ctx.getString(R.string.rejected));
            holder.tvStatus.setBackgroundColor(Color.parseColor("#F22D4E"));
            holder.tvStatus.setTextColor(Color.WHITE);
            holder.imgEdit.setVisibility(View.GONE);
        } else {
            holder.tvStatus.setVisibility(View.INVISIBLE);
            holder.imgEdit.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return getLeaveStatus.size();
    }

    public void removeItem(int position) {
        getLeaveStatus.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(GetLeaveStatusPOJO.Datum item, int position) {
        getLeaveStatus.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}