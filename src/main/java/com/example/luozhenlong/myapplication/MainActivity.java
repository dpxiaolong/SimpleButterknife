package com.example.luozhenlong.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import injectorFrame.BindOnclick;
import injectorFrame.BindOnlongClick;
import injectorFrame.BindString;
import injectorFrame.BindView;
import injectorFrame.SimpleButterKnife;

public class MainActivity extends AppCompatActivity {

    //2.1，注入id，相当于mButton执行了findviewbyid
    //注意，这里的成员变量需要时public类型，因为是基于反射获取MainActivity的所有成员变量
    @BindView(R.id.btn_test)
    public Button mButton;
    @BindView(R.id.btn_test2)
    public Button mButton2;
    //2.2，注入String类型id，相当于mName执行了getString
    @BindString(R.string.name)
    public String mName;


    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 初始化注入到这个acitivty
        SimpleButterKnife.bind(this);

        Log.i(TAG,"bindSting result"+mName);

    }

    //2.3 注入onclick，相当于对id的所有控件设置了onclick方法
    @BindOnclick({R.id.btn_test,R.id.btn_test2})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.btn_test:
                Toast.makeText(MainActivity.this,"btn1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_test2:
                Toast.makeText(MainActivity.this,"btn2",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    //2.4 注入onLongclick，相当于对id的所有控件设置了onLongclick方法
    @BindOnlongClick(R.id.btn_test2)
    public void onLongClick(View view){
        switch (view.getId()){
            case R.id.btn_test2:
                Toast.makeText(MainActivity.this,"btn2 long click",Toast.LENGTH_SHORT).show();
                break;
        }

    }

}
