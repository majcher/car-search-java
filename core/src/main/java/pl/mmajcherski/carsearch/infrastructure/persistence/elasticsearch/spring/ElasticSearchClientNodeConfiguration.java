package pl.mmajcherski.carsearch.infrastructure.persistence.elasticsearch.spring;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchClientNodeConfiguration {

    @Bean(destroyMethod = "close")
    public Node elasticSearchClientNodeNode() {
	    NodeBuilder nb = NodeBuilder.nodeBuilder().client(true);
	    nb.settings().put("http.enabled", false);
	    nb.settings().put("node.name", "ClientNode");
        return nb.node();
    }

	@Bean(destroyMethod = "close")
    public Client elasticSearchNodeClient() {
        return elasticSearchClientNodeNode().client();
    }

}
