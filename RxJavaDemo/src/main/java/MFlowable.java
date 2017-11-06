import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by 尚振鸿 on 17-10-28. 19:40
 * mail:szh@codekong.cn
 */

public class MFlowable {
    public static void main(String[] args) {
        demo1();
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
        //.subscribeOn(Schedulers.newThread())
        //.observeOn(Schedulers.newThread())
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
