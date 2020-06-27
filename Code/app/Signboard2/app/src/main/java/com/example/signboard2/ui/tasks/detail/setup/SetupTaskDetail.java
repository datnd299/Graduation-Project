package com.example.signboard2.ui.tasks.detail.setup;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.signboard2.R;
import com.example.signboard2.model.RespJObj;
import com.example.signboard2.model.other.ImageText;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.ImageSignboardReport;
import com.example.signboard2.model.task.Task;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.tasks.components.ListImageTextAdapter;
import com.example.signboard2.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class SetupTaskDetail extends Fragment {

    private SetupTaskDetailViewModel mViewModel;
    private  Task currentTask;
    private RecyclerView recyclerView;
    BaseAPIService mApiService;
    private JsonObject taskDetail;
    public SetupTaskDetail(Task task) {
        super();
        this.currentTask = task;
    }
    public static SetupTaskDetail newInstance() {
        return new SetupTaskDetail(null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mApiService = UtilsAPI.getAPIService();



        View root = inflater.inflate(R.layout.setup_task_detail_fragment, container, false);
        Call<RespJObj> call = mApiService.taskGetByID(this.currentTask.getId());
        SetupTaskDetail sef = this;
        call.enqueue(new ApiCallback<RespJObj>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {
                taskDetail = response.body().getData();
                ((TextView) root.findViewById(R.id.txtPlaceName)).setText("Điểm treo: "+taskDetail.getAsJsonObject("setup_task").getAsJsonObject("place_rental").get("name").getAsString());
                ((TextView) root.findViewById(R.id.txtPlaceAddress)).setText("Địa chỉ: "+taskDetail.getAsJsonObject("setup_task").getAsJsonObject("place_rental").get("address").getAsString());
                JsonArray accs = taskDetail.getAsJsonObject("setup_task").getAsJsonArray("accs");
                String accStr="";
                for (JsonElement acc :accs){
                    JsonObject jAcc = acc.getAsJsonObject();
                    accStr+=jAcc.get("name").getAsString()+", ";
                }
                if(accStr.length()>0){
                    accStr = accStr.substring(0,accStr.length()-2);

                }
                ((TextView) root.findViewById(R.id.txtEm)).setText("Người thực hiện: "+accStr);
                String start = Utils.toDateTimeString(taskDetail.get("start").getAsString()) ;
                String end = Utils.toDateTimeString(taskDetail.get("end").getAsString()) ;
                ((TextView) root.findViewById(R.id.txtTime)).setText(start+" -> "+end);

                recyclerView =  (RecyclerView) root.findViewById(R.id.mRecyclerViewSignboard);
                JsonArray sbs = taskDetail.getAsJsonObject("setup_task").getAsJsonArray("signboards");
                ArrayList<ImageText> imgLst = new ArrayList<ImageText>();
                List<ImageSignboardReport> lt = new ArrayList<ImageSignboardReport>();
                for (JsonElement sb :sbs){
                    JsonObject jSb = sb.getAsJsonObject();
                    JsonArray imgs = jSb.getAsJsonArray("imgs");
                    String img = imgs.get(0).getAsString();
                    ImageText imgT = new ImageText(UtilsAPI.BASE_URL_API+"file/get/"+img,jSb.get("name").getAsString());
                    imgLst.add(imgT);

                    ImageSignboardReport imageSignboardReport=new ImageSignboardReport();
                    imageSignboardReport.setName(jSb.get("name").getAsString());
                    imageSignboardReport.setImgs(new ArrayList<ImageReport>());
                    lt.add(imageSignboardReport);
                }


                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.HORIZONTAL,false));
                recyclerView.setItemAnimator(new DefaultItemAnimator());



                ListImageTextAdapter adapter = new ListImageTextAdapter(root.getContext(),recyclerView,sef, imgLst);

                ListView lv = ((ListView) root.findViewById(R.id.listRecyclerView));
                ViewGroup.LayoutParams params = lv.getLayoutParams();
                params.height = 435*2;
                lv.setLayoutParams(params);
                lv.requestLayout();

//                List<List<ImageReport>> lt = new ArrayList<>();
//                for (int i =0;i<2;i++){
//                    lt.add(new ArrayList<ImageReport>());
//                }
                lv.setAdapter(new ListOfListImageReportAdapter(root.getContext(), sef, lt));

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void handleFailure(Call<RespJObj> call, Response<RespJObj> response, Throwable t) {
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SetupTaskDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
