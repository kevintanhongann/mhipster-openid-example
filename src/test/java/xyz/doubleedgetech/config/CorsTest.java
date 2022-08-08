package xyz.doubleedgetech.config;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class CorsTest {

    @Inject
    EmbeddedServer server;

    @Inject
    BlockingHttpClient blockingHttpClient;

    @Test
    public void testCorsFilterOnApiPath() {
        Map<String, Object> props = new LinkedHashMap<>();
        props.put("micronaut.server.cors.enabled", true);
        props.put("micronaut.server.cors.single-header", true);
        props.put("micronaut.server.cors.configurations.default.allowed-methods", Arrays.asList("GET", "POST", "PUT", "DELETE"));
        props.put("micronaut.server.cors.configurations.default.max-age", 1800L);
        props.put("micronaut.server.cors.configurations.default.allow-credentials", true);

//        EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class, props);
//        ApplicationContext ctx = server.getApplicationContext();


        HttpResponse<?> response = blockingHttpClient.exchange(
            HttpRequest.OPTIONS("/api/test-cors")
                .header(HttpHeaders.ORIGIN, "other.domain.com")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST"));

        assertThat(response.getStatus().getCode()).isEqualTo(200);
        assertThat(response.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)).isEqualTo("other.domain.com");
        assertThat(response.header(HttpHeaders.VARY)).isEqualTo("Origin");
        assertThat(response.header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS)).isEqualTo("POST");
        assertThat(response.header(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS)).isEqualTo("true");
        assertThat(response.header(HttpHeaders.ACCESS_CONTROL_MAX_AGE)).isEqualTo("1800");

//        ctx.close();
    }

    @Test
    public void testCorsFilterDeactivated() throws Exception {
        Map<String, Object> props = new LinkedHashMap<>();
        props.put("micronaut.server.cors.enabled", false);
        props.put("micronaut.server.cors.single-header", true);
        props.put("micronaut.server.cors.configurations.default.allowed-methods", Arrays.asList("GET", "POST", "PUT", "DELETE"));
        props.put("micronaut.server.cors.configurations.default.max-age", 1800L);
        props.put("micronaut.server.cors.configurations.default.allow-credentials", true);

//        EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class, props);
//        ApplicationContext ctx = server.getApplicationContext();


        HttpResponse<?> response = blockingHttpClient.exchange(
            HttpRequest.GET("/api/test-cors")
                .header(HttpHeaders.ORIGIN, "other.domain.com")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST"));

        assertThat(response.getStatus().getCode()).isEqualTo(200);
        assertThat(response.getHeaders().contains(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)).isFalse();

//        ctx.close();
    }

}
