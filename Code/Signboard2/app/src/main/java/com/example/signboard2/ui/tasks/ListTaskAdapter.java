package com.example.signboard2.ui.tasks;

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
import com.example.signboard2.model.task.Task;
import com.example.signboard2.utils.Utils;
import com.google.gson.JsonObject;

import java.util.List;

import androidx.annotation.RequiresApi;

public class ListTaskAdapter extends BaseAdapter {
    private List<JsonObject> taskLst;
    private Context context;
    private TaskFragment fragment;
    private LayoutInflater layoutInflater;
    public ListTaskAdapter(Context aContext, TaskFragment fragment, List<JsonObject> taskLst) {
        this.context = aContext;
        this.taskLst = taskLst;
        this.fragment = fragment;
        layoutInflater = LayoutInflater.from(aContext);
    }




    @Override
    public int getCount() {
        return taskLst.size();
    }

    @Override
    public Object getItem(int position) {
        return taskLst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.component_single_task, null);

        } else {

        }
        JsonObject task = this.taskLst.get(position);
        Button btn = convertView.findViewById(R.id.btnDetail);
        ImageView imgIcon = convertView.findViewById(R.id.imgTaskIcon);
        if(task.get("type").getAsString().equals("setup")){
            imgIcon.setImageResource(R.drawable.ic_cogs_solid);

        }
        if(task.get("type").getAsString().equals("check")){
            imgIcon.setImageResource(R.drawable.ic_flag_checkered_solid);
        }
        if(task.get("type").getAsString().equals("fee")){
            imgIcon.setImageResource(R.drawable.ic_money_bill_alt_regular);
        }
        if(task.get("type").getAsString().equals("report")){
            imgIcon.setImageResource(R.drawable.ic_camera_retro_solid);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.viewTaskDetail(task);
//                TaskDetail fragment = new TaskDetail();
//                FragmentTransaction transaction = convertView.getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, fragment);
//                transaction.commit();
            }
        });


        ((TextView) convertView.findViewById(R.id.txtTaskPlace)).setText(Utils.getPlacesOfTask(task));
        ((TextView) convertView.findViewById(R.id.txtTime)).setText(Utils.getStringTimeOfTask(task));
        ((Button) convertView.findViewById(R.id.btnDetail)).setText(Utils.getStatusStringOfTask(task));


        return convertView;
    }
}
