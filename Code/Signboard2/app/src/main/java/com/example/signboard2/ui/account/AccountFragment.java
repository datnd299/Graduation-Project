package com.example.signboard2.ui.account;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Response;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.signboard2.MainActivity;
import com.example.signboard2.R;
import com.example.signboard2.model.RespJObj;
import com.example.signboard2.model.account.AccountResponse;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.login.LoginActivity;
import com.google.gson.JsonObject;

public class AccountFragment extends Fragment {

    private AccountViewModel mViewModel;
    BaseAPIService mApiService;
    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account, container, false);

        mApiService = UtilsAPI.getAPIService();
        Call<RespJObj> call = mApiService.getMyInfo();
        call.enqueue(new ApiCallback<RespJObj>(getActivity()) {

            @Override
            public void handleSuccess(Call<RespJObj> call, Response<RespJObj> response) {
                JsonObject accInfo = response.body().getData();
                ((TextView)root.findViewById(R.id.txtAccName)).setText("Tên đăng nhập: "+accInfo.getAsJsonObject("acc").get("acc_name").getAsString());
                ((TextView)root.findViewById(R.id.txtEmail)).setText("Email: "+accInfo.getAsJsonObject("acc").get("email").getAsString());
                ((TextView)root.findViewById(R.id.txtName)).setText("Tên: "+accInfo.getAsJsonObject("acc").get("name").getAsString());
            }

            @Override
            public void handleFailure(Call<RespJObj> call, Response<RespJObj> response, Throwable t) {

            }
        });

        ((Button)root.findViewById(R.id.btnLogout))
                .setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getDefaultSharedPreferences(root.getContext()).edit().putString("token", "").apply();
                Log.d("xxx", "onClick: ");
                try {
                    Intent k = new Intent(getActivity(), LoginActivity.class);
                    startActivity(k);
                } catch(Exception e) {
                    Log.d("xxx", "onC"+e.getMessage());
                    e.printStackTrace();
                }

            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        // TODO: Use the ViewModel
    }

}
