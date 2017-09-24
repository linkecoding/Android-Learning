package com.codekong.litepaldemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codekong.litepaldemo.R;
import com.codekong.litepaldemo.model.User;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.id_user_id_ed)
    EditText mIdUserIdEd;
    @BindView(R.id.id_user_name_ed)
    EditText mIdUserNameEd;
    @BindView(R.id.id_user_password_ed)
    EditText mIdUserPasswordEd;
    @BindView(R.id.id_add_user_btn)
    Button mIdAddUserBtn;
    @BindView(R.id.id_delete_user_btn)
    Button mIdDeleteUserBtn;
    @BindView(R.id.id_modify_user_btn)
    Button mIdModifyUserBtn;
    @BindView(R.id.id_select_user_btn)
    Button mIdSelectUserBtn;
    @BindView(R.id.id_select_user_res_tv)
    TextView mIdSelectUserResTv;

    private Unbinder mUnbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.id_add_user_btn, R.id.id_delete_user_btn, R.id.id_modify_user_btn, R.id.id_select_user_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_add_user_btn:
                addUser();
                break;
            case R.id.id_delete_user_btn:
                deleteUserById();
                break;
            case R.id.id_modify_user_btn:
                modifyUserById();
                break;
            case R.id.id_select_user_btn:
                selectUserById();
                break;
        }
    }

    private void selectUserById() {
        User user = DataSupport.find(User.class,
                Long.parseLong(mIdUserIdEd.getText().toString()));
        mIdSelectUserResTv.setText("用户名：" + user.getUsername() + "\r\n"
        + "密码：" + user.getPassword());
    }

    private void modifyUserById() {
        User user = new User();
        user.setPassword(mIdUserPasswordEd.getText().toString());
        int res = user.update(Long.parseLong(mIdUserIdEd.getText().toString()));
        if (res > 0){
            showToast("更新成功");
        }else{
            showToast("更新失败");
        }
    }

    private void deleteUserById() {
        int res = DataSupport.delete(User.class, Long.parseLong(mIdUserIdEd.getText().toString()));
        if (res > 0){
            showToast("删除成功");
        }else{
            showToast("删除失败");
        }

    }

    private void addUser() {
        User user = new User();
        user.setUsername(mIdUserNameEd.getText().toString());
        user.setPassword(mIdUserPasswordEd.getText().toString());
        boolean flag = user.save();
        if (flag){
            showToast("添加成功");
        }else{
            showToast("添加失败");
        }
    }

    public void showToast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
