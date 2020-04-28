package com.jet.messaging.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="rocketmq")
//@Component
public class MessageProperties {

    private String nameServer;
//
    private ProducerProperties producer;

    private ConsumerProperties consumer;
//
    public MessageProperties(){

    }

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public ProducerProperties getProducer() {
        return producer;
    }

    public void setProducer(ProducerProperties producer) {
        this.producer = producer;
    }

    public ConsumerProperties getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerProperties consumer) {
        this.consumer = consumer;
    }

    public static class ProducerProperties{
        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        private String groupName;
    }

    public static class ConsumerProperties{
        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        private String groupName;
    }
}
