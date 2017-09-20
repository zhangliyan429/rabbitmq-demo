package com.shsxt.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * @author zhangliyan by 2017-09-04-下午 3:42
 */
public class EmitLog {
    //定义一个exchangename
    public  static  final String EXCHANGE_NAME="logs";
    public static void main(String[] args) throws Exception {
        //建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("liyushuai");
        connectionFactory.setPassword("111111");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//fanout就是广播模式
        System.out.println("请输入内容");
        //String message="不要让自己后悔";
        //channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        //System.out.println("发送消息");
        //channel.close();
        //connection.close();
        while (true){
            //发送消息
            Scanner scanner=new Scanner(System.in);
            String message=scanner.nextLine();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            if ("q".equals(message)){
                channel.close();
                connection.close();
            }
        }
    }
}
