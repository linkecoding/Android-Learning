// SsoAuth.aidl
package com.codekong.aidldemo;

// Declare any non-default types here with import statements

interface SsoAuth {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(byte aByte, char aChar, int anInt, long aLong,
    boolean aBoolean, float aFloat, double aDouble, String aString,
    in List<String> aList);

    /**
    * 实现SSO授权
    */
    void ssoAuth(String username, String password);
}
