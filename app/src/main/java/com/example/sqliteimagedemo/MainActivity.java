package com.example.sqliteimagedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);

        //create database
        db = this.openOrCreateDatabase("test.db", Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists imageTb ( a blob )");
    }

    //getting images
    public void get(View view)
    {
        //select the image
        Cursor c = db.rawQuery("select * from imageTb", null);
        if(c.moveToNext())
        {
            byte[] image = c.getBlob(0);
            Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
            imageView.setImageBitmap(bmp);
            Toast.makeText(this,"Done", Toast.LENGTH_SHORT).show();

        }
    }

    //saving files sd card
    public  void save(View view) throws IOException {
        //input stream
        FileInputStream fis = new FileInputStream("/storage/sdcard/demoImage.jpg");
        byte[] image= new byte[fis.available()];

        //reading image
        fis.read(image);
        ContentValues values = new ContentValues();
        values.put("a",image);
        //inserting
        db.insert("imageTb", null,values);
        fis.close();
        Toast.makeText(this,"Done", Toast.LENGTH_SHORT).show();
    }
}
