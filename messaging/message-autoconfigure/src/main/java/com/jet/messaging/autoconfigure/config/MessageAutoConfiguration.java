package com.jet.messaging.autoconfigure.config;


import com.jet.estate.message.core.AConfiguration;
import com.jet.messaging.autoconfigure.MessageProperties;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.LitePullConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@EnableConfigurationProperties(MessageProperties.class)
//@Import(AConfiguration.class)
@ComponentScan( "com.jet.estate.message.core")
public class MessageAutoConfiguration {
    private MessageProperties properties;

    public MessageAutoConfiguration(MessageProperties props) {
        this.properties = props;
    }

    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.producer", name = "groupName", matchIfMissing = false)
    public MQProducer defaultMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer();

        producer.setNamesrvAddr(this.properties.getNameServer());
        producer.setProducerGroup(this.properties.getProducer().getGroupName());

        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }

    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.consumer", name = "groupName")
    public LitePullConsumer defaultConsumer() {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer();

        consumer.setNamesrvAddr(this.properties.getNameServer());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setConsumerGroup(this.properties.getConsumer().getGroupName());
        try {
            consumer.subscribe("tp", "*");
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return consumer;
    }
}
