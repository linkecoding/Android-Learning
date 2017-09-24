package com.codekong.aidlserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codekong.aidldemo.IMyAidlInterface;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 尚振鸿 on 2017/9/23. 16:03
 * mail:szh@codekong.cn
 */

public class AidlService extends Service {
    private IBinder mBinder = new IMyAidlInterface.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString, List<String> aList) throws RemoteException {

        }

        @Override
        public void getaList(String[] list) throws RemoteException {
            if (list[0] == null){
                Log.d(TAG, "getaList: 服务端得不到值, 服务端开始设置值 ABC");
                list[0] = "ABC";
            }else{
                Log.d(TAG, "getaList: 服务端得到值 = " + list[0]);
            }
        }

        @Override
        public void setaList(String[] list) throws RemoteException {

        }

        @Override
        public void getAndSetList(String[] list) throws RemoteException {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
