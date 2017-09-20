package thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zhangliyan by 2017-09-13-下午 1:45
 */
public class MyThread implements Runnable{
    public void run() {
        try {
            Thread.sleep(1000);//守护线程阻塞1秒后运行
            File f=new File("daemon.txt");
            FileOutputStream os=new FileOutputStream(f,true);
            os.write("daemon".getBytes());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
