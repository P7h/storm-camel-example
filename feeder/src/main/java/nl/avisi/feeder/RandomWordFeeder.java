package nl.avisi.feeder;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

/**
 * @author robbreuk
 */
public class RandomWordFeeder extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private Random random;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        this.collector = collector;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void nextTuple() {
        Utils.sleep(100);
        String[] words = new String[]{
                "accompts", "active",  "altiloquent", "bicuspid",  "biweekly", "buffo", "chattels", "detached", "gaoler", "heeltap",  "milksop",
                "paralyzed", "passado", "reciminate", "repetend", "supertonic", "swashbuckler", "vaporarium", "wenching", "withers"
        };

        collector.emit(new Values(words[random.nextInt(words.length)]));
    }
}
