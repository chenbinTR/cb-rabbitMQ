package com.cb.rabbitmq.direct;

import com.cb.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 发布/订阅模式
 * 生产者不直接和消费者关联，而是通过交换机（exchange）
 * exchange有四种类型：fanout、direct、topic、direct
 *
 * direct
 * 生产者发消息给exchange，同时设定了消息的key
 *
 * 消费者创建队列，并与exchange绑定，同时制定队列接收特定key的消息
 *
 * 同样存在竞争关系
 *
 */
public class Send {
    public static final String EXCHANGE_NAME = "text_exchange_direct";

    public static void main(String[] args) throws Exception{
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 消息内容
        String message = "添加商品999999";
        channel.basicPublish(EXCHANGE_NAME, "insert", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }
}
