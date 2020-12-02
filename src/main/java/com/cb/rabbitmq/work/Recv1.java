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
        /**
		 true:表示自动确认，只要消息从队列中获取，无论消费者获取到消息后是否成功消费，都会认为消息已经成功消费
		 false:表示手动确认，消费者获取消息后，服务器会将该消息标记为不可用状态，等待消费者的反馈，
				如果消费者一直没有反馈，那么该消息将一直处于不可用状态，并且服务器会认为该消费者已经挂掉，不会再给其
				发送消息，直到该消费者反馈。
	    */
        channel.basicConsume(Send.QUEUE_NAME, true, consumer);
    }
}
