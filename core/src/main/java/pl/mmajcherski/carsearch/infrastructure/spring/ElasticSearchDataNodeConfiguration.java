package pl.mmajcherski.carsearch.infrastructure.spring;

import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("es-server")
public class ElasticSearchDataNodeConfiguration {

	@Bean(destroyMethod = "close")
    public Node elasticSearchClientNodeNode() {
        NodeBuilder nb = NodeBuilder.nodeBuilder();
	    nb.settings().put("http.enabled", false);
	    nb.settings().put("node.name", "DataNode");
		return nb.node();
    }

}
