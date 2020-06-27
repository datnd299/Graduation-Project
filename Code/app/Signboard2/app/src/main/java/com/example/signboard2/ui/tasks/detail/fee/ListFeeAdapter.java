package com.example.signboard2.ui.tasks.detail.fee;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.signboard2.R;
import com.example.signboard2.model.task.FeeLineReport;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.example.signboard2.ui.tasks.components.ListImageAdapter;
import com.example.signboard2.ui.tasks.detail.setup.SetupTaskDetail;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListFeeAdapter extends BaseAdapter {
    private List<FeeLineReport> feeLst;
    private Context context;
    private FeeTaskDetail fragment;
    private LayoutInflater layoutInflater;
    public ListFeeAdapter(Context aContext, FeeTaskDetail fragment, List<FeeLineReport> fees) {
        this.context = aContext;
        this.feeLst = fees;
        this.fragment = fragment;
        layoutInflater = LayoutInflater.from(aContext);
    }




    @Override
    public int getCount() {
        return feeLst.size();
    }

    @Override
    public Object getItem(int position) {
        return feeLst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.component_single_fee, null);
            FeeLineReport feeLineReport = this.feeLst.get(position);
            ((TextView)convertView.findViewById(R.id.placeName)).setText(feeLineReport.getPlace().getName());
            ((TextView)convertView.findViewById(R.id.amount)).setText("Số tiền: "+String.format("%1$,.0f", feeLineReport.getAmount()));

        } else {

        }


        return convertView;
    }
}
