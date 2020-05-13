//package com.cb.rabbitmq.routing;
//
//import com.cb.rabbitmq.ConnectionUtil;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//
///**
// * 根据消息“类别”，由特定的消费者消费
// * @author ChenOT
// * @date 2020-05-11
// * @see
// * @since
// */
//public class Send {
//    private static final String EXCHANGE_NAME = "text_exchange_direct";
//
//    public static void main(String[] args) throws Exception{
//        // 获取到连接以及mq通道
//        Connection connection = ConnectionUtil.getConnection();
//        Channel channel = connection.createChannel();
//
//        // 声明exchange
//        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//
//        // 消息内容
//        String message = "删除商品";
//        channel.basicPublish(EXCHANGE_NAME, "delete", null, message.getBytes());
//        System.out.println(" [x] Sent '" + message + "'");
//
//        channel.close();
//        connection.close();
//
//    }
//}
