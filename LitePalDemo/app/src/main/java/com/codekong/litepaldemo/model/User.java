package com.codekong.litepaldemo.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by 尚振鸿 on 2017/9/24. 15:05
 * mail:szh@codekong.cn
 */

public class User extends DataSupport {
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
