package com.cb.rabbitmq.work;

import com.cb.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv1 {
    public static void main(String[] argv) throws Exception {

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(Send.QUEUE_NAME, false, false, false, null);

        // 同一时刻服务器只会发一条消息给消费者
        //channel.basicQos(1);

        // 定义队列的消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println(consumerTag);
                System.out.println(envelope.toString());
//                System.out.println(properties.toString());
                System.out.println("Recv1 消息内容:" + new String(body));
            }
        };
        // 监听队列
        channel.basicConsume(Send.QUEUE_NAME, true, consumer);
    }
}
