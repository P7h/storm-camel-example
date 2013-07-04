package nl.avisi.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author robbreuk
 */
public class StreamingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:backtype.storm.contrib.example.queue")
                .to("websocket://storm?sendToAll=true");
    }
}
