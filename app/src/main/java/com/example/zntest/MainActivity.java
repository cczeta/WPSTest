package com.example.zntest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt_word;
    private Button bt_excel;

    private EditText et_file_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();

        //设置按键监听
        bt_word.setOnClickListener(this);
        bt_excel.setOnClickListener(this);

    }

    //初始化控件
    private void intiView() {
        bt_word = (Button)findViewById(R.id.bt_word);
        bt_excel = (Button)findViewById(R.id.bt_excel);
        et_file_name = (EditText)findViewById(R.id.et_file_name);
    }


    //通过WPS打开文件
    private void openWithWPS(File file){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName("cn.wps.moffice_eng", "cn.wps.moffice.documentmanager.PreStartActivity2");
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        startActivity(intent);
    }

    private void createFile(String name){
        //判断SD卡状态(是否挂载)
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            try {
                File word = new File(Environment.getExternalStorageDirectory(), name);

                //判断新建的文件是否存在
                if(!word.exists()){
                    word.createNewFile();
                    openWithWPS(word);
                }else {
                    Toast.makeText(MainActivity.this, name+"文件已存在!", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(MainActivity.this, name + "新建完成！", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "未找到外部存储,无法创建", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_word:
                String file_name_word = et_file_name.getText().toString() + ".docx";
                createFile(file_name_word);
                break;
            case R.id.bt_excel:
                String file_name_excel = et_file_name.getText().toString() + ".xls";
                createFile(file_name_excel);
                break;


        }
    }
}
