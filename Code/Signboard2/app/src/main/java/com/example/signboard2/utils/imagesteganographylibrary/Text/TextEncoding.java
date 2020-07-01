package com.example.signboard2.utils.imagesteganographylibrary.Text;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.signboard2.utils.imagesteganographylibrary.Text.AsyncTaskCallback.TextEncodingCallback;
import com.example.signboard2.utils.imagesteganographylibrary.Utils.Utility;

import java.io.File;
import java.util.List;

import androidx.annotation.RequiresApi;

/**
 * In this class all those method in EncodeDecode class are used to encode secret message in image.
 * All the tasks will run in background.
 */
public class TextEncoding extends AsyncTask<ImageSteganography, Integer, ImageSteganography> {

    //Tag for Log
    private static final String TAG = TextEncoding.class.getName();

    private final ImageSteganography result;

    //Callback interface for AsyncTask
    private final TextEncodingCallback callbackInterface;
    private int maximumProgress;
    private final ProgressDialog progressDialog;

    public TextEncoding(Activity activity, TextEncodingCallback callbackInterface) {
        super();

        this.progressDialog = new ProgressDialog(activity);
        this.callbackInterface = callbackInterface;
        //making result object
        this.result = new ImageSteganography();
    }

    //pre execution of method
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //setting parameters of progress dialog
        if (progressDialog != null) {
            progressDialog.setMessage("Loading, Please Wait...");
            progressDialog.setTitle("Encoding Message");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected void onPostExecute(ImageSteganography textStegnography) {
        super.onPostExecute(textStegnography);

        //dismiss progress dialog
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        //Sending result to callback interface
        callbackInterface.onCompleteTextEncoding(result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        //Updating progress dialog
        if (progressDialog != null) {
            progressDialog.incrementProgressBy(values[0]);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected ImageSteganography doInBackground(ImageSteganography... imageSteganographies) {

        maximumProgress = 0;

        if (imageSteganographies.length > 0) {

            ImageSteganography textStegnography = imageSteganographies[0];

            //getting image bitmap
            Bitmap bitmap = textStegnography.getImage();
            File resultFile = EncodeDecode.encodeMessage(bitmap, textStegnography.getMessage(), new EncodeDecode.ProgressHandler() {


                //Progress Handler
                @Override
                public void setTotal(int tot) {
                    maximumProgress = tot;
                    progressDialog.setMax(maximumProgress);
                    Log.d(TAG, "Total Length : " + tot);
                }

                @Override
                public void increment(int inc) {
                    publishProgress(inc);
                }

                @Override
                public void finished() {
                    Log.d(TAG, "Message Encoding end....");
                    progressDialog.setIndeterminate(true);
                }
            });




//            bitmap.recycle();

            System.gc();



//            result.setEncoded_image(resultBitmap);
            result.setSavedFile(resultFile);
            result.setEncoded(true);
        }

        return result;
    }
}
