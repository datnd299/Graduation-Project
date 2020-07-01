package com.example.signboard2.ui.tasks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Response;

import com.example.signboard2.MainActivity;
import com.example.signboard2.R;
import com.example.signboard2.model.RespJArr;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.tasks.detail.report.ReportTaskDetail;
import com.example.signboard2.ui.tasks.detail.check.CheckTaskDetail;
import com.example.signboard2.ui.tasks.detail.fee.FeeTaskDetail;
import com.example.signboard2.ui.tasks.detail.setup.SetupTaskDetail;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

    private TaskViewModel dashboardViewModel;
    BaseAPIService mApiService;
    ListView listView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(TaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);

        mApiService = UtilsAPI.getAPIService();
        Call<RespJArr> call = mApiService.taskGetMine();
        final ListView listView = (ListView) root.findViewById(R.id.list_tasks);
//        ((Button) root.findViewById(R.id.btnTest)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("xxxx", "onItemClick: ");
            }
        });
        final TaskFragment fragment = this;

        ((MainActivity) getActivity()).showPrg();
        call.enqueue(new ApiCallback<RespJArr>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJArr> call, Response<RespJArr> response) {
                ((MainActivity) getActivity()).hidePrg();
                JsonArray jTaskArr = response.body().getData();
                List<JsonObject> jTaskLst = new ArrayList<JsonObject>();
                for(JsonElement i:jTaskArr){
                    jTaskLst.add(i.getAsJsonObject());
                }
                Log.d("xxx", "handleSuccess: ");
//                List<Task> taskLst= response.body().getData();
                listView.setAdapter(new ListTaskAdapter(getActivity(), fragment, jTaskLst));
            }
            @Override
            public void handleFailure(Call<RespJArr> call, Response<RespJArr> response, Throwable t) {
            }
        });

        return root;
    }

    public void viewTaskDetail(JsonObject task){
        if(task.get("type").getAsString().equals("setup")){
            SetupTaskDetail fragment = new SetupTaskDetail(task);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(task.get("type").getAsString().equals("check")){
            CheckTaskDetail fragment = new CheckTaskDetail(task);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(task.get("type").getAsString().equals("fee")){
            FeeTaskDetail fragment = new FeeTaskDetail(task);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(task.get("type").getAsString().equals("report")){
            ReportTaskDetail fragment = new ReportTaskDetail(task);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }
}
