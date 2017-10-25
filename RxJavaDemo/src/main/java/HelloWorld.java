import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by 尚振鸿 on 17-10-25. 14:35
 * mail:szh@codekong.cn
 *
 */

public class HelloWorld {
    public static void main(String[] args) {
        //hello();
        //just();
        /*
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        fromIterable(list);
        */
        //rang();
        //filter();

        //distinct();
        //map();
        //flatMap();
        //mergeWith();

        //concatWith();
        zipWith();
    }

    /**
     * 订阅
     */
    private static void hello() {
        //创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onNext("4");
                e.onNext("5");
                e.onNext("6");
                e.onComplete();
            }
        });

        //创建观察者
        Observer<String> observer = new Observer<String>() {
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
                if (s.equals("4")){
                    // mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        //订阅事件
        observable.subscribe(observer);
    }

    /**
     * just可以传递
     * 单个数据
     * 单个字符/对象
     * 一个数组/集合
     *
     * just 同样可以定义异常和完成状态
     */
    private static void just() {
        Observable.just(new Integer[]{1,2}).subscribe(new Consumer<Integer[]>() {
            @Override
            public void accept(Integer[] integers) throws Exception {
                System.out.println(integers.length);
            }
        });

        Observable.just(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("收到: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("错误了");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("接收完成");
            }
        });
    }

    /**
     * 输出可迭代对象
     * @param list
     */
    private static void fromIterable(List<String> list){
        Observable.fromIterable(list).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("accept: " + s);
            }
        });
    }

    /**
     * 发送一个范围内的整数序列
     */
    private static void rang() {
        Observable.range(2, 9).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("accept: " + integer);
            }
        });
    }

    /**
     * 对发射的数据进行过滤操作
     */
    private static void filter() {
        Observable.range(1, 10).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer % 2 == 0;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("accept: " + integer);
            }
        });
    }

    /**
     * 对发射的数据进行去重
     */
    private static void distinct(){
        Observable.just(1, 2, 2, 8, 12, 12)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("accept: " + integer);
                    }
                });
    }


    /**
     * 对发射的数据进行指定的变换
     */
    private static void map() {
        Observable.just(1, 2, 3, 4, 5)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + "^2 = " + (integer * integer);
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("map accept " + s);
            }
        });
    }

    /**
     * 将一个发射数据的Observable变换为多个Observable，
     * 然后将多个Observable发射的数据合并到一个Observable中进行发射
     * 对原始数据进行展开铺平
     */
    private static void flatMap() {
        Integer[] array1 = new Integer[]{1, 2, 3, 4, 5};
        Integer[] array2 = new Integer[]{6, 7, 8, 9, 10};
        Integer[] array3 = new Integer[]{11, 12, 13, 14, 15};
        Observable.just(array1, array2, array3)
                .flatMap(new Function<Integer[], ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer[] integers) throws Exception {
                        return Observable.fromArray(integers);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("flatMap accept: " + integer);
                    }
                });
    }


    /**
     * 将两股要发射的数据进行合并,可能会造成数据的交错
     */
    private static void mergeWith() {
        Integer[] nums = new Integer[]{5, 6, 7, 8};
        Observable.just(1, 2, 3, 4)
                .mergeWith(Observable.fromArray(nums))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("merge accept " + integer);
                    }
                });
    }

    /**
     * 将两股要发射的数据进行连接,不会造成数据的交错
     */
    private static void concatWith() {
        Integer[] nums = new Integer[]{5, 6, 7, 8};
        Observable.just(1, 2, 3, 4)
                .concatWith(Observable.fromArray(nums))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("concat accept " + integer);
                    }
                });
    }


    /**
     * 将多个Obversable发射的数据，
     * 通过一个函数BiFunction对对应位置的数据处理后放到一个新的Observable中发射，
     * 所发射的数据个数与最少的Observabel中的一样多。
     * 对两股数据进行聚合
     */
    private static void zipWith() {
        String[] colors = new String[]{"红", "橙", "黄", "绿"};
        Observable.just(1,2,3)
                .zipWith(Observable.fromArray(colors), new BiFunction<Integer, String, String>() {
                    @Override
                    public String apply(Integer integer, String s) throws Exception {
                        return integer + " " + s;
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

}