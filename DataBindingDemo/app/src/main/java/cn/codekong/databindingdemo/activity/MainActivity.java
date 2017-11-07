package cn.codekong.databindingdemo.activity;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.codekong.databindingdemo.BR;
import cn.codekong.databindingdemo.R;
import cn.codekong.databindingdemo.bean.User;
import cn.codekong.databindingdemo.databinding.ActivityMainBinding;

/**
 * @author szh
 */
public class MainActivity extends AppCompatActivity {
    User mUser = new User("xiaohong", "123456");
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        // 两种绑定方法
        // binding.setUser(mUser);
        binding.setVariable(BR.user, mUser);

        binding.setPresenter(new Presenter());
    }


    public class Presenter{
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mUser.setPassword(s.toString());
            binding.setUser(mUser);
        }

        public void onClick(View view){
            Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_LONG).show();
        }

        public void onClickUserName(User user){
            Toast.makeText(MainActivity.this, "点击了 " + user.getUsername(), Toast.LENGTH_LONG).show();
        }
    }
}
