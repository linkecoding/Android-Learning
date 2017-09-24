package com.codekong.aidlserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codekong.aidldemo.SsoAuth;

import java.util.List;

/**
 * Created by 尚振鸿 on 2017/9/22. 10:25
 * mail:szh@codekong.cn
 */

public class SinaSsoService extends Service {
    private static final String TAG = "SinaSsoService";

    SinaSsoImpl mBinder = new SinaSsoImpl();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class SinaSsoImpl extends SsoAuth.Stub{


        @Override
        public void basicTypes(byte aByte, char aChar, int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString, List<String> aList) throws RemoteException {

        }

        @Override
        public void ssoAuth(String username, String password) throws RemoteException {
            Log.d(TAG, "ssoAuth: 这里是新浪客户端SSO登录,用户名 " + username + "  密码 " + password);
        }
    }
}
