package com.example.signboard2.utils.imagesteganographylibrary.Text;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.example.signboard2.utils.Utils;
import com.example.signboard2.utils.imagesteganographylibrary.Utils.Utility;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import androidx.annotation.RequiresApi;
import okhttp3.internal.Util;

class EncodeDecode {

    private static final String TAG = EncodeDecode.class.getName();
    //start and end message constants
    private static final String END_MESSAGE_COSTANT = "#!@";
    private static final String START_MESSAGE_COSTANT = "@!#";


    /**
     * This method represent the core of 2 bit Encoding
     *
     * @return : byte encoded pixel array
     * @parameter :  integer_pixel_array {The integer RGB array}
     * @parameter : image_columns {Image width}
     * @parameter : image_rows {Image height}
     * @parameter : messageEncodingStatus {object}
     * @parameter : progressHandler {A handler interface, for the progress bar}
     */





    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static File encodeMessage(Bitmap bmp,
                                       String encrypted_message, ProgressHandler progressHandler) {



        Bitmap bitmap = bmp.copy(bmp.getConfig(), true);
        //Message Encoding Status


        //Progress Handler
        if (progressHandler != null) {
            progressHandler.setTotal(encrypted_message.getBytes(Charset.forName("ISO-8859-1")).length);
        }


            //getting bitmap height and width
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int usingPixels = (encrypted_message.length()*8+128+24)/3+1;
            int usedBit=0;
            int usingRows = usingPixels/width;
        if(usingPixels%width>0){
            usingRows+=1;
        }
            encrypted_message = START_MESSAGE_COSTANT + encrypted_message+"___"+Utility.md5Bitmap(bitmap,usingRows)+END_MESSAGE_COSTANT;

            byte[] byte_encrypted_message = encrypted_message.getBytes(Charset.forName("ISO-8859-1"));
            MessageEncodingStatus message = new MessageEncodingStatus(byte_encrypted_message, encrypted_message);

            for (int row = 0; row <usingRows ; row++) {
                for (int col = 0; col <height ; col++) {

                    if(message.isMessageEncoded()){
                        break;
                    }
                    int oldColor= bitmap.getPixel(col,row);


                    int red = Color.red(oldColor);
                    int blue = Color.blue(oldColor);
                    int green = Color.green(oldColor);
                    int alpha = Color.alpha(oldColor);


                    int byteIndex = usedBit/8;
                    int newPixel = oldColor;

                   int newRed = (red>>1<<1)|(((byte)((byte)(byte_encrypted_message[usedBit/8]<<(usedBit%8))>>(7)))&1);
                    newPixel = Color.argb(alpha,newRed,green,blue);
                    bitmap.setPixel(col,row,newPixel);
                    if (progressHandler != null)
                        progressHandler.increment(1);
                   if(usedBit++>=encrypted_message.length()*8-1){

                           message.setMessageEncoded();
//                           if (progressHandler != null)
//                               progressHandler.finished();
                    break;

                   }
                    int newGreen = (green>>1<<1)|(((byte)((byte)(byte_encrypted_message[usedBit/8]<<(usedBit%8))>>(7)))&1);
                    newPixel = Color.argb(alpha,newRed,newGreen,blue);
                    bitmap.setPixel(col,row,newPixel);
                    if (progressHandler != null)
                        progressHandler.increment(1);
                    if(usedBit++>=encrypted_message.length()*8-1){
                        message.setMessageEncoded();

//                        if (progressHandler != null)
//                            progressHandler.finished();
                        break;
                    }
                    int newBlue = (blue>>1<<1)|(((byte)((byte)(byte_encrypted_message[usedBit/8]<<(usedBit%8))>>(7)))&1);
                    newPixel = Color.argb(alpha,newRed,newGreen,newBlue);
                    bitmap.setPixel(col,row,newPixel);
                    if (progressHandler != null)
                        progressHandler.increment(1);
                    if(usedBit++>=encrypted_message.length()*8-1){
                        message.setMessageEncoded();
//                        if (progressHandler != null)
//                            progressHandler.finished();
                        break;
                    }



                }


        }

        File file = Utils.saveImage(bitmap);
            progressHandler.finished();

        return file;
    }



    //Progress handler class
    public interface ProgressHandler {

        void setTotal(int tot);

        void increment(int inc);

        void finished();
    }

    private static class MessageDecodingStatus {

        private String message;
        private boolean ended;

        MessageDecodingStatus() {
            message = "";
            ended = false;
        }

        boolean isEnded() {
            return ended;
        }

        void setEnded() {
            this.ended = true;
        }

        String getMessage() {
            return message;
        }

        void setMessage(String message) {
            this.message = message;
        }


    }

    private static class MessageEncodingStatus {
        private boolean messageEncoded;
        private int currentMessageIndex;
        private byte[] byteArrayMessage;
        private String message;

        MessageEncodingStatus(byte[] byteArrayMessage, String message) {
            this.messageEncoded = false;
            this.currentMessageIndex = 0;
            this.byteArrayMessage = byteArrayMessage;
            this.message = message;
        }

        void incrementMessageIndex() {
            currentMessageIndex++;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        boolean isMessageEncoded() {
            return messageEncoded;
        }

        void setMessageEncoded() {
            this.messageEncoded = true;
        }

        int getCurrentMessageIndex() {
            return currentMessageIndex;
        }

        public void setCurrentMessageIndex(int currentMessageIndex) {
            this.currentMessageIndex = currentMessageIndex;
        }

        byte[] getByteArrayMessage() {
            return byteArrayMessage;
        }

        public void setByteArrayMessage(byte[] byteArrayMessage) {
            this.byteArrayMessage = byteArrayMessage;
        }
    }

}
