package com.example.signboard2.ui.notifications;

import android.os.Bundle;
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
import com.example.signboard2.model.RespJArr;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    BaseAPIService mApiService;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        mApiService = UtilsAPI.getAPIService();
        Call<RespJArr> call = mApiService.getNotification();


        ((MainActivity) getActivity()).showPrg();
        call.enqueue(new ApiCallback<RespJArr>(getActivity()) {
            @Override
            public void handleSuccess(Call<RespJArr> call, Response<RespJArr> response) {
                ListView lv = root.findViewById(R.id.lstNotification);
                ListNotificationAdapter adapter = new ListNotificationAdapter(getContext(),NotificationsFragment.this,response.body().getData());
                lv.setAdapter(adapter);
                ((MainActivity) getActivity()).hidePrg();
            }

            @Override
            public void handleFailure(Call<RespJArr> call, Response<RespJArr> response, Throwable t) {
                ((MainActivity) getActivity()).hidePrg();
            }
        });
        return root;
    }
}
