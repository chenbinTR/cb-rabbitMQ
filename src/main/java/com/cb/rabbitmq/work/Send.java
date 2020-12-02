package com.cb.rabbitmq.work;


import com.cb.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * work模式
 * 轮询分发就是将消息队列中的消息，依次发送给所有消费者。一个消息只能被一个消费者获取。
 * 一个队列
 * 一个生产者
 * 多个消费者
 * 每个消费者分别消费部分消息
 * 只有一个消费者能得到消息
 * 无exchange
 *
 * work模式中，如果只有一个生产者和消费者，就是最普通的模式
 */
public class Send {
    public final static String QUEUE_NAME = "test_queue_work";
    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 100; i++) {
            // 消息内容
            String message = "" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(message);
            Thread.sleep(i * 10);
        }

        channel.close();
        connection.close();
    }
}
