package com.shsxt.rpc;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;

/**
 * @author zhangliyan by 2017-09-04-下午 4:38
 */
public class RPCClient {
    //定义一个exchangename
    public static final String QUEUE_NAME="rpc_queue";
    public  static  final String CALLBACK_QUEUE_NAME="rpc_callback_queue";

    public static void main(String[] args) throws Exception {
        //建立连接
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("liyushuai");
        connectionFactory.setPassword("111111");
        Connection connection=connectionFactory.newConnection();
        Channel channel=connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //定义一个BasicProperties
        final String correlationId= UUID.randomUUID().toString();
        //声明回调队列
        channel.queueDeclare(CALLBACK_QUEUE_NAME,false,false,false,null);
        AMQP.BasicProperties basicProperties=new AMQP.BasicProperties().builder()
                .replyTo(CALLBACK_QUEUE_NAME).correlationId(correlationId).build();
        channel.basicPublish("",QUEUE_NAME,basicProperties,"3".getBytes());
        System.out.println("请求参数是:"+3);
        //接收对调内容
        //定义消息消费对象
        DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                //打印一下内容
                String message=new String(body,"utf-8");
                String correlationIdCallback=properties.getCorrelationId();
                if (correlationId.equals(correlationIdCallback)){
                System.out.println("[x] 接收斐波拉契函数值: '"+message+"'");
                }
            }
        };
        //执行消费方法
        channel.basicConsume(CALLBACK_QUEUE_NAME,true,defaultConsumer);
    }
}
