package test.edu.upenn.cis.stormlite;

import edu.upenn.cis.stormlite.Config;
import edu.upenn.cis.stormlite.LocalCluster;
import edu.upenn.cis.stormlite.TopologyBuilder;
import edu.upenn.cis.stormlite.tuple.Fields;

public class TestWordCount {
	private static final String WORD_SPOUT = "WORD_SPOUT";
    private static final String COUNT_BOLT = "COUNT_BOLT";
    private static final String INSERT_BOLT = "INSERT_BOLT";
    
    public static void main(String[] args) throws Exception {
        Config config = new Config();

        WordSpout spout = new WordSpout();
        WordCounter bolt = new WordCounter();
        PrintBolt printer = new PrintBolt();

        // wordSpout ==> countBolt ==> MongoInsertBolt
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout(WORD_SPOUT, spout, 1);
        builder.setBolt(COUNT_BOLT, bolt, 1).shuffleGrouping(WORD_SPOUT);
        builder.setBolt(INSERT_BOLT, printer, 1).fieldsGrouping(COUNT_BOLT, new Fields("word"));


        if (args.length == 2) {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("test", config, 
            		builder.createTopology());
            Thread.sleep(30000);
            cluster.killTopology("test");
            cluster.shutdown();
            System.exit(0);
        } else{
            System.out.println("Usage: InsertWordCount <mongodb url> <mongodb collection> [topology name]");
        }
    } 
}
