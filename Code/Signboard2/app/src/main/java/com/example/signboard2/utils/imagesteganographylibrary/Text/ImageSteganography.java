package com.example.signboard2.utils.imagesteganographylibrary.Text;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.signboard2.utils.imagesteganographylibrary.Utils.Utility;
import com.example.signboard2.utils.imagesteganographylibrary.Utils.Zipping;

import java.io.File;
import java.nio.charset.Charset;

/**
 * This main class of the text steganography
 */
public class ImageSteganography {

    //Tag for Log
    private static final String TAG = ImageSteganography.class.getName();

    private String message;
    private File savedFile;

    public File getSavedFile() {
        return savedFile;
    }

    public void setSavedFile(File savedFile) {
        this.savedFile = savedFile;
    }

    private Bitmap image;
    private Bitmap encoded_image;
    private byte[] encrypted_zip;
    private Boolean encoded;
    private Boolean decoded;

    public ImageSteganography() {
        this.encoded = false;
        this.decoded = false;


        this.message = "";

        this.image = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888);
        this.encoded_image = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888);
        this.encrypted_zip = new byte[0];
    }


    public ImageSteganography(Bitmap image,String message ) {

        this.message = message;
        this.image = image;
//        try {
//            this.encrypted_zip = Zipping.compress(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        this.savedFile = null;
//        this.encrypted_zip = message.getBytes();
        this.encoded = false;
        this.decoded = false;
//        this.secretKeyWrong = true;

        this.encoded_image = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888);

    }



    public String getEncrypted_zip_string()  {
        String str = "";
        str = new String(encrypted_zip, Charset.forName("UTF-8"));
        return str;
//        return  new BigInteger(1, encrypted_zip).toString(16);
    }


    public Bitmap getEncoded_image() {
        return encoded_image;
    }

    public void setEncoded_image(Bitmap encoded_image) {
        this.encoded_image = encoded_image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public byte[] getEncrypted_zip() {
        return encrypted_zip;
    }


    public void setEncrypted_zip(byte[] encrypted_zip) {
        this.encrypted_zip = encrypted_zip;
    }



    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Boolean isEncoded() {
        return encoded;
    }

    public void setEncoded(Boolean encoded) {
        this.encoded = encoded;
    }

    public Boolean isDecoded() {
        return decoded;
    }

    public void setDecoded(Boolean decoded) {
        this.decoded = decoded;
    }

}
