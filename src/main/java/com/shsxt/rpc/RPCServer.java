package com.shsxt.rpc;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author zhangliyan by 2017-09-04-下午 4:38
 */
public class RPCServer {
    //定义一个exchangename
    public static final String QUEUE_NAME="rpc_queue";
    /*public static final String CALLBACK_QUEUE_NAME="rpc_callback_queue";*/
    private static int fib(int n){
        if (n ==0){
            return 0;
        }
        if (n ==1){
            return 1;
        }
        return fib(n -2)+fib(n-1);
    }

    public static void main(String[] args) throws Exception {
        //建立连接
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("liyushuai");
        connectionFactory.setPassword("111111");
        Connection connection=connectionFactory.newConnection();
        final Channel channel=connection.createChannel();
        //声明队列
        //声明Exchange/声明一个队列  绑定queue和Exchange
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //定义一个内部类DefaultConsumer接收信息
        DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                //打印一下内容
                String message=new String(body,"utf-8");
                System.out.println("接收客户端的信息是:"+message);
                int result=fib(Integer.parseInt(message));
                //把内容发送到callback的队列里面去
                String correlationId=properties.getCorrelationId();//标记id
                String callbackQueue=properties.getReplyTo();//回调队列名词
                AMQP.BasicProperties basicProperties=new AMQP.BasicProperties().
                        builder().correlationId(correlationId).build();
                //发送
                channel.basicPublish("",callbackQueue,basicProperties,(result+"").getBytes());
                System.out.println("返回值为: "+result);
            }
        };
        //消费
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);
    }

}
