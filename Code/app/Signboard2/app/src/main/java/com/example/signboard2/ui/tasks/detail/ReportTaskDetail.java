package com.example.signboard2.ui.tasks.detail;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signboard2.R;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.Task;
import com.example.signboard2.ui.tasks.components.ListImageAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReportTaskDetail extends Fragment {

    private TaskDetailViewModel mViewModel;
    private Task currentTask;
    private ArrayList<ImageReport> imgLst;
    RecyclerView recyclerView;
    public ReportTaskDetail(Task task) {
        super();
        this.currentTask = task;
    }

    public static ReportTaskDetail newInstance() {
        return new ReportTaskDetail(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_detail_fragment, container, false);
//        ((TextView) root.findViewById(R.id.txtTName)).setText(this.currentTask.getType());
        ((TextView) root.findViewById(R.id.txtPlaceName)).setText("Điểm treo: "+this.currentTask.getPlacesString());
        ((TextView) root.findViewById(R.id.txtStatus)).setText(this.currentTask.getStatus().toString());
        ((TextView) root.findViewById(R.id.txtSignboardName)).setText("Biển quảng cáo: ");

        ((TextView) root.findViewById(R.id.txtptAName)).setText("Bên thuê: "+this.currentTask.getReportTask().getPlaceRental().getPartyRenter());
        if(this.currentTask.getReportTask().getPlaceRental().getImgs()!=null){
            Picasso.get().load("http://103.35.64.5:8086/api/v1/file/get/"+this.currentTask.getReportTask().getPlaceRental().getImgs().get(0)).into(((ImageView) root.findViewById(R.id.txtSignboardImg)));
        }
        ((TextView) root.findViewById(R.id.txtTime)).setText("Thực hiện từ: "+this.currentTask.getStartTimesString()+" đến: "+this.currentTask.getEndTimesString());
        ((TextView) root.findViewById(R.id.txtRepeatType)).setText("Loại: "+this.currentTask.getReportTask().getRepeatTypeString()+(this.currentTask.getReportTask().getRepeatDetail().getRandom()?"Ngẫu nhiên":""));

        ((TextView) root.findViewById(R.id.txtRepeatFz)).setText("Chu kỳ: "+this.currentTask.getReportTask().getRepeatDetail().getFzUnitString()+" -  "+this.currentTask.getReportTask().getRepeatDetail().getFzValsString());


        recyclerView = (RecyclerView) root.findViewById(R.id.mRecyclerView);
        imgLst = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 1; i++) {
            ImageReport itemModel = new ImageReport();
            imgLst.add(itemModel);
        }

        ListImageAdapter adapter = new ListImageAdapter(root.getContext(),recyclerView,this, imgLst);

        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            Bitmap bp = (Bitmap) data.getExtras().get("data");

            if (resultCode == -1) {
                Bitmap bp2 = (Bitmap) data.getExtras().get("data");
                for(ImageReport img:imgLst){
                    if(img.getTakingPhoto()){
                        img.setImg(bp2);
                        img.setTakingPhoto(false);
                        img.setHaveImg(true);

                        this.imgLst.add(new ImageReport());
                        recyclerView.getAdapter().notifyDataSetChanged();

                    }
                }

            }
            else if (resultCode == 0) {

            } else {

            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TaskDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
