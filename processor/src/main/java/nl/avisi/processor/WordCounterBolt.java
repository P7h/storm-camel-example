package nl.avisi.processor;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * @author robbreuk
 */
public final class WordCounterBolt extends BaseBasicBolt {

    private static final long serialVersionUID = -5809759391597965718L;
    private final Map<String, Integer> counts = new HashMap<>();

    @Override
    public final void execute(final Tuple tuple, final BasicOutputCollector collector) {
        final String word = tuple.getString(0);
        int count = counts.getOrDefault(word, 0);
        count++;
        counts.put(word, count);

        collector.emit(new Values(word, count));
    }

    @Override
    public final void declareOutputFields(final OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "count"));
    }
}