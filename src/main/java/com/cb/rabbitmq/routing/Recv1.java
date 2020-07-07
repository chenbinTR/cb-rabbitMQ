package com.cb.rabbitmq.routing;

import com.cb.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.*;
import java.io.IOException;

/**
 * @author ChenOT
 * @date 2020-05-11
 * @see
 * @since
 */
public class Recv1 {
    private final static String QUEUE_NAME = "test_queue_direct_3";
    public static void main(String[] argv) throws Exception {

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange
//        channel.exchangeDeclare(Send.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列到交换机
//        channel.queueBind(QUEUE_NAME, Send.EXCHANGE_NAME, "update");
        channel.queueBind(QUEUE_NAME, Send.EXCHANGE_NAME, "delete");

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 定义队列的消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(envelope.toString());
                System.out.println("Recv1 消息内容:" + new String(body));
            }
        };
        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
