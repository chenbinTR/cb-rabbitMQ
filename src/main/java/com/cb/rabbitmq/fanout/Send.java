package com.cb.rabbitmq.fanout;

import com.cb.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 发布/订阅模式
 * 生产者不直接和消费者关联，而是通过交换机（exchange）
 * exchange有四种类型：fanout、direct、topic、direct
 * <p>
 * fanout
 *
 * 生产者发消息给exchange
 * 消费者创建queue，并将queue绑定到exchange
 * exchange再转发给所有绑定到自己的queue（广播）
 * <p>
 * 注意：广播模式存在竞争关系
 * 消息广播到所有绑定exchange的queue上，但如果一个queue有多个consumer消费消息，则只有一个consumer能消费成功
 * 若想让所有的consumer成功消费，则每个consumer需要绑定不懂的queue
 *
 * @author ChenOT
 * @date 2020-05-11
 * @see
 */
public class Send {
    /**
     * 自定义exchange name
     */
    public static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // 消息内容
        String message = "fanout消息";
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        channel.close();
        connection.close();
    }
}
