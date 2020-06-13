package com.example.signboard2.ui.image;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signboard2.R;
import com.example.signboard2.utils.imagesteganographylibrary.Text.AsyncTaskCallback.TextDecodingCallback;
import com.example.signboard2.utils.imagesteganographylibrary.Text.AsyncTaskCallback.TextEncodingCallback;
import com.example.signboard2.utils.imagesteganographylibrary.Text.ImageSteganography;
import com.example.signboard2.utils.imagesteganographylibrary.Text.TextDecoding;
import com.example.signboard2.utils.imagesteganographylibrary.Text.TextEncoding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class SteganoActivity extends AppCompatActivity implements TextEncodingCallback, TextDecodingCallback {
    Button btnChooseImg, btnEx, btnDecode;
    EditText edtText,edtKey;
    ImageView imgV, imgResult,imgDecoded;
    TextView txtMess,txtImgV,txtImgEx,txtImgDecoded;

    private static int RESULT_LOAD_IMAGE = 1;
    ImageSteganography imageSteganography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stegano);
        btnChooseImg = (Button) findViewById(R.id.btnChoose);
        btnEx = (Button) findViewById(R.id.btnEx);
        btnDecode = (Button) findViewById(R.id.btnDecode);


        edtKey = (EditText) findViewById(R.id.edtKey);
        edtText = (EditText) findViewById(R.id.edtText);
        imgV = (ImageView) findViewById(R.id.imgV);
        imgResult = (ImageView) findViewById(R.id.imgEx);
        imgDecoded = (ImageView) findViewById(R.id.imgDecoded);
        txtMess = (TextView) findViewById(R.id.txtMess);
        txtImgV = (TextView) findViewById(R.id.txtImgV);
        txtImgEx = (TextView) findViewById(R.id.txtImgEx);
        txtImgDecoded = (TextView) findViewById(R.id.txtImgDecoded);


        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");


        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });
        btnEx.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                imgV.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imgV.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                imageSteganography = new ImageSteganography("123",
                        "123",
                        bitmap);
                TextEncoding textEncoding = new TextEncoding(SteganoActivity.this,
                        SteganoActivity.this);
                textEncoding.execute(imageSteganography);


            }
        });

        btnDecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgV.invalidate();
//                BitmapDrawable drawable = (BitmapDrawable) imgV.getDrawable();
//                Bitmap bitmap = drawable.getBitmap();
                String root = Environment.getExternalStorageDirectory().toString();
                Bitmap bitmap =BitmapFactory.decodeFile(root+"/saved_images/Image-9867.png");
                imgV.setImageBitmap(bitmap);
                ImageSteganography imageSteganography = new ImageSteganography("123",
                        bitmap);
                TextDecoding textDecoding = new TextDecoding(SteganoActivity.this,
                        SteganoActivity.this);
                textDecoding.execute(imageSteganography);

            }
        });
        String d = getDeviceName()+"__"+System.currentTimeMillis()+"__"+"20.9911808,105.8504704"+"đạt";

            byte[] bytes = d.getBytes();
        String value = new String(bytes);

        Log.d("xxx", d);
    }




    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
//            txtImgV.setText(Long.toString((new File(selectedImage.getPath())).length()));
            imgV.setImageURI(selectedImage);


            BitmapDrawable drawable = (BitmapDrawable) imgV.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
//            txtImgV.setText(Long.toString(hashBitmap(bitmap)));
//
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//
//            imgV.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }
    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        if (!myDir.exists()) {
           boolean success= myDir.mkdirs();
            if (success) {
                Log.d("xxxx", "SaveImage: ");
            } else {
                Log.d("xxxx", "SaveImage: sadf sd ");
            }
        }





        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".png";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
//        file.mkdirs();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onStartTextEncoding() {

    }
    public long hashBitmap(Bitmap bmp){
        long hash = 31; //or a higher prime at your choice
        for(int x = 0; x < bmp.getWidth(); x++){
            for (int y = 0; y < bmp.getHeight(); y++){
                hash *= (bmp.getPixel(x,y) + 31);
            }
        }
        return hash;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCompleteTextEncoding(ImageSteganography result) {
        if (result != null && result.isEncoded()) {

            //encrypted image bitmap is extracted from result object
            Bitmap encoded_image = result.getEncoded_image();
            SaveImage(encoded_image);
//            byte[] bitmapdata = result.getEncrypted_zip();
//            Bitmap encoded_image = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
//            txtImgEx.setText(Long.toString(hashBitmap(encoded_image)));
            imgResult.setImageBitmap(encoded_image);

        }
        if(result!=null && result.isDecoded()){
            Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();

            Bitmap encoded_image = result.getEncoded_image();
            imgDecoded.setImageBitmap(encoded_image);
            txtImgDecoded.setText(Integer.toString(encoded_image.getAllocationByteCount()));

        }
    }
}
