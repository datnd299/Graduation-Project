package com.example.signboard2.ui.chat;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.signboard2.R;
import com.example.signboard2.model.party.PartyA;
import com.example.signboard2.model.task.Task;
import com.example.signboard2.ui.tasks.TaskFragment;
import com.google.gson.JsonObject;

import java.util.List;

import androidx.annotation.RequiresApi;

public class ListRoomAdapter extends BaseAdapter {
    private List<JsonObject> ptLst;
    private Context context;
    private LayoutInflater layoutInflater;
    public ListRoomAdapter(Context aContext, List<JsonObject> taskLst) {
        this.context = aContext;
        this.ptLst = taskLst;
        layoutInflater = LayoutInflater.from(aContext);
    }




    @Override
    public int getCount() {
        return ptLst.size();
    }

    @Override
    public Object getItem(int position) {
        return ptLst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.component_room, null);

        } else {

        }
        JsonObject pt = ptLst.get(position);



        ((TextView) convertView.findViewById(R.id.ptName)).setText(pt.get("name").getAsString());


        return convertView;
    }
}
