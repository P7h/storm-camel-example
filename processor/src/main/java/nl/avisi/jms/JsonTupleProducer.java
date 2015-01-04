package nl.avisi.jms;

import backtype.storm.contrib.jms.JmsTupleProducer;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@SuppressWarnings("serial")
public class JsonTupleProducer implements JmsTupleProducer {

    public Values toTuple(Message msg) throws JMSException {
        if (msg instanceof TextMessage) {
            String json = ((TextMessage) msg).getText();
            return new Values(json);
        } else {
            return null;
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("json"));
    }

}