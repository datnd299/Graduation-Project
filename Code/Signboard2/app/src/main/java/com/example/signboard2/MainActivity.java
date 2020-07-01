package com.example.signboard2;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.signboard2.model.Resp;
import com.example.signboard2.model.RespJObj;
import com.example.signboard2.model.other.LocationCollected;
import com.example.signboard2.model.other.NToken;
import com.example.signboard2.model.task.ImageReport;
import com.example.signboard2.rest.ApiCallback;
import com.example.signboard2.rest.UtilsAPI;
import com.example.signboard2.ui.image.SteganoActivity;
import com.example.signboard2.ui.location.LocationAssistant;
import com.example.signboard2.utils.imagesteganographylibrary.Text.AsyncTaskCallback.TextEncodingCallback;
import com.example.signboard2.utils.imagesteganographylibrary.Text.ImageSteganography;
import com.example.signboard2.utils.imagesteganographylibrary.Text.TextEncoding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import retrofit2.Call;
import retrofit2.Response;

import com.example.signboard2.rest.BaseAPIService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements LocationAssistant.Listener, TextEncodingCallback {

    BaseAPIService mApiService;
    ProgressBar mainPrg;
    LocationAssistant assistant;
    private LocationCollected locationCollected;
    ImageSteganography imageSteganography;

  ImageReport currentImageReport;

    public void encodeStegano(Bitmap bitmap, ImageReport imageReport){

        currentImageReport = imageReport;

        imageSteganography = new ImageSteganography(bitmap,getMessage());
        TextEncoding textEncoding = new TextEncoding(MainActivity.this,
                MainActivity.this);
        textEncoding.execute(imageSteganography);

    }
    public String getMessage(){
        String message = "";
        if(locationCollected==null){
            message  = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL+"__"+System.currentTimeMillis()+"__"+"0,0";
        }else {
            if(locationCollected.isTrusted()){
                message  = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL+"__"+System.currentTimeMillis()+"__"+locationCollected.getLocation().getLatitude()+","+locationCollected.getLocation().getLongitude();
            }else {
                message  = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL+"__"+System.currentTimeMillis()+"__"+(-locationCollected.getLocation().getLatitude())+","+(-locationCollected.getLocation().getLongitude());
            }
        }
        return message;
    }



    public LocationCollected getLocationCollected() {
        return locationCollected;
    }

    public void setLocationCollected(LocationCollected locationCollected) {
        this.locationCollected = locationCollected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mainPrg = (ProgressBar) findViewById(R.id.mainPrg);
        locationCollected = new LocationCollected();
        FirebaseApp.initializeApp(this);
        mApiService = UtilsAPI.getAPIService();
        Call<Resp> call = mApiService.sendNtoken(new NToken(FirebaseInstanceId.getInstance().getToken()));
        call.enqueue(new ApiCallback<Resp>(MainActivity.this) {
            @Override
            public void handleSuccess(Call<Resp> call, Response<Resp> response) {

            }

            @Override
            public void handleFailure(Call<Resp> call, Response<Resp> response, Throwable t) {

            }
        });
        Log.d("Firebase", "token "+ FirebaseInstanceId.getInstance().getToken());

        assistant = new LocationAssistant(MainActivity.this, this, LocationAssistant.Accuracy.HIGH, 500, false);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_tasks, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    public void showPrg(){
        mainPrg.setVisibility(View.VISIBLE);
    }
    public void hidePrg(){
        mainPrg.setVisibility(View.INVISIBLE);
    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onResume() {
        super.onResume();
        assistant.start();
    }

    @Override
    protected void onPause() {
        assistant.stop();
        super.onPause();
    }
    @Override
    public void onNeedLocationPermission() {

    }

    @Override
    public void onExplainLocationPermission() {

    }

    @Override
    public void onLocationPermissionPermanentlyDeclined(View.OnClickListener fromView, DialogInterface.OnClickListener fromDialog) {

    }

    @Override
    public void onNeedLocationSettingsChange() {

    }

    @Override
    public void onFallBackToSystemSettings(View.OnClickListener fromView, DialogInterface.OnClickListener fromDialog) {

    }

    @Override
    public void onNewLocationAvailable(Location location) {

        this.locationCollected.setTrusted(true);
        this.locationCollected.setLocation(location);
    }

    @Override
    public void onMockLocationsDetected(View.OnClickListener fromView, DialogInterface.OnClickListener fromDialog, Location location) {
        this.locationCollected.setTrusted(false);
        this.locationCollected.setLocation(location);
    }

    @Override
    public void onError(LocationAssistant.ErrorType type, String message) {

    }

    @Override
    public void onStartTextEncoding() {

    }

    @Override
    public void onCompleteTextEncoding(ImageSteganography result) {
        if (result != null && result.isEncoded()) {
            currentImageReport.setSavedFile(result.getSavedFile());
        }
    }
}
