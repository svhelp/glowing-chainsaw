package serv;

import javax.ws.rs.container.ContainerRequestContext;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class RESTCorsResponseFilter implements ContainerResponseFilter {
    private final static Logger log = Logger.getLogger( RESTCorsRequestFilter.class.getName() );

    @Override
    public void filter( ContainerRequestContext requestCtx, ContainerResponseContext responseCtx ) throws IOException {
        log.info( "Executing REST response filter" );

        responseCtx.getHeaders().add( "Access-Control-Allow-Origin", "http://localhost:8070" );
        responseCtx.getHeaders().add( "Access-Control-Allow-Headers", "Accept, Content-Type" );
        responseCtx.getHeaders().add( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" );
    }
}