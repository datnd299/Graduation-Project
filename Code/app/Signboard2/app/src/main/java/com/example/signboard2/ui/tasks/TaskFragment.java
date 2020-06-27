package com.example.signboard2.ui.tasks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.signboard2.model.task.Task;
import com.example.signboard2.model.task.TaskLst;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.tasks.detail.ReportTaskDetail;
import com.example.signboard2.ui.tasks.detail.check.CheckTaskDetail;
import com.example.signboard2.ui.tasks.detail.fee.FeeTaskDetail;
import com.example.signboard2.ui.tasks.detail.setup.SetupTaskDetail;

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
        Call<TaskLst> call = mApiService.taskGetMine();
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
        call.enqueue(new ApiCallback<TaskLst>(getActivity()) {
            @Override
            public void handleSuccess(Call<TaskLst> call, Response<TaskLst> response) {
                ((MainActivity) getActivity()).hidePrg();
                List<Task> taskLst= response.body().getData();
                listView.setAdapter(new ListTaskAdapter(getActivity(), fragment, taskLst));
            }
            @Override
            public void handleFailure(Call<TaskLst> call, Response<TaskLst> response, Throwable t) {
            }
        });

        return root;
    }

    public void viewTaskDetail(Task task){
        if(task.getType().equals("setup")){
            SetupTaskDetail fragment = new SetupTaskDetail(task);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(task.getType().equals("check")){
            CheckTaskDetail fragment = new CheckTaskDetail(task);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(task.getType().equals("fee")){
            FeeTaskDetail fragment = new FeeTaskDetail(task);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(task.getType().equals("report")){
            ReportTaskDetail fragment = new ReportTaskDetail(task);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }
}
