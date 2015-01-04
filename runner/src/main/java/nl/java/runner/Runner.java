package nl.java.runner;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.contrib.jms.JmsProvider;
import backtype.storm.contrib.jms.bolt.JmsBolt;
import backtype.storm.topology.TopologyBuilder;
import nl.avisi.feeder.RandomWordFeeder;
import nl.avisi.jms.SpringJmsProvider;
import nl.avisi.processor.WordCounterBolt;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * @author robbreuk
 * @author Prashanth
 */
public final class Runner {
    public static final void main(final String[] args) throws Exception {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        final JmsProvider jmsQueueProvider = new SpringJmsProvider(applicationContext, "jmsConnectionFactory",
                "notificationQueue");

        final TopologyBuilder topologyBuilder = new TopologyBuilder();

        final JmsBolt jmsBolt = new JmsBolt();
        jmsBolt.setJmsProvider(jmsQueueProvider);
        jmsBolt.setJmsMessageProducer((session, input) -> {
            final String json = "{\"word\":\"" + input.getString(0) + "\", \"count\":" + String.valueOf(input.getInteger(1)) + "}";
            return session.createTextMessage(json);
        });

        topologyBuilder.setSpout("wordGenerator", new RandomWordFeeder());
        topologyBuilder.setBolt("counter", new WordCounterBolt()).shuffleGrouping("wordGenerator");
        topologyBuilder.setBolt("jmsBolt", jmsBolt).shuffleGrouping("counter");

        final Config config = new Config();
        config.setDebug(false);

        final LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("word-count", config, topologyBuilder.createTopology());
    }
}
