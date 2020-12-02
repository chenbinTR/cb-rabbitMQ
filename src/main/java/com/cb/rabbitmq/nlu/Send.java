package com.cb.rabbitmq.nlu;

import com.cb.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 发布/订阅模式
 * 生产者不直接和消费者关联，而是通过交换机（exchange）
 * exchange有四种类型：fanout、direct、topic、direct
 *
 * fanout
 * 生产者发消息给exchange
 * exchange再转发给所有绑定到自己的queue（广播）
 *
 * queue是有consumer创建的
 *
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
        String message = "{\"code\":\"entity\",\"data\":{\"apiKey\":\"34e86b172bb145cbbbfd72395e58722f\",\"name\":\"story_name\",\"entity\":{\"value\":\"产品妈妈\",\"normalize\":\"达到法定\",\"score\":100},\"operate\":1}}";
//        JSONObject jsonObject = JSONObject.parseObject(message);
//        System.out.println(jsonObject.toString());
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME, "", properties.build(), toByteArray(message));
        channel.close();
        connection.close();
    }
    /**
     * 对象转数组
     * @param obj
     * @return
     */
    public static byte[] toByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}
