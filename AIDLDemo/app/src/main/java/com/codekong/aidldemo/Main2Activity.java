package com.codekong.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private IMyAidlInterface myAidl;
    private String[] list = new String[20];
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (myAidl == null){
                myAidl = IMyAidlInterface.Stub.asInterface(service);
                try {
                    Toast.makeText(Main2Activity.this, "原来的值 = " + list[0], Toast.LENGTH_SHORT).show();
                    myAidl.getaList(list);
                    Toast.makeText(Main2Activity.this, "收到服务端改变的值 = " + list[0], Toast.LENGTH_LONG).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initEvent();
    }


    private void initEvent() {
        findViewById(R.id.id_test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myAidl == null){
                    changeList();
                    bindAidlService();
                }else{
                    changeList();
                }
            }
        });
    }


    private void changeList() {
        list[0] = "123";
    }


    private void bindAidlService() {
        Intent intent = new Intent();
        intent.setAction("com.codekong.aidlservice.service.aidl");
        intent.setPackage("com.codekong.aidlserver");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
}
