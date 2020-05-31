package net.thumbtack.asurovenko;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class App {
    public static void main(String[] args) {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        MyApplication config = new MyApplication();
        Server server = JettyHttpContainerFactory.createServer(baseUri, config);
    }
}