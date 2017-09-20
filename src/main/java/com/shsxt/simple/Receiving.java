package com.shsxt.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author zhangliyan by 2017-09-02-下午 5:13
 */
public class Receiving {
    //设置队列名称
    private static final String QUEUE_NAME="liyushuai";

    public static void main(String[] args) throws Exception {
        //建立连接
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("liyushuai");//用户名
        factory.setPassword("111111");//密码
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
        //声明一个队列并且发送消息给这个队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //定义一个内部类DefaultComsuler接收消息
        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(consumerTag+"\r\n"+envelope+"\r\n"+properties+"\r\n"+body);
                //打印一下内容
                String message=new String(body,"utf-8");
                System.out.println("[x] Received'"+message+"'");
            }
        };
         //CosumerOwner consumer = new CosumerOwner(channel);
        // 调用消费方法
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
