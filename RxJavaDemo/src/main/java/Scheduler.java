import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 尚振鸿 on 17-10-26. 18:07
 * mail:szh@codekong.cn
 */

public class Scheduler {
    public static void main(String[] args) {
        oneThread();
    }

    /**
     * 发射数据在一个现场,
     * 处理数据在另一个线程
     */
    private static void oneThread() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            System.out.println(Thread.currentThread().getName() + " 发射数据 " + i);
                            Thread.sleep(1000);
                            e.onNext(i);
                        }
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Thread.sleep(2000);
                        System.out.println(Thread.currentThread().getName() + " 接收数据 " + integer);
                    }
                });
    }
}

