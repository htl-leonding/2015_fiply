package htl_leonding.fiplyteam.fiply;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Gerald on 30/10/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;

    private List<String> uebungsListHeader;
    private HashMap<String, List<String>> uebungsListChildren;

    public ExpandableListAdapter(Context context, List<String> uebungsListHeader, HashMap<String, List<String>> uebungsListChildren) {
        this.context = context;
        this.uebungsListHeader = uebungsListHeader;
        this.uebungsListChildren = uebungsListChildren;
    }

    @Override
    public int getGroupCount() {
        return this.uebungsListHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.uebungsListChildren.get(this.uebungsListHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.uebungsListHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.uebungsListChildren.get(this.uebungsListHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.parent_listview, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.expListViewParent);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_listview, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.expListViewChild);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}