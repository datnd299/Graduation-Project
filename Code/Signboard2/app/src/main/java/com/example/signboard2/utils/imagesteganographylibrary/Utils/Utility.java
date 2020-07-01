package com.example.signboard2.utils.imagesteganographylibrary.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/*
This is the Utility Class containing some useful methods
 */
public class Utility {



    /**
     * This method is used to check whether the string is empty of not
     *
     * @return : true or false {boolean}
     * @parameter : String
     */
    public static boolean isStringEmpty(String str) {
        boolean result = true;

        if (str == null) ;
        else {
            str = str.trim();
            if (str.length() > 0 && !str.equals("undefined"))
                result = false;
        }

        return result;
    }
    public static String md5Bitmap(Bitmap bitmap, int beginRow) {
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, beginRow, bitmap.getWidth(), bitmap.getHeight()-beginRow);
        int bytes = croppedBitmap.getByteCount();

        ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
        croppedBitmap.copyPixelsToBuffer(buffer); //Move the byte data to the buffer

        byte[] array = buffer.array();
        MessageDigest md5Digest = null;
        String result ="";

        try {
            md5Digest = MessageDigest.getInstance("MD5");
        } catch ( NoSuchAlgorithmException e) {
            // error handling here...
        }

        //            result = new String(md5Digest.digest(bitmapBytes), "UTF-8");
        result = new BigInteger(1, md5Digest.digest(array)).toString(16);
        for (int i =32;i>result.length();i++){
            result = "0"+result;
        }
        return result;
    }

    public static String hashBitmap(Bitmap bitmap, int beginRow) {
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, beginRow, bitmap.getWidth(), bitmap.getHeight()-beginRow);
        long hash = 31; //or a higher prime at your choice
        for(int x = 0; x < croppedBitmap.getWidth(); x++){
            for (int y = 0; y < croppedBitmap.getHeight(); y++){
                hash *= (croppedBitmap.getPixel(x,y) + 31);
            }
        }
        return Long.toString(hash);
    }
}
