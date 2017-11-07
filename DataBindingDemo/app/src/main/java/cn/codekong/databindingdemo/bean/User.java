package cn.codekong.databindingdemo.bean;

/**
 * Created by 尚振鸿 on 17-11-7. 10:55
 * mail:szh@codekong.cn
 */

public class User {


    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
