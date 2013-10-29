import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.server.handler.ContextHandler
import org.eclipse.jetty.server.handler.ResourceHandler

import org.eclipse.jetty.webapp.WebAppContext;

class RunJettyTask extends DefaultTask {

    int port = 8080
    boolean join = false
    def resources = [:]
    def wars = [:]

    @TaskAction
    def runJetty() {
        logger.info('Starting Jetty...')

        Server server = new Server(port)

        HandlerList handlerList = new HandlerList()

        wars.each() { ctx, warPath ->
            logger.info("Registering WAR file: $ctx - $warPath")

            WebAppContext webapp = new WebAppContext()
            webapp.setContextPath(ctx)
            webapp.setWar(warPath)

            handlerList.addHandler(webapp)
        }

        resources.each {ctx, path ->
            logger.info("Registering resources: $ctx - $path")

            ContextHandler context = new ContextHandler();
            context.setContextPath(ctx);
            context.setResourceBase(path);
            context.setHandler(new ResourceHandler())

            handlerList.addHandler(context)
        }

        server.setHandler(handlerList)
        server.start()

        if (join) {
            logger.lifecycle("Server started: http://localhost:$port/ - press Ctrl+C to stop.")
            server.join()
        }
    }

}