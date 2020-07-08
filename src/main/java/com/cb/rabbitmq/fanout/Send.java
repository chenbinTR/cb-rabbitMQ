package com.cb.rabbitmq.fanout;

import com.cb.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.*;

/**
 * fanout类型 广播模式（也可以成为发布/订阅模式）
 * fanout：所有bind到此exchange的queue都可以接收消息(纯广播的)
 * 注意：广播模式存在竞争关系
 * 消息广播到所有绑定exchange的queue上，但如果一个queue有多个consumer消费消息，则只有一个consumer能消费成功
 * 若想让所有的consumer成功消费，则每个consumer需要绑定不懂的queue
 *
 * @author ChenOT
 * @date 2020-05-11
 * @see
 * @since
 */
public class Send {
    /**
     * 自定义exchange name
     */
    public static final String EXCHANGE_NAME = "fanout_nlu";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT, true);
        // 消息参数
        AMQP.BasicProperties.Builder properties = MessageProperties.PERSISTENT_TEXT_PLAIN.builder();
        properties.contentType("application/json");
        // 消息内容
        String message = "{\"code\":\"entity\",\"data\":{\"apiKey\":\"general\",\"name\":\"poetry_author\",\"entity\":{\"value\":\"匡亚明\",\"score\":100,\"nature\":\"n_poetry_author\"},\"operate\":2}}";
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME, "", properties.build(), message.getBytes());
        channel.close();
        connection.close();
    }
}
