package com.shsxt.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.concurrent.TimeoutException;

/**
 * @author zhangliyan by 2017-09-02-下午 3:02
 */
public class Sending {
    //设置队列名称
    private static final String QUEUE_NAME="liyushuai";

    public static void main(String[] args) throws Exception, TimeoutException {
        //连接工厂
        ConnectionFactory factory=new ConnectionFactory();

        //设置主机
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("liyushuai");//用户名
        factory.setPassword("111111");//密码
        //创建连接
        Connection connection=factory.newConnection();
        //创建一个通道
        Channel channel=connection.createChannel();
        //声明一个队列并且发送消息给这个队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        String message="李玉帅,对我最好的人";
        channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        System.out.println("发送成功");
        //关闭连接
        channel.close();
        connection.close();
    }
}
