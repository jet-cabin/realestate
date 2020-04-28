package com.jet.estate.message.core;

import com.jet.estate.message.core.annotations.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.LitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class Consumer implements ApplicationContextAware {
    private Map<String, Map<Object, Method>> listeners = new ConcurrentHashMap<>();
    private ApplicationContext context;

    @Autowired
    private LitePullConsumer innerConsumer;

    public Consumer() {
    }

    @PostConstruct
    public void afterConstruct() {
        log.info("");
        registerListener();
        polling();
    }

    public void registerListener() {
        Map<String, Object> beans = this.context.getBeansWithAnnotation(MessageListener.class);

        if (CollectionUtils.isEmpty(beans)) return;

        for (Object beanEntry : beans.values()) {
            MessageListener annotation = beanEntry.getClass().getAnnotation(MessageListener.class);

            Map<Object, Method> subscribers = listeners.getOrDefault(annotation.topic(), new HashMap<>());

            Method[] methods = beanEntry.getClass().getDeclaredMethods();

            Method method = Arrays.stream(methods).filter(m -> m.getParameterTypes().length == 1 && m.getParameterTypes()[0] == MessageExt.class).findFirst().get();

            subscribers.putIfAbsent(beanEntry, method);

            listeners.putIfAbsent(annotation.topic(), subscribers);
        }
    }

    public void polling() {
        if (CollectionUtils.isEmpty(listeners)) return;

//        innerConsumer = context.getBean(LitePullConsumer.class);
        Map<Object, Method> listener;

        while (true) {
            List<MessageExt> msgs = innerConsumer.poll();

            for (MessageExt msg : msgs) {
                listener = listeners.get(msg.getTopic());
                listener.forEach((obj, method) -> {
                    if (null == method) return;

                    try {
                        method.invoke(obj, msg);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        this.innerConsumer.shutdown();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }
}
