package com.codekong.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SsoAuth mSsoAuth;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            //建立连接之后将Binder转化为mSsoAuth
            mSsoAuth = SsoAuth.Stub.asInterface(service);
            doAuth();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mSsoAuth = null;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.id_start_sso_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: 点击事件");
                if(mSsoAuth == null){
                    //绑定远程服务进行登录
                    bindSsoAuthService();
                }else{
                    //直接进行登录
                    doAuth();
                }
            }
        });
    }

    private void doAuth() {
        try {
            mSsoAuth.ssoAuth("xiaohong", "xiaohong");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private void bindSsoAuthService() {
        Intent intent = new Intent();
        intent.setAction("com.codekong.aidlserver.service.SinaSsoAuthService");
        intent.setPackage("com.codekong.aidlserver");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
