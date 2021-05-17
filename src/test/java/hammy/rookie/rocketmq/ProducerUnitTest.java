package hammy.rookie.rocketmq;

import hammy.rookie.rockermq.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerUnitTest {

    @Autowired
    private Producer producer;

    @Test
    public void synSend() {
        producer.sendMsg("hello, world", "1");
    }

    @Test
    public void asyncSend() {
        for (int i = 0; i < 5; i++) {
            producer.asyncSend("hello, ca", "1");
        }
    }

    @Test
    public void sendOrderly() {
        int val = 0;
        for (int i = 0; i < 5; i++) {
            producer.sendOrderly("hello, orderly", String.valueOf(++val));
        }
    }
}
