package hammy.rookie.rockermq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(nameServer = "${rocketmq.name-server}",
        topic = "${rocketmq.topic.name}", consumerGroup = "hammy-consumer-groups")
public class Consumer implements RocketMQListener<String> ,
        RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(String message) {
        log.info("message:[{}]", message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {

    }


}
