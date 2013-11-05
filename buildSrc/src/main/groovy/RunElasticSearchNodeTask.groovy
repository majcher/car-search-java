import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

class RunElasticSearchNodeTask extends DefaultTask {

    String clusterName;
    String nodeName;
    boolean join;

    @TaskAction
    def runNode() {
        logger.info('Starting ElasticSearch node...')

        NodeBuilder nb = NodeBuilder.nodeBuilder().data(true)
        if (clusterName != null)
            nb.clusterName(clusterName)

        if (nodeName != null)
            nb.settings().put("node.name", nodeName)

        nb.node();

        logger.info('ElasticSearch node started')

        if (join)
            blockThread()
    }

    def blockThread() {
        Object lock = new Object()
        while (true) {
            synchronized (lock) {
                lock.wait()
            }
        }
    }

}