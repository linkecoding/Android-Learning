/**
 * Created by 尚振鸿 on 17-10-26. 19:05
 * mail:szh@codekong.cn
 */

public class Main {
    public static void main(String[] args) {
        new MyThread().start();
    }
}

class MyThread extends Thread{
    @Override
    public void run() {
        super.run();
        System.out.println(Thread.currentThread().getName());
    }
}
