package com.example.signboard2.ui.tasks.components;


import android.content.ContentValues;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.signboard2.R;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.tasks.detail.check.CheckTaskDetail;
import com.example.signboard2.ui.tasks.detail.report.ReportTaskDetail;
import com.example.signboard2.ui.tasks.detail.setup.SetupTaskDetail;
import com.squareup.picasso.Picasso;

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
            if(imgReport.getUploadedName()!=null){
                Picasso.get().load(UtilsAPI.BASE_URL_API+"file/get/"+imgReport.getUploadedName()).into(viewHolder.imgImage);
            }else {
                viewHolder.imgImage.setImageBitmap(imgReport.getImg());
            }

        }else {
            viewHolder.imgIcon.setVisibility(View.VISIBLE);
            viewHolder.imgImage.setVisibility(View.INVISIBLE);
            viewHolder.btnDel.setVisibility(View.INVISIBLE);
        }

        if(imgReport.getUploading()){
            viewHolder.prg.setVisibility(View.VISIBLE);
        }else {
            viewHolder.prg.setVisibility(View.INVISIBLE);
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
//            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            Uri imageUri = mReportTaskDetail.getActivity().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);


            if(mReportTaskDetail.getClass()==SetupTaskDetail.class){
                ((SetupTaskDetail)mReportTaskDetail).setImageUri(imageUri);
            }else if(mReportTaskDetail.getClass()== CheckTaskDetail.class){
                ((CheckTaskDetail)mReportTaskDetail).setImageUri(imageUri);
            }else if(mReportTaskDetail.getClass()== ReportTaskDetail.class){
                ((ReportTaskDetail)mReportTaskDetail).setImageUri(imageUri);
            }

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
        ProgressBar prg;
        public viewHolder(View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            imgImage = (ImageView) itemView.findViewById(R.id.imgImage);
            btnDel = (Button) itemView.findViewById(R.id.btnDel);
            prg = (ProgressBar) itemView.findViewById(R.id.prgUpload);

        }
    }

}
 interface ItemClickListener {
    void onClick(View view, int position,boolean isLongClick);
}
