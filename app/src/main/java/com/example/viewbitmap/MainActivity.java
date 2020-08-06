package com.example.viewbitmap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout layout;
    private Button button0,button1,button2,button3,button4,button5,button6,button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout);
        button0 = findViewById(R.id.butt0);
        button1 = findViewById(R.id.butt1);
        button2 = findViewById(R.id.butt2);
        button3 = findViewById(R.id.butt3);
        button4 = findViewById(R.id.butt4);
        button5 = findViewById(R.id.butt5);
        button6 = findViewById(R.id.butt6);
        button7 = findViewById(R.id.butt7);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //新建画笔，默认style为实心
        final Paint paint = new Paint();
        //设置颜色，颜色可用Color.parseColor("#6b99b9")代替
        paint.setColor(Color.parseColor("#006b99b9"));
        //设置透明度
        paint.setAlpha(80);
        //抗锯齿
        paint.setAntiAlias(true);
        //画笔粗细大小
        paint.setTextSize(ViewBitmap.sp2px(getApplicationContext(),30));
        switch (view.getId()){
            case R.id.butt0:
                if ( ViewBitmap.getInstance(getApplicationContext(),"文休","daragtag/hugh",paint).getViewBitmap(layout)){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.butt1:
                if ( ViewBitmap.getInstance(getApplicationContext(),"文休","daragtag/hugh",paint).getViewBitmap(layout,-30,ViewBitmapStyle.CENTER)){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.butt2:
                if ( ViewBitmap.getInstance(getApplicationContext(),"文休","daragtag/hugh",paint).getViewBitmap(layout,-30,ViewBitmapStyle.TOP_LEFT)){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.butt3:
                if ( ViewBitmap.getInstance(getApplicationContext(),"文休","daragtag/hugh",paint).getViewBitmap(layout,-30,ViewBitmapStyle.LOWER_LEFT)){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.butt4:
                if ( ViewBitmap.getInstance(getApplicationContext(),"文休","daragtag/hugh",paint).getViewBitmap(layout,-30,ViewBitmapStyle.TOP_RIGHT)){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.butt5:
                if ( ViewBitmap.getInstance(getApplicationContext(),"文休","daragtag/hugh",paint).getViewBitmap(layout,-30,ViewBitmapStyle.LOWER_RIGET)){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.butt6:
                if ( ViewBitmap.getInstance(getApplicationContext(),"文休","daragtag/hugh",paint).getViewBitmap(layout,-30,ViewBitmapStyle.TOP_CENTER)){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.butt7:
                if ( ViewBitmap.getInstance(getApplicationContext(),"文休","daragtag/hugh",paint).getViewBitmap(layout,-30,ViewBitmapStyle.LOWER_CENTER)){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
