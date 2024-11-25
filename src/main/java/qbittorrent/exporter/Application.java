package qbittorrent.exporter;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import qbittorrent.exporter.handler.QbtHttpHandler;

@Path("")
public class Application {

    @Inject
    QbtHttpHandler qbtHttpHandler;

    @GET
    @Path("metrics")
    @Produces({MediaType.TEXT_PLAIN})
    public Uni<Response> metrics() {
        return Uni.createFrom().item(() -> {
            try {
                // Return the response reactively
                return Response.ok(qbtHttpHandler.handleRequest()).build();
            } catch (Exception e) {
                // Return the error response reactively
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("An error occurred. " + e.getMessage())
                        .build();
            }
        });
    }
}
