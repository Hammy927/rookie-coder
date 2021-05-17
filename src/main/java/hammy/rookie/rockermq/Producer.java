package hammy.rookie.rockermq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.util.Objects;

@Slf4j
@Component
public class Producer {

    @Value("${rocketmq.topic.name}")
    private String topic;

    @Autowired
    private RocketMQTemplate mqTemplate;

    public void sendMsg(String msg, String msgId) {
        SendResult sendResult = mqTemplate.syncSend(topic, MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.KEYS, msgId)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .build());

        log.info("sendResult:[{}]", sendResult);
    }

    public void sendOrderly(String msg, String msgId) {
        SendResult sendResult = mqTemplate.syncSendOrderly(topic, MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.KEYS, msgId)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .build(), Objects.hashCode(msg) + "");

        log.info("sendResult:[{}]", sendResult);
    }

    public void asyncSend(String msg, String msgId) {
        mqTemplate.asyncSend(topic, MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.KEYS, msgId)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("sendResult:[{}]", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                log.info("exception:[{}]", e.getMessage());
            }
        });
    }

    public void sendOneWay(String message,
                           String msgId) {
        mqTemplate.sendOneWay(topic, MessageBuilder.withPayload(message)
                .setHeader(RocketMQHeaders.KEYS, msgId)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .build());
    }


}
