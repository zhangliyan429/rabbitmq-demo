package com.shsxt.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * @author zhangliyan by 2017-09-04-下午 6:34
 */
public class AnimalEmit {
    //定义一个exchangename
    private static final String EXCHANGE_NAME="topic_animal";

    public static void main(String[] args) throws Exception {
        //建立连接
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("liyushuai");
        connectionFactory.setPassword("111111");
        Connection connection=connectionFactory.newConnection();
        Channel channel=connection.createChannel();
        //声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");// topic要求bindingkey与routingkey模糊匹对
        System.out.println("请输入内容:");
        while(true){
            //发送消息
            Scanner scanner=new Scanner(System.in);
            String message=scanner.nextLine();// abc#message
            if (!message.contains("#")){
                channel.close();
                connection.close();
            }
            String[] messageArr=message.split("#");
            channel.basicPublish(EXCHANGE_NAME,messageArr[0],null,messageArr[1].getBytes());
        }
    }
}
