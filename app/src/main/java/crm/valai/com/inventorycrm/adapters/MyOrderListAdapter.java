package crm.valai.com.inventorycrm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import crm.valai.com.inventorycrm.R;
import crm.valai.com.inventorycrm.modals.GetInventoryClassificationPOJO;

public class MyOrderListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<GetInventoryClassificationPOJO.Datum> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<GetInventoryClassificationPOJO.Datum>> _listDataChild;

    public MyOrderListAdapter(Context context, List<GetInventoryClassificationPOJO.Datum> listDataHeader, HashMap<String, List<GetInventoryClassificationPOJO.Datum>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getName()).get(childPosition).getName();
    }

    public Object getChildParentId(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getName()).get(childPosition).getParentId();
    }

    public Object getChildItemId(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getName()).get(childPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (infalInflater != null) {
                convertView = infalInflater.inflate(R.layout.order_listcluster, null);
            }
        }

        TextView lblListHeader = null;
        if (convertView != null) {
            lblListHeader = convertView.findViewById(R.id.lblListCluster);
        }
        if (lblListHeader != null) {
            lblListHeader.setTypeface(null, Typeface.BOLD);
        }
        if (lblListHeader != null) {
            lblListHeader.setText(headerTitle);
        }
        return convertView;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                convertView = inflater.inflate(R.layout.order_listcluster_sub, null);
            }
        }

        TextView txtListChild = null;
        if (convertView != null) {
            txtListChild = convertView.findViewById(R.id.lblListItem);
        }

        if (txtListChild != null) {
            txtListChild.setText(childText);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (_listDataChild.get(this._listDataHeader.get(groupPosition).getName()) != null) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition).getName()).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getName();
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
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