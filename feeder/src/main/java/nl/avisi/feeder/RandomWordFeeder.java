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
 * @author Prashanth
 */
public class RandomWordFeeder extends BaseRichSpout {

	private static final String[] COMPANIES = new String[]{
		"Google", "Apple", "BlackBerry", "Microsoft", "Amazon", "Motorola", "HTC", "Samsung", "Nokia", "LG", "Sony", "Dell",
		"ASUS", "ACER", "ZTE"};
	private SpoutOutputCollector collector;
	private Random random;

	@Override
	public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
		this.collector = collector;
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public void nextTuple() {
		Utils.sleep(200);

		collector.emit(new Values(COMPANIES[random.nextInt(COMPANIES.length)]));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}
}
