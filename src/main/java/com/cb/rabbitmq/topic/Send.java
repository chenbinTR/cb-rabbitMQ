package com.cb.rabbitmq.topic;

import com.cb.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 发布/订阅模式
 * 生产者不直接和消费者关联，而是通过交换机（exchange）
 * exchange有四种类型：fanout、direct、topic、direct
 *
 * topic
 * 生产者发消息给exchange，同时设定了消息的key（模糊key、规则key）
 *
 * 消费者创建队列，并与exchange绑定，同时制定队列接收特定key的消息
 *
 * 同样存在竞争关系

 *  (星号) 用来表示一个单词 (必须出现的)
 #  (井号) 用来表示任意数量（零个或多个）单词
 */
public class Send {

    public final static String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // 消息内容
        String message = "Hello World!!";
        channel.basicPublish(EXCHANGE_NAME, "routekey.1", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
