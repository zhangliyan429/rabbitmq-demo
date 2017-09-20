package com.shsxt.topic;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author zhangliyan by 2017-09-04-下午 6:35
 */
public class AnimalReceive {
    //定义一个exchangename
    private static final String EXCHANGE_NAME="topic_animal";
    public static final String QUEUE_NAME="topic_queue_logs";

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
        //声明exchange,声明一个队列  绑定queue和exchange
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"*.orange.*");
        //定义一个内部类DefaultConmsuler接收消息
        DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                //打印一下内容
                String message=new String(body,"utf-8");
                System.out.println("[x] Received 橙色动物:'"+message+"'");
            }
        };
        //消费
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);
    }
}
