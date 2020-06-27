package com.example.signboard2.ui.tasks.detail.check;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.signboard2.R;
import com.example.signboard2.model.RespJObj;
import com.example.signboard2.model.place.PlaceRental;
import com.example.signboard2.model.task.CheckLineReport;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.model.task.Task;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CheckTaskDetail extends Fragment {

    private CheckTaskDetailViewModel mViewModel;
    private  Task currentTask;
    private RecyclerView recyclerView;
    BaseAPIService mApiService;
    private JsonObject taskDetail;
    public CheckTaskDetail(Task task) {
        this.currentTask = task;

    }

    public static CheckTaskDetail newInstance() {
        return new CheckTaskDetail(null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mApiService = UtilsAPI.getAPIService();



        View root = inflater.inflate(R.layout.check_task_detail_fragment, container, false);
        Call<RespJObj> call = mApiService.taskGetByID(this.currentTask.getId());
        CheckTaskDetail sef = this;
        call.enqueue(new ApiCallback<RespJObj>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {
                taskDetail = response.body().getData();
                List<CheckLineReport> cLst = new ArrayList<CheckLineReport>();
                JsonArray jLCheck = taskDetail.getAsJsonObject("check_task").getAsJsonArray("place_rental");



                for (JsonElement f :jLCheck){
                    JsonObject cPlace = f.getAsJsonObject();
                    PlaceRental pl = new PlaceRental();
                    pl.setName(cPlace.get("name").getAsString());
                    pl.setId(cPlace.get("_id").getAsString());

                    CheckLineReport cLineReport = new CheckLineReport();

                    List<ImageReport> lImageReport = new ArrayList<ImageReport>();
                    lImageReport.add(new ImageReport());
                    cLineReport.setPlace(pl);
                    cLineReport.setImgLst(lImageReport);
                    cLineReport.setStatus(1);

                    cLst.add(cLineReport);
                }

                ListCheckAdapter adapter = new ListCheckAdapter(root.getContext(),sef, cLst);

                ListView lv = ((ListView) root.findViewById(R.id.list_check));
                ViewGroup.LayoutParams params = lv.getLayoutParams();
                params.height = 435*2;
                lv.setLayoutParams(params);
                lv.requestLayout();

//                List<List<ImageReport>> lt = new ArrayList<>();
//                for (int i =0;i<2;i++){
//                    lt.add(new ArrayList<ImageReport>());
//                }
                lv.setAdapter(adapter);


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
        mViewModel = ViewModelProviders.of(this).get(CheckTaskDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
