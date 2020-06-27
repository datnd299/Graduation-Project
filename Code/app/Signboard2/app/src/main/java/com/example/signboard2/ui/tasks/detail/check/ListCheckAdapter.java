package com.example.signboard2.ui.tasks.detail.check;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.signboard2.R;
import com.example.signboard2.model.task.CheckLineReport;
import com.example.signboard2.model.task.FeeLineReport;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.ui.tasks.components.ListImageAdapter;
import com.example.signboard2.ui.tasks.detail.fee.FeeTaskDetail;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListCheckAdapter extends BaseAdapter {
    private List<CheckLineReport> checkLst;
    private Context context;
    private CheckTaskDetail fragment;
    private LayoutInflater layoutInflater;
    public ListCheckAdapter(Context aContext, CheckTaskDetail fragment, List<CheckLineReport> checks) {
        this.context = aContext;
        this.checkLst = checks;
        this.fragment = fragment;
        layoutInflater = LayoutInflater.from(aContext);
    }




    @Override
    public int getCount() {
        return checkLst.size();
    }

    @Override
    public Object getItem(int position) {
        return checkLst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.component_single_check, null);
            CheckLineReport checkLineReport = this.checkLst.get(position);
            ((TextView)convertView.findViewById(R.id.placeName)).setText(checkLineReport.getPlace().getName());
            RecyclerView recyclerView = (RecyclerView) convertView.findViewById(R.id.listImage);


            recyclerView.setLayoutManager(new LinearLayoutManager(convertView.getContext(),LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());


            ListImageAdapter adapter = new ListImageAdapter(convertView.getContext(),recyclerView,fragment, checkLineReport.getImgLst());

            recyclerView.setAdapter(adapter);

        } else {

        }


        return convertView;
    }
}
