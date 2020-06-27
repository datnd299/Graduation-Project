package com.example.signboard2.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.signboard2.MainActivity;
import com.example.signboard2.R;
import com.example.signboard2.model.account.AccountResponse;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.BaseAPIService;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.utils.Constant;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    ImageView imageViewHeader;
    BaseAPIService mApiService;
    TextInputEditText edtAcc,edtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        imageViewHeader = findViewById(R.id.imageViewHeader);
        Constant.TOKEN= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).getString("token", null);
        if(Constant.TOKEN != null&&!Constant.TOKEN.isEmpty()){
            try {
                Intent k = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(k);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        mApiService = UtilsAPI.getAPIService();
        handleView();
        Picasso.get().load("https://cdn.shopify.com/s/files/1/1991/1709/products/Conected-tile-800x800.jpg?v=1575931838").into(imageViewHeader);
        Picasso.get().load("http://103.35.64.5:8086/assets/media/logos/logo-mini-md.png").into((ImageView) findViewById(R.id.imgLogo));
    }
    void handleView(){
        edtAcc = findViewById(R.id.edtAcc);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<AccountResponse> call = mApiService.usersLogin(edtAcc.getText().toString(),edtPassword.getText().toString());
                call.enqueue(new ApiCallback<AccountResponse>(LoginActivity.this) {
                    @Override
                    public void handleSuccess(Call<AccountResponse> call, Response<AccountResponse> response) {
                        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("token", response.body().getToken()).apply();
                        Constant.TOKEN = response.body().getToken();
                        try {
                            Intent k = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(k);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("xxxx", "onResponse: "+response.body().getData().getAcc().getAccName());
                    }
                    @Override
                    public void handleFailure(Call<AccountResponse> call, Response<AccountResponse> response, Throwable t) {
                        Log.d("xxxx", response.message());
                    }
                });
            }
        });
    }
}
