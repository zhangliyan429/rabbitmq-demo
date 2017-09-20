package thread;

/**
 * @author zhangliyan by 2017-09-13-下午 2:27
 */
//测试入口
public class DeMain {
    public static void main(String[] args) {
        try {
            PublicVar publicVar=new PublicVar();
            ThreadA threadA=new ThreadA(publicVar);
            threadA.start();
            threadA.sleep(200);
            publicVar.getValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
