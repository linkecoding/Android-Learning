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
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick({R.id.id_add_user_btn, R.id.id_delete_user_btn, R.id.id_modify_user_btn, R.id.id_select_user_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_add_user_btn:
                demo1();
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
            default:
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

    /**
     * Flowable基础代码
     */
    private static void demo1() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                System.out.println("发射数据1");
                e.onNext(1);
                System.out.println("发射数据2");
                e.onNext(2);
                System.out.println("发射数据3");
                e.onNext(3);
                System.out.println("发射数据4");
                e.onNext(4);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("接收到数据 " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("接收完成");
                    }
                });
    }
}
