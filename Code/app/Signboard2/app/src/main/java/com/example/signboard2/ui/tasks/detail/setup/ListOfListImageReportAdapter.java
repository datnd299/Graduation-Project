package com.example.signboard2.ui.tasks.detail.setup;

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
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.example.signboard2.model.task.Task;
import com.example.signboard2.ui.tasks.TaskFragment;
import com.example.signboard2.ui.tasks.components.ListImageAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListOfListImageReportAdapter extends BaseAdapter {
    private List<ImageSignboardReport> imageSignboardReportList;
    private Context context;
    private SetupTaskDetail fragment;
    private LayoutInflater layoutInflater;
    public ListOfListImageReportAdapter(Context aContext, SetupTaskDetail fragment, List<ImageSignboardReport> taskLstOfLst) {
        this.context = aContext;
        this.imageSignboardReportList = taskLstOfLst;
        this.fragment = fragment;
        layoutInflater = LayoutInflater.from(aContext);
    }




    @Override
    public int getCount() {
        return imageSignboardReportList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageSignboardReportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.component_recyclerview, null);
            ImageSignboardReport imageSignboardReport = this.imageSignboardReportList.get(position);
            ((TextView) convertView.findViewById(R.id.txtText)).setText(imageSignboardReport.getName());
            RecyclerView recyclerView = (RecyclerView) convertView.findViewById(R.id.mRecyclerView);
            ArrayList<ImageReport> imgLst = new ArrayList<>();

            recyclerView.setLayoutManager(new LinearLayoutManager(convertView.getContext(),LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            for (int i = 0; i < 1; i++) {
                ImageReport itemModel = new ImageReport();
                imgLst.add(itemModel);
            }

            ListImageAdapter adapter = new ListImageAdapter(convertView.getContext(),recyclerView,fragment, imgLst);

            recyclerView.setAdapter(adapter);

        } else {

        }


        return convertView;
    }
}
