package com.shsxt.simple;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author zhangliyan by 2017-09-04-下午 3:38
 */
public class CosumerOwner extends DefaultConsumer{
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public CosumerOwner(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println(consumerTag+"\r\n"+envelope+"\r\n"+properties+"\r\n"+body);
        //打印一下内容
        String message=new String(body,"utf-8");
        System.out.println("[x] Received'"+message+"'");
    }
}
