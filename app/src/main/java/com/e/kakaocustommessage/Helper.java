package com.e.kakaocustommessage;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Helper extends AppCompatActivity {

    private Uri getUriFromFilePath(String filePath){
        long photoId;
        Uri photoUri = MediaStore.Images.Media.getContentUri("external");
        String[] projection = {MediaStore.Images.ImageColumns._ID};
        Cursor cursor = getContentResolver().query(photoUri, projection, MediaStore.Images.ImageColumns.DATA + " LIKE ?", new String[] { filePath }, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(projection[0]);
        photoId = cursor.getLong(columnIndex);

        cursor.close();
        return Uri.parse(photoUri.toString() + "/" + photoId);

    }
//    private Uri getUriFromPath(String filePath) {
//        long photoId;
//        Uri photoUri = MediaStore.Images.Media.getContentUri("external");
//        String[] projection = {MediaStore.Images.ImageColumns._ID};
//        Cursor cursor = getContentResolver().query(photoUri, projection, MediaStore.Images.ImageColumns.DATA + " LIKE ?", new String[] { filePath }, null);
//        cursor.moveToFirst();
//
//        int columnIndex = cursor.getColumnIndex(projection[0]);
//        photoId = cursor.getLong(columnIndex);
//
//        cursor.close();
//        return Uri.parse(photoUri.toString() + "/" + photoId);
//    }

    public String getUniqueID() {
        Random r = new Random();

        StringBuilder sb = new StringBuilder(10);

        for(int i = 0; i < 10; i++) {
            char tmp = (char) ('a' + r.nextInt('z' - 'a'));
            sb.append(tmp);
        }

        return sb.toString() + String.valueOf(r.nextInt()) + String.valueOf(r.nextInt());
    }

}
