package pl.mmajcherski.carsearch.infrastructure.spring;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {

    @Bean
    public Node elasticSearchNode() {
        return NodeBuilder.nodeBuilder().node();
    }

    @Bean
    public Client elasticSearchNodeClient() {
        return elasticSearchNode().client();
    }

}
