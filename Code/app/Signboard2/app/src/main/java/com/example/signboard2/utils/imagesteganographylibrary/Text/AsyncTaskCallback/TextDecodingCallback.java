package com.example.signboard2.utils.imagesteganographylibrary.Text.AsyncTaskCallback;

import com.example.signboard2.utils.imagesteganographylibrary.Text.ImageSteganography;

/**
 * This the callback interface for TextDecoding AsyncTask.
 */

public interface TextDecodingCallback {

    void onStartTextEncoding();

    void onCompleteTextEncoding(ImageSteganography result);

}
