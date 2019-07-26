package com.xq.ai;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.xq.zxing.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

public class PermissionsActivity extends AppCompatActivity {


    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.VIBRATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
    };

    List<String> mPermissionList = new ArrayList<>();

    private static final int PERMISSION_CODE = 999;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        for(int i = 0;i<permissions.length;i++){
            if(ActivityCompat.checkSelfPermission(PermissionsActivity.this,permissions[i]) != PackageManager.PERMISSION_GRANTED){
                mPermissionList.add(permissions[i]);
            }
        }

        if(mPermissionList.size()>0){
            //请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]); //将List转为数组
            ActivityCompat.requestPermissions(PermissionsActivity.this,permissions,PERMISSION_CODE);
        }else {
            startActivity(new Intent(PermissionsActivity.this,MainActivity.class));
            finish();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        if (requestCode == PERMISSION_CODE){
            startActivity(new Intent(PermissionsActivity.this,MainActivity.class));
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
