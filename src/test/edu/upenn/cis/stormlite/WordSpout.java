package test.edu.upenn.cis.stormlite;

import java.util.Map;
import java.util.Random;

import edu.upenn.cis.stormlite.OutputFieldsDeclarer;
import edu.upenn.cis.stormlite.TopologyContext;
import edu.upenn.cis.stormlite.bolt.OutputCollector;
import edu.upenn.cis.stormlite.spout.IRichSpout;
import edu.upenn.cis.stormlite.tuple.Fields;
import edu.upenn.cis.stormlite.tuple.Values;

public class WordSpout implements IRichSpout {

    OutputCollector collector;
    public static final String[] words = new String[] { "apple", "orange", "pineapple", "banana", "watermelon" };

    public WordSpout() {
    }


    @SuppressWarnings("rawtypes")
    public void open(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    public void close() {

    }

    public void nextTuple() {
        final Random rand = new Random();
        final String word = words[rand.nextInt(words.length)];
        this.collector.emit(new Values<Object>(word));
        Thread.yield();
    }

    public void ack(Object msgId) {

    }

    public void fail(Object msgId) {

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

}
