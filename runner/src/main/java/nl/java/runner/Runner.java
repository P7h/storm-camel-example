package nl.java.runner;

import nl.avisi.feeder.RandomWordFeeder;
import nl.avisi.jms.JsonTupleProducer;
import nl.avisi.jms.SpringJmsProvider;
import nl.avisi.processor.WordCounterBolt;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.contrib.jms.JmsMessageProducer;
import backtype.storm.contrib.jms.JmsProvider;
import backtype.storm.contrib.jms.JmsTupleProducer;
import backtype.storm.contrib.jms.bolt.JmsBolt;
import backtype.storm.contrib.jms.spout.JmsSpout;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Tuple;

/**
 * @author robbreuk
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        JmsProvider jmsQueueProvider = new SpringJmsProvider(context, "jmsConnectionFactory", "notificationQueue");

        TopologyBuilder builder = new TopologyBuilder();

        JmsBolt jmsBolt = new JmsBolt();
        jmsBolt.setJmsProvider(jmsQueueProvider);
        jmsBolt.setJmsMessageProducer(new JmsMessageProducer() {
            @Override
            public Message toMessage(Session session, Tuple input) throws JMSException {
                String json = "{\"word\":\"" + input.getString(0) + "\", \"count\":" + String.valueOf(input.getInteger(1)) + "}";
                return session.createTextMessage(json);
            }
        });

        builder.setSpout("wordGenerator", new RandomWordFeeder());
        builder.setBolt("counter", new WordCounterBolt()).shuffleGrouping("wordGenerator");
        builder.setBolt("jmsBolt", jmsBolt).shuffleGrouping("counter");

        Config conf = new Config();
        conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("word-count", conf, builder.createTopology());

    }
}
