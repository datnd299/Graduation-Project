package com.example.signboard2.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.signboard2.R;
import com.example.signboard2.ui.tasks.TaskFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public class ListNotificationAdapter extends BaseAdapter {

    private JsonArray notiArr;
    private Context context;
    private NotificationsFragment fragment;
    private LayoutInflater layoutInflater;
    public ListNotificationAdapter(Context aContext, NotificationsFragment fragment, JsonArray notiArr) {
        this.context = aContext;
        this.notiArr = notiArr;
        this.fragment = fragment;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return notiArr.size();
    }

    @Override
    public Object getItem(int position) {
        return notiArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.component_single_notification, null);
        } else {

        }
        JsonObject noti = notiArr.get(position).getAsJsonObject();
        ((TextView) convertView.findViewById(R.id.txtTitle)).setText(noti.get("title").getAsString());
        ((TextView) convertView.findViewById(R.id.txtContent)).setText(noti.get("content").getAsString());
        return convertView;
    }
}
