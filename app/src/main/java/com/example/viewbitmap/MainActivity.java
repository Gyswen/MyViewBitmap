package com.example.viewbitmap;

import androidx.appcompat.app.AppCompatActivity;

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

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ViewBitmap.getInstance(getApplicationContext(),"文休","daragtag/hugh").getViewBitmap(layout)){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
