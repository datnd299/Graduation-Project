package com.example.signboard2.ui.tasks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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


        ((MainActivity) getActivity()).showPrg();
        call.enqueue(new ApiCallback<TaskLst>(getActivity()) {
            @Override
            public void handleSuccess(Call<TaskLst> call, Response<TaskLst> response) {
                ((MainActivity) getActivity()).hidePrg();
                List<Task> taskLst= response.body().getData();
                listView.setAdapter(new ListTaskAdapter(getActivity(), taskLst));
            }
            @Override
            public void handleFailure(Call<TaskLst> call, Response<TaskLst> response, Throwable t) {
            }
        });

        return root;
    }
}
