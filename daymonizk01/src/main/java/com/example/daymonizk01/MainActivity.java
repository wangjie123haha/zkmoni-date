package com.example.daymonizk01;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;


import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.my_image_view)
    SimpleDraweeView myImageView;
    @BindView(R.id.Birthday)
    EditText birthday;


    private Button btn;
    private Button btn_01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.my_image_view, R.id.Birthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_image_view:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //创建一个视图
                AlertDialog dialog = builder.create();
                //加载视图
                View view1 = View.inflate(MainActivity.this, R.layout.include1, null);
                dialog.setView(view1);
                btn = view1.findViewById(R.id.btn);
                btn_01 = view1.findViewById(R.id.btn_01);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    }
                });
                btn_01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 通过隐式跳转的方式，，去打开相册
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        // 取出相册图片 需要只指定类型
                        intent.setType("image/*");
                        startActivityForResult(intent, 2);
                    }
                });
                dialog.show();
                break;
            case R.id.Birthday:
                //不显示键盘
                birthday.setInputType(InputType.TYPE_NULL); //不显示系统输入键盘
                birthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                 @Override
                public void onFocusChange(View v, boolean hasFocus) {
                 if (hasFocus) {
                   Calendar c = Calendar.getInstance();
                     new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                      @Override
                       public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                           birthday.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth+"日");
                                 }
                                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                              }
                              }
                            });
                      Calendar c = Calendar.getInstance();
                     new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            birthday.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //可用接收回传的数据----取出的资源类型是  bitmap
            Bitmap bitmap = data.getParcelableExtra("data");
            //设置给图片
            myImageView.setImageBitmap(bitmap);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //取出数据，。。。并回显
            Uri uri = data.getData();
            myImageView.setImageURI(uri);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需

    }
}
