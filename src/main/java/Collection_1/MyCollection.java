package Collection_1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author zhangliyan by 2017-09-15-上午 11:17
 */
public class MyCollection {
    public static void main(String[] args) {
    Collection<Integer> col=new ArrayList<Integer>();
    col.add(1);
    col.add(2);
    col.add(3);
    Iterator<Integer> iterator=col.iterator();
    while (iterator.hasNext()){
        Integer num=iterator.next();
        System.out.println("值为:"+num);
        System.out.println(num.SIZE);
    }
       // Collections
    }


}
