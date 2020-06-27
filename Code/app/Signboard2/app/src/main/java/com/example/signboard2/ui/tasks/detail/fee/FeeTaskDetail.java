package com.example.signboard2.ui.tasks.detail.fee;

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
import com.example.signboard2.model.task.FeeLineReport;
import com.example.signboard2.model.task.Task;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class FeeTaskDetail extends Fragment {

    private FeeTaskDetailViewModel mViewModel;
    private  Task currentTask;
    private RecyclerView recyclerView;
    BaseAPIService mApiService;
    private JsonObject taskDetail;

    public FeeTaskDetail(Task task) {
        this.currentTask = task;
    }

    public static FeeTaskDetail newInstance() {
        return new FeeTaskDetail(null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mApiService = UtilsAPI.getAPIService();



        View root = inflater.inflate(R.layout.fee_task_detail_fragment, container, false);
        Call<RespJObj> call = mApiService.taskGetByID(this.currentTask.getId());
        FeeTaskDetail sef = this;
        call.enqueue(new ApiCallback<RespJObj>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {
                taskDetail = response.body().getData();
                List<FeeLineReport> fLst = new ArrayList<FeeLineReport>();
                JsonArray jLFee = taskDetail.getAsJsonObject("fee_task").getAsJsonArray("feeDetail");



                for (JsonElement f :jLFee){
                    JsonObject jT = f.getAsJsonObject();
                    JsonObject fPlace = jT.getAsJsonObject("place_rental");

                    PlaceRental pl = new PlaceRental();
                    pl.setName(fPlace.get("name").getAsString());
                    pl.setId(fPlace.get("_id").getAsString());
                    FeeLineReport fLineReport = new FeeLineReport();
                    fLineReport.setPlace(pl);
                    fLineReport.setAmount(jT.get("amount").getAsDouble());
                    fLineReport.setEmStatus(1);
                    fLineReport.setPtBStatus(1);

                    fLst.add(fLineReport);
                }

                ListFeeAdapter adapter = new ListFeeAdapter(root.getContext(),sef, fLst);

                ListView lv = ((ListView) root.findViewById(R.id.list_fee));
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
        mViewModel = ViewModelProviders.of(this).get(FeeTaskDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
