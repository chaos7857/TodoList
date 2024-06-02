package com.example.todolist.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.todolist.R;
import com.example.todolist.db.domain.Thing;

import java.util.List;

public class ThingAdapter extends BaseAdapter {
    private final Context context;
    private final List<Thing> things;
    private TextView title_item;
    private TextView des_item;

    public ThingAdapter(Context context, List<Thing> things) {
        this.context = context;
        this.things = things;
    }

    @Override
    public int getCount() {
        return things.size();
    }

    @Override
    public Object getItem(int position) {
        return things.get(position);
    }

    @Override
    public long getItemId(int position) {
        return things.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.list_item, null);
        }
        title_item = convertView.findViewById(R.id.title_item);
        des_item = convertView.findViewById(R.id.des_item);

        title_item.setText(things.get(position).getTitle());
        des_item.setText(things.get(position).getDes());

        return convertView;
    }
}
