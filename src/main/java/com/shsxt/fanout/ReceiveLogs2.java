package com.shsxt.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author zhangliyan by 2017-09-04-下午 3:40
 */
public class ReceiveLogs2{
    //定义一个exchangename
    public  static  final String EXCHANGE_NAME="logs";
    public static final String QUEUE_NAME="fanout_logs2";

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
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");//fanout就是广播模式
        //声明Exchage/声明一个队列  绑定queue和Exchange
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        //定义一个内部类DefaultConsumer接收信息
        DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                //打印一下内容
                String message=new String(body,"utf-8");
                System.out.println("[x] Received '"+message+"'");
            }
        };
        //消费
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);
    }

}
