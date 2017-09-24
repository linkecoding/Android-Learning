// IMyAidlInterface.aidl
package com.codekong.aidldemo;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString, out List<String> aList);

    void getaList(out String[] list);
    void setaList(in String[] list);
    void getAndSetList(inout String[] list);
}