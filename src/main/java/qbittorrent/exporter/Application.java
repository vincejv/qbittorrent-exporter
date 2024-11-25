package qbittorrent.exporter;

import io.vertx.core.http.HttpServerResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import qbittorrent.exporter.handler.QbtHttpHandler;

import static io.prometheus.client.exporter.common.TextFormat.CONTENT_TYPE_OPENMETRICS_100;

@Path("")
public class Application {

    @Inject
    QbtHttpHandler qbtHttpHandler;

    @GET
    @Path("metrics")
    @Produces(CONTENT_TYPE_OPENMETRICS_100)
    public void metrics(HttpServerResponse serverResponse) {
        qbtHttpHandler.handleRequest(serverResponse);
    }
}
