package com.example.signboard2.ui.tasks;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.signboard2.R;
import com.example.signboard2.model.task.Task;

import java.util.List;

import androidx.annotation.RequiresApi;

public class ListTaskAdapter extends BaseAdapter {
    private List<Task> taskLst;
    private Context context;
    private LayoutInflater layoutInflater;
    public ListTaskAdapter(Context aContext, List<Task> taskLst) {
        this.context = aContext;
        this.taskLst = taskLst;
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

        Task task = this.taskLst.get(position);
        ((TextView) convertView.findViewById(R.id.txtTaskPlace)).setText(task.getPlacesString());
        ((TextView) convertView.findViewById(R.id.txtTime)).setText(task.getTimesString());

        return convertView;
    }
}
