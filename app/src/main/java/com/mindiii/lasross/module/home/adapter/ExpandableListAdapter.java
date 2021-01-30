package com.mindiii.lasross.module.home.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.mindiii.lasross.R;
import com.mindiii.lasross.base.ClickListener;
import com.mindiii.lasross.module.home.model.MenuSliderResponse;

import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<MenuSliderResponse.DataBean.CategoryBean> listDataHeader;
    // private List<MenuSliderResponse.DataBean.CategoryBean.SubCategoryBean> listHashMap;
    private ClickListener.MenuChildClick menuChildClick;

    public ExpandableListAdapter(ClickListener.MenuChildClick menuChildClick, Context context,
                                 List<MenuSliderResponse.DataBean.CategoryBean> listDataHeader) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        // this.listHashMap = listHashMap;
        this.menuChildClick = menuChildClick;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (listDataHeader.get(groupPosition).getSub_category()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataHeader.get(groupPosition).getSub_category().get(childPosition);
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
        String headerTitle = listDataHeader.get(groupPosition).getCategory_name();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group_api_layout, null);
        }

        final Typeface semi_bold = ResourcesCompat.getFont(context, R.font.ibmplexsans_semibold);
        final Typeface regular = ResourcesCompat.getFont(context, R.font.ibmplexsans_regular);

        TextView tvHeaderName;
        View tvLine;
        ImageView ivHeaderImage, ivUpAndDown;
        tvHeaderName = convertView.findViewById(R.id.tvHeaderName);
        tvLine = convertView.findViewById(R.id.tvLine);
        ivHeaderImage = convertView.findViewById(R.id.ivHeaderImage);
        ivUpAndDown = convertView.findViewById(R.id.ivUpAndDown);

        tvHeaderName.setText(headerTitle);

        if (listDataHeader.get(listDataHeader.size() - 1).equals(headerTitle)) {
            tvLine.setVisibility(View.GONE);
        } else
            tvLine.setVisibility(View.VISIBLE);


        if (headerTitle.equalsIgnoreCase(listDataHeader.get(listDataHeader.size() - 1).getCategory_name())) {
            tvLine.setVisibility(View.GONE);
        }

        //CommonUtils.toastMessage(context,listDataHeader.get(listDataHeader.size()-1).getCategory_name()+"");
        //Log.e("last item is ",listDataHeader.get(listDataHeader.size())+"");

        if (headerTitle.equalsIgnoreCase(listDataHeader.get(groupPosition).getCategory_name())) {
            if (isExpanded) {
                tvHeaderName.setTextColor(context.getResources().getColor(R.color.another_black));
                ivUpAndDown.setImageResource(R.drawable.ic_drop_down_arrow_ico);
                tvLine.setVisibility(View.GONE);
                tvHeaderName.setTypeface(semi_bold);
            } else {
                tvHeaderName.setTextColor(context.getResources().getColor(R.color.another_black));
                ivUpAndDown.setImageResource(R.drawable.ic_gray_back_ico);
                tvHeaderName.setTypeface(regular);
            }
        }

        /*if (isExpanded)
            convertView.setPadding(0, 0, 0, 0);
        else
            convertView.setPadding(0, 0, 0, 20);*/
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childText = listDataHeader.get(groupPosition).getSub_category().get(childPosition).getCategory_name();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.list_item_api_layout, null);
        }


        TextView tvHeaderName = convertView.findViewById(R.id.tvHeaderName);
        View tvChildLine = convertView.findViewById(R.id.tvChildLine);
        LinearLayout llChildItemName = convertView.findViewById(R.id.llChildItemName);
        llChildItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myCatName = listDataHeader.get(groupPosition).getSub_category().get(childPosition).getCategory_name();
                String myCatId = listDataHeader.get(groupPosition).getSub_category().get(childPosition).getCategoryId();
                menuChildClick.onClick(myCatId, myCatName);
            }
        });

        tvHeaderName.setText(childText);

        if (isLastChild) {
            tvChildLine.setVisibility(View.VISIBLE);
        } else {
            tvChildLine.setVisibility(View.GONE);
        }
        if (childPosition == listDataHeader.get(groupPosition).getSub_category().size() - 1) {
            convertView.setPadding(0, 0, 0, 20);
        } else
            convertView.setPadding(0, 0, 0, 0);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
