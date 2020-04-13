package com.ck19.infodroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformationAdapter extends BaseAdapter {

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_VALUE = 1;
    private Context context;

    private List<String> items;
    private List<Integer> titleIndex;

    private HashMap<String, Object>objects;

    public InformationAdapter(Context context, HashMap<String, Object>objects) {
        super();

        this.context = context;

        this.objects = objects;

        this.titleIndex = new ArrayList<Integer>();
        this.items = new ArrayList<String>();

        resetObjects();
    }

    private void resetObjects() {
        this.titleIndex.clear();
        this.items.clear();

        for (Map.Entry<String, Object> entry: this.objects.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                this.titleIndex.add(this.items.size());
                this.items.add(key);
                this.items.add((String) value);
            } else if (value instanceof String[]) {
                this.titleIndex.add(this.items.size());
                this.items.add(key);
                this.items.addAll(Arrays.asList((String[]) value));
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        this.resetObjects();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (this.titleIndex.contains(position)) {
            return TYPE_TITLE;
        } else {
            return TYPE_VALUE;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TitleHolder titleHolder;
        ValueHolder valueHolder;
        switch (getItemViewType(position)) {
            case TYPE_TITLE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.title_list_item, null);
                    titleHolder = new TitleHolder();
                    titleHolder.title = (TextView) convertView.findViewById(R.id.text_item_title);
                    convertView.setTag(titleHolder);
                } else {
                    titleHolder = (TitleHolder) convertView.getTag();
                }
                titleHolder.title.setText(this.items.get(position));
                break;
            case TYPE_VALUE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.value_list_item, null);
                    valueHolder = new ValueHolder();
                    valueHolder.value = (TextView) convertView.findViewById(R.id.text_item_value);
                    convertView.setTag(valueHolder);
                } else {
                    valueHolder = (ValueHolder) convertView.getTag();
                }
                valueHolder.value.setText(this.items.get(position));
                break;
        }
        return convertView;
    }

    class TitleHolder {
        TextView title;
    }

    class ValueHolder {
        TextView value;
    }
}
