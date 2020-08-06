package com.example.viewbitmap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout layout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout);
        textView = findViewById(R.id.tv);

        //新建画笔，默认style为实心
        final Paint paint = new Paint();
        //设置颜色，颜色可用Color.parseColor("#6b99b9")代替
        paint.setColor(Color.parseColor("#006b99b9"));
        //设置透明度
        paint.setAlpha(80);
        //抗锯齿
        paint.setAntiAlias(true);
        //画笔粗细大小
        paint.setTextSize(ViewBitmap.sp2px(getApplicationContext(),100));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ViewBitmap.getInstance(getApplicationContext(),"文休","daragtag/hugh",paint).getViewBitmap(layout,1)){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
