package thread;

/**
 * @author zhangliyan by 2017-09-13-下午 1:49
 */
public class TestDem2 {
    public static void main(String[] args) {
        Runnable tr=new MyThread();
        Thread thread=new Thread(tr);
        //thread.setDaemon(true);//设置守护线程
        thread.start();

    }
}
