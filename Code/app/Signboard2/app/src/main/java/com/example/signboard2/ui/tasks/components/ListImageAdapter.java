package com.example.signboard2.ui.tasks.components;


import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signboard2.R;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.ui.tasks.detail.ReportTaskDetail;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.viewHolder> implements View.OnClickListener,View.OnLongClickListener {

    Context context;
    List<ImageReport> arrayList;
    RecyclerView mRecyclerView;
    Fragment mReportTaskDetail;
    private ItemClickListener itemClickListener;

    public ListImageAdapter(Context context, RecyclerView recyclerView, Fragment reportTaskDetail, List<ImageReport> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.mReportTaskDetail = reportTaskDetail;
        this.mRecyclerView = recyclerView;


    }

    @Override
    public  viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, viewGroup, false);

        view.setOnClickListener(this);
        return new viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(ListImageAdapter.viewHolder viewHolder,int position) {

        ImageReport imgReport = arrayList.get(position);

        if(imgReport.getHaveImg()){
            viewHolder.imgIcon.setVisibility(View.INVISIBLE);
            viewHolder.imgImage.setVisibility(View.VISIBLE);
            viewHolder.btnDel.setVisibility(View.VISIBLE);
            viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrayList.remove(position);
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            });
            viewHolder.imgImage.setImageBitmap(imgReport.getImg());
        }else {
            viewHolder.imgIcon.setVisibility(View.VISIBLE);
            viewHolder.imgImage.setVisibility(View.INVISIBLE);
            viewHolder.btnDel.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onClick(View v) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(v);
        ImageReport item = arrayList.get(itemPosition);
        if(!item.getHaveImg()){
            item.setTakingPhoto(true);
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            mReportTaskDetail.startActivityForResult(intent,111);
        }
    }



    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        ImageView imgImage;
        Button btnDel;

        public viewHolder(View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            imgImage = (ImageView) itemView.findViewById(R.id.imgImage);
            btnDel = (Button) itemView.findViewById(R.id.btnDel);

        }
    }

}
 interface ItemClickListener {
    void onClick(View view, int position,boolean isLongClick);
}
