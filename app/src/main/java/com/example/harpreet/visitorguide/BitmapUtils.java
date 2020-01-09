package com.example.harpreet.visitorguide;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class BitmapUtils {
    private BitmapUtils(){}

    public static byte[] convertBitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }finally {
            if(baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    Log.e(BitmapUtils.class.getSimpleName(), "ByteArrayOutputStream was not closed");
                }
            }
        }
    }
    public static byte[] convertBitmapToByteArrayUncompressed(Bitmap bitmap){
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        return byteBuffer.array();
    }

    public static Bitmap convertCompressedByteArrayToBitmap(byte[] src){
        return BitmapFactory.decodeByteArray(src, 0, src.length);
    }

    public static void storePhotoOnDisk(final Bitmap capturedBitmap) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                File folder = new File(Environment.getExternalStorageDirectory() +
                        File.separator + "MyFolder");
                Log.e("PictureDemo", "here");

                folder.mkdirs();

                String format = "forML";
                File photoFile = new File(folder, format.concat(".jpg"));

                if (photoFile.exists()) {
                    photoFile.delete();
                }

                try {
                    FileOutputStream fos = new FileOutputStream(photoFile.getPath());

                    capturedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    Log.e("PictureDemo", "Exception in photoCallback", e);
                }

            }
        }).start();
    }

}